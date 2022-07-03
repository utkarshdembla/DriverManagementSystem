package com.app.repository;

import com.app.enums.OnboardingStatus;
import com.app.exception.EntityNotFoundException;
import com.app.exception.InternalServerException;
import com.app.exception.InvalidInputException;
import com.app.model.DriverOnboardingStatusDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Repository
public class OnboardingRepoImpl implements OnboardingRepo{

    private static final Map<String, DriverOnboardingStatusDTO> driverOnboardingStatusDb = new HashMap<>();

    @Override
    public DriverOnboardingStatusDTO getOnboardingStatus(String driverId) {
            return driverOnboardingStatusDb.get(driverId);
    }

    @Override
    public DriverOnboardingStatusDTO updateOnboardingStatus(String driverId,OnboardingStatus updatedOnboardingStatus) throws InternalServerException {
        try {
            log.info("Received request for updating on boarding status for driverId :: {}, and on boarding status :: {}",driverId,updatedOnboardingStatus);

            DriverOnboardingStatusDTO driverOnboardingStatusDTO = driverOnboardingStatusDb.get(driverId);
            if (driverOnboardingStatusDTO == null) {
                driverOnboardingStatusDTO = new DriverOnboardingStatusDTO(driverId, updatedOnboardingStatus);
            }
            driverOnboardingStatusDTO.setOb_status(updatedOnboardingStatus);
            driverOnboardingStatusDb.put(driverId, driverOnboardingStatusDTO);

            return driverOnboardingStatusDTO;
        }
        catch (Exception ex){
            String errorMessage = String.format("Some internal server occurred while updating status for driverId :: {} to on boarding status :: {}",driverId,updatedOnboardingStatus);
            log.error(errorMessage);
            throw new InternalServerException(errorMessage);
        }
    }
}
