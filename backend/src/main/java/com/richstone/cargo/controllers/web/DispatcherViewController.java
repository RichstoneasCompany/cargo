package com.richstone.cargo.controllers.web;

import com.richstone.cargo.dto.UserDto;
import com.richstone.cargo.model.User;
import com.richstone.cargo.service.impl.AdminServiceImpl;
import com.richstone.cargo.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dispatchers")
public class DispatcherViewController {
    private final AdminServiceImpl adminService;
    private final UserServiceImpl userService;

    @GetMapping
    public String dispatchersList(Model model) {
        List<User> dispatchers = userService.getAllDispatchers();
        model.addAttribute("dispatchers", dispatchers);
        return "dispatcher-list";
    }

    @GetMapping("/formForAddDispatcher")
    @Secured("ADMIN")
    public String formForAddDispatcher(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "dispatcher-form";
    }

    @PostMapping("/save")
    public String addDispatcher(@ModelAttribute("user") UserDto user) {
        adminService.addDispatcher(user);
        return "redirect:/dispatchers";
    }

    @PostMapping("/delete")
    public String deleteDispatcher(@RequestParam("userId") Long id) {
        User user = userService.findById(id);
        userService.delete(user);
        return "redirect:/dispatchers";
    }

    @GetMapping("/formForUpdateDispatcher")
    public String formForUpdateDispatcher(@RequestParam("id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "update-dispatcher-form";
    }

    @PostMapping("/update")
    public String updateDispatcher(@ModelAttribute("user") User user) {
        User existingUser = userService.findById(user.getId());

        existingUser.setUsername(user.getUsername());
        existingUser.setFirstname(user.getFirstname());
        existingUser.setLastname(user.getLastname());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhone(user.getPhone());
        existingUser.setRole(user.getRole());

        userService.save(existingUser);
        return "redirect:/dispatchers";
    }

}
