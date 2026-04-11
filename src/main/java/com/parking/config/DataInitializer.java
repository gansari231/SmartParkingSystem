package com.parking.config;

import com.parking.entity.ParkingSpot;
import com.parking.enums.SpotType;
import com.parking.repository.ParkingSpotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ParkingSpotRepository spotRepository;

    @Override
    public void run(String... args) {

        if (spotRepository.count() == 0) {

            ParkingSpot s1 = new ParkingSpot();
            s1.setSpotNumber("A1");
            s1.setSpotType(SpotType.CAR);

            ParkingSpot s2 = new ParkingSpot();
            s2.setSpotNumber("A2");
            s2.setSpotType(SpotType.CAR);

            ParkingSpot s3 = new ParkingSpot();
            s3.setSpotNumber("B1");
            s3.setSpotType(SpotType.BIKE);

            ParkingSpot s4 = new ParkingSpot();
            s4.setSpotNumber("A3");
            s4.setSpotType(SpotType.CAR);

            spotRepository.save(s1);
            spotRepository.save(s2);
            spotRepository.save(s3);
            spotRepository.save(s4);
        }
    }
}