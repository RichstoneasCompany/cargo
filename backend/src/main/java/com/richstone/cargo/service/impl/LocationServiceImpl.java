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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private static final String API_KEY = "622786fb-36bc-431e-8a2f-828b71dac7a9";
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

}
