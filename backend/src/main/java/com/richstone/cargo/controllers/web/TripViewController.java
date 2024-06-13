package com.richstone.cargo.controllers.web;

import com.richstone.cargo.dto.*;
import com.richstone.cargo.mapper.TruckMapper;
import com.richstone.cargo.model.*;
import com.richstone.cargo.repository.DriverRepository;
import com.richstone.cargo.service.impl.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Controller
@RequiredArgsConstructor
@RequestMapping("/trips")
public class TripViewController {
    private final TripServiceImpl tripService;
    private final UserServiceImpl userService;
    private final RouteServiceImpl routeService;
    private final DriverRepository driverRepository;
    private final ImageServiceImpl imageService;
    private final TruckServiceImpl truckService;
    private final CargoServiceImpl cargoService;
    private final IncomeServiceImpl incomeService;
    private final ExpenseServiceImpl expenseService;
    private final FirebaseNotificationServiceImpl notificationService;
    private final TripChangeHistoryServiceImpl tripChangeHistoryService;

    @PostMapping("/save")
    public String save(@ModelAttribute("trip") TripDto tripDto) {
        tripService.addTrip(tripDto);
        return "redirect:/trips/1";
    }

    @GetMapping("/{pageNo}")
    public String tripList(@PathVariable(value = "pageNo") int pageNo, Model model) {
        int pageSize = 10;
        Page<Trip> page = tripService.getAllTrips(pageNo, pageSize);
        List<Trip> trips = page.getContent();
        List<Double> incomes = incomeService.calculateIncomeForEachTrip(trips);
        List<Double> expenses = expenseService.calculateExpenseForEachTrip(trips);
        model.addAttribute("trips", trips);
        model.addAttribute("incomes", incomes);
        model.addAttribute("expenses", expenses);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        return "trip-page";
    }

    @GetMapping("/formForAddTrip")
    public String formForAddTrip(Model model) {
        TripDto trip = new TripDto();
        List<User> drivers = userService.getAllDrivers();
        List<Route> routes = routeService.getAllRoutes();
        model.addAttribute("drivers", drivers);
        model.addAttribute("trip", trip);
        model.addAttribute("routes", routes);
        return "trip-form";
    }

    @GetMapping("/formForUpdateTrip")
    public String formForUpdateTrip(@RequestParam("id") Long id, Model model) {
        TripCargoFormDto tripCargoForm = new TripCargoFormDto();

        Trip tripById = tripService.findById(id);

        Cargo cargo = cargoService.getCargoByTrip(tripById.getId());

        tripCargoForm.setTrip(tripById);
        tripCargoForm.setCargo(cargo);

        List<Driver> drivers = driverRepository.findAll();
        List<Route> routes = routeService.getAllRoutes();

        model.addAttribute("drivers", drivers);
        model.addAttribute("routes", routes);
        model.addAttribute("tripCargoForm", tripCargoForm);

        return "trip-update-form";
    }

    @PostMapping("/update")
    public String updateTrip(@ModelAttribute("tripCargoForm") TripCargoFormDto tripCargoForm) {
        Trip existingTrip = tripService.findById(tripCargoForm.getTrip().getId());

        StringBuilder changeDescription = new StringBuilder("Рейс " + existingTrip.getTripNumber() + " " + existingTrip.getRoute().getName() + " Изменено: ");

        checkAndUpdateField(changeDescription, "статус", existingTrip.getTripStatus(), tripCargoForm.getTrip().getTripStatus(), () -> existingTrip.setTripStatus(tripCargoForm.getTrip().getTripStatus()));
        checkAndUpdateField(changeDescription, "время отправления", existingTrip.getDepartureTime(), tripCargoForm.getTrip().getDepartureTime(), () -> existingTrip.setDepartureTime(tripCargoForm.getTrip().getDepartureTime()));
        checkAndUpdateField(changeDescription, "время прибытия", existingTrip.getArrivalTime(), tripCargoForm.getTrip().getArrivalTime(), () -> existingTrip.setArrivalTime(tripCargoForm.getTrip().getArrivalTime()));
        checkAndUpdateField(changeDescription, "водитель", existingTrip.getAssignedDriver().getUser().getUsername(), tripCargoForm.getTrip().getAssignedDriver().getUser().getUsername(), () -> existingTrip.setAssignedDriver(tripCargoForm.getTrip().getAssignedDriver()));
        checkAndUpdateField(changeDescription, "маршрут", existingTrip.getRoute() != null ? existingTrip.getRoute().getName() : null, tripCargoForm.getTrip().getRoute() != null ? tripCargoForm.getTrip().getRoute().getName() : null, () -> existingTrip.setRoute(tripCargoForm.getTrip().getRoute()));

        Cargo cargo = existingTrip.getCargo();
        checkAndUpdateField(changeDescription, "наименование груза", cargo.getName(), tripCargoForm.getCargo().getName(), () -> cargo.setName(tripCargoForm.getCargo().getName()));
        checkAndUpdateField(changeDescription, "описание груза", cargo.getDescription(), tripCargoForm.getCargo().getDescription(), () -> cargo.setDescription(tripCargoForm.getCargo().getDescription()));
        checkAndUpdateField(changeDescription, "вес груза", cargo.getWeight(), tripCargoForm.getCargo().getWeight(), () -> cargo.setWeight(tripCargoForm.getCargo().getWeight()));
        checkAndUpdateField(changeDescription, "количество паллет", cargo.getNumberOfPallets(), tripCargoForm.getCargo().getNumberOfPallets(), () -> cargo.setNumberOfPallets(tripCargoForm.getCargo().getNumberOfPallets()));
        checkAndUpdateField(changeDescription, "температура груза", cargo.getTemperature(), tripCargoForm.getCargo().getTemperature(), () -> cargo.setTemperature(tripCargoForm.getCargo().getTemperature()));

        cargoService.updateCargo(cargo);
        tripService.save(existingTrip);

        if (changeDescription.toString().endsWith("; ")) {
            changeDescription.setLength(changeDescription.length() - 2);
        }

        TripChangeHistory tripChangeHistory = TripChangeHistory.builder()
                .trip(existingTrip)
                .changeDescription(changeDescription.toString())
                .changeTime(LocalDateTime.now())
                .changeTitle(getChangeTitle(changeDescription.toString()))
                .build();
        tripChangeHistoryService.saveChangeHistory(tripChangeHistory);

        User user = existingTrip.getAssignedDriver().getUser();
        notificationService.sendTripUpdateNotification(user);

        return "redirect:/trips/1";
    }

    private <T> void checkAndUpdateField(StringBuilder changeDescription, String fieldName, T oldValue, T newValue, Runnable updateAction) {
        if (oldValue == null && newValue == null) {
            return;
        }
        if (oldValue == null || !oldValue.equals(newValue)) {
            changeDescription.append(fieldName).append(": ").append(oldValue).append(" на ").append(newValue).append("; ");
            updateAction.run();
        }
    }
    private String getChangeTitle(String changeDescription) {
        if (changeDescription.contains("статус")) {
            return "Изменение статуса рейса";
        } else if (changeDescription.contains("время отправления")) {
            return "Изменение времени отправления";
        } else if (changeDescription.contains("время прибытия")) {
            return "Изменение времени прибытия";
        } else if (changeDescription.contains("водитель")) {
            return "Изменение водителя";
        } else if (changeDescription.contains("маршрут")) {
            return "Изменение маршрута";
        } else if (changeDescription.contains("наименование груза")) {
            return "Изменение наименование груза";
        } else if (changeDescription.contains("описание груза")) {
            return "Изменение описание груза";
        } else if (changeDescription.contains("вес груза")) {
            return "Изменение веса груза";
        } else if (changeDescription.contains("количество паллет")) {
            return "Изменение количество паллет";
        } else if (changeDescription.contains("температура груза")) {
            return "Изменение температуры груза";
        }
        return "Изменение по рейсу";
    }

    @GetMapping("/activeTrips/{pageNo}")
    public String activeTripsList(@PathVariable(value = "pageNo") int pageNo, Model model) {
        int pageSize = 10;
        Page<Trip> page = tripService.getActiveTrips(pageNo, pageSize);
        List<Trip> trips = page.getContent();
        model.addAttribute("trips", trips);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        return "active-trips-page";
    }

    @GetMapping("/archive/{pageNo}")
    public String tripsHistoryList(@PathVariable(value = "pageNo") int pageNo, Model model) {
        int pageSize = 10;
        Page<Trip> page = tripService.getTripsHistory(pageNo, pageSize);
        List<Trip> trips = page.getContent();
        model.addAttribute("trips", trips);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        return "trips-archive-page";
    }

    @GetMapping("/details/{id}")
    public String tripDetails(@PathVariable("id") Long id, Model model) {
        Trip trip = tripService.findById(id);
        User driver = trip.getAssignedDriver().getUser();
        Image image = imageService.getImageToUser(driver);
        Truck truckByDriver = truckService.getTruckByDriver(trip.getAssignedDriver());
        TruckDto truck = TruckMapper.INSTANCE.truckToDto(truckByDriver);
        CargoDto cargo = cargoService.getCargoByTripId(trip.getId());
        if (image != null && image.getImageBytes() != null && image.getImageBytes().length > 0) {
            String encodedImg = Base64.getEncoder().encodeToString(image.getImageBytes());
            model.addAttribute("encodedImg", encodedImg);
        } else {
            try {
                Resource resource = new ClassPathResource("static/images/default.png");
                byte[] defaultImageBytes;
                try (InputStream inputStream = resource.getInputStream()) {
                    defaultImageBytes = inputStream.readAllBytes();
                }
                String encodedImg = Base64.getEncoder().encodeToString(defaultImageBytes);
                model.addAttribute("encodedImg", encodedImg);
            } catch (IOException e) {
                return "error-page";
            }
        }
        model.addAttribute("trip", trip);
        model.addAttribute("driver", driver);
        model.addAttribute("image", image);
        model.addAttribute("truck", truck);
        model.addAttribute("cargo", cargo);
        return "trip-details-page";
    }

    @PostMapping("/delete")
    public String deleteDispatcher(@RequestParam("id") Long id) {
        tripService.cancelTrip(id);
        return "redirect:/trips/1";
    }

    @GetMapping("/incomes/{id}")
    public String incomesList(@PathVariable("id") Long id, Model model) {
        List<Income> incomes = incomeService.getIncomesByTripId(id);
        IncomeRequestDto incomeDto = new IncomeRequestDto();
        model.addAttribute("incomes", incomes);
        model.addAttribute("tripId", id);
        model.addAttribute("incomeDto", incomeDto);
        return "incomes-page";
    }

    @PostMapping("/addIncome")
    public ResponseEntity<String> addIncome(@ModelAttribute IncomeRequestDto income) {
        Trip trip = tripService.findById(income.getTripId());
        incomeService.saveIncome(income, trip);
        return ResponseEntity.ok("Income added successfully");
    }

    @PostMapping("/deleteIncome")
    public ResponseEntity<String> deleteIncome(@RequestParam("id") Long id) {
        incomeService.delete(id);
        return ResponseEntity.ok("Income deleted successfully");
    }

    @GetMapping("/expenses/{id}")
    public String expensesList(@PathVariable("id") Long id, Model model) {
        List<Expense> expenses = expenseService.getExpensesByTripId(id);
        ExpenseRequestDto expenseDto = new ExpenseRequestDto();
        model.addAttribute("expenses", expenses);
        model.addAttribute("tripId", id);
        model.addAttribute("expenseDto", expenseDto);
        return "expenses-page";
    }

    @PostMapping("/addExpense")
    public ResponseEntity<String> addExpense(@ModelAttribute ExpenseRequestDto expense) {
        Trip trip = tripService.findById(expense.getTripId());
        expenseService.saveExpense(expense, trip);
        return ResponseEntity.ok("Expense added successfully");
    }

    @PostMapping("/deleteExpense")
    public ResponseEntity<String> deleteExpense(@RequestParam("id") Long id) {
        expenseService.delete(id);
        return ResponseEntity.ok("Expense deleted successfully");
    }

}
