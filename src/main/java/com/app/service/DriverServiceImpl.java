package com.app.service;

import com.app.exception.*;
import com.app.handler.DriverServiceHandler;
import com.app.model.DriverAvailabilityDTO;
import com.app.model.DriverDTO;
import com.app.model.DriverOnboardingStatusDTO;
import com.app.model.DriverSignInDTO;
import com.app.request.DriverInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class DriverServiceImpl implements DriverService {

    @Autowired
    private DriverServiceHandler driverServiceHandler;

    public Map<String,DriverDTO> getAllDriverInfo(){
        return driverServiceHandler.getAllDriverInfo();
    }

    @Override
    public DriverDTO signUpService(DriverInfo driverInfo) throws EntityPersistentException, InvalidInputException, DriverAlreadyExists, InternalServerException {
        return driverServiceHandler.signUpService(driverInfo);
    }

    @Override
    public DriverSignInDTO signIn(String driverId) throws EntityNotFoundException, DriverSignInOutException, InvalidInputException, InternalServerException {
        return driverServiceHandler.signIn(driverId);
    }

    @Override
    public DriverSignInDTO signOut(String driverId) throws EntityNotFoundException, DriverSignInOutException, InvalidInputException, InternalServerException {
        return driverServiceHandler.signOut(driverId);
    }


    @Override
    public DriverAvailabilityDTO markForRide(String driverId) throws InvalidInputException, DriverAvailableException, EntityNotFoundException, InternalServerException, InvalidOnboardingStepException, DriverSignInOutException {
        return driverServiceHandler.markForRide(driverId);
    }

    @Override
    public DriverAvailabilityDTO markForNoRide(String driverId) throws InvalidInputException, DriverAvailableException, EntityNotFoundException, InternalServerException, DriverSignInOutException, InvalidOnboardingStepException {
        return driverServiceHandler.markForNoRide(driverId);
    }

    @Override
    public DriverOnboardingStatusDTO sendDriverDocument(String driverId) throws EntityNotFoundException, InvalidInputException, InternalServerException, InvalidRequestException, InvalidOnboardingStepException {
        return driverServiceHandler.sendDriverDocument(driverId);
    }
}
