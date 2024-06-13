package com.richstone.cargo.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.richstone.cargo.dto.LocationDto;
import com.richstone.cargo.model.Coordinates;
import com.richstone.cargo.model.Location;
import com.richstone.cargo.repository.CoordinatesRepository;
import com.richstone.cargo.repository.LocationRepository;
import com.richstone.cargo.service.LocationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    @Value("${api.yandex.key}")
    private String API_KEY;
    private final CoordinatesRepository coordinatesRepository;
    private final LocationRepository locationRepository;

    @Override
    public void saveLocation(LocationDto locationDto) {
        String address = locationDto.getAddress();
        String url = "https://geocode-maps.yandex.ru/1.x/?apikey=" + API_KEY + "&geocode=" + address + "&format=json";

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(response);
            JsonNode pointNode = jsonNode.at("/response/GeoObjectCollection/featureMember/0/GeoObject/Point/pos");
            String[] coordinates = pointNode.asText().split(" ");
            Double latitude = Double.parseDouble(coordinates[1]);
            Double longitude = Double.parseDouble(coordinates[0]);

            JsonNode addressNode = jsonNode.at("/response/GeoObjectCollection/featureMember/0/GeoObject/metaDataProperty/GeocoderMetaData/Address/formatted");
            String formattedAddress = addressNode.asText();

            Optional<Coordinates> existingCoordinatesOptional = coordinatesRepository.findByLatitudeAndLongitude(latitude, longitude);

            if (existingCoordinatesOptional.isPresent()) {
                log.info("Location with the same coordinates already exists. Skipping save operation.");
                return;
            }

            Coordinates coords = new Coordinates();
            coords.setLatitude(latitude);
            coords.setLongitude(longitude);
            coordinatesRepository.save(coords);

            Location location = new Location();
            location.setName(formattedAddress);
            location.setCoordinates(coords);
            locationRepository.save(location);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Location> getAllLocation() {
        return locationRepository.findAll();
    }

    @Override
    public Location findById(Long id) {
        log.info("Finding location by ID: {}", id);

        Location location = locationRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Location not found with ID: {}", id);
                    return new UsernameNotFoundException("Location not found with this id" + id);
                });

        log.info("Found location with ID: {}", id);
        return location;
    }

    @Override
    public void delete(Location location) {
        log.info("Deleting location: {}", location.getName());
        locationRepository.delete(location);
        log.info("Location deleted successfully: {}", location.getName());
    }

    public Page<Location> getAllLocation(int pageNo, int pageSize) {
        log.info("Fetching locations for page number: {} with page size: {}", pageNo, pageSize);
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Location> locations = locationRepository.findAll(pageable);
        log.info("Fetched {} locations", locations.getNumberOfElements());
        return locations;
    }


}
