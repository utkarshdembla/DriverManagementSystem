package com.app.handler;

import com.app.exception.*;
import com.app.model.DriverAvailabilityDTO;
import com.app.model.DriverDTO;
import com.app.model.DriverOnboardingStatusDTO;
import com.app.model.DriverSignInDTO;
import com.app.request.DriverInfo;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface DriverServiceHandler {
    Map<String,DriverDTO> getAllDriverInfo();
    DriverDTO signUpService(DriverInfo driverInfo) throws EntityPersistentException, InvalidInputException, DriverAlreadyExists, InternalServerException;
    DriverSignInDTO signIn(String driverId) throws EntityNotFoundException, DriverSignInOutException, InvalidInputException, InternalServerException;
    DriverSignInDTO signOut(String driverId) throws EntityNotFoundException, DriverSignInOutException, InvalidInputException, InternalServerException;
    DriverAvailabilityDTO markForRide(String driverId) throws InvalidInputException, DriverAvailableException, EntityNotFoundException, InternalServerException, InvalidOnboardingStepException, DriverSignInOutException;
    DriverAvailabilityDTO markForNoRide(String driverId) throws InvalidInputException, DriverAvailableException, EntityNotFoundException, InternalServerException, DriverSignInOutException;
    DriverOnboardingStatusDTO sendDriverDocument(String driverId) throws EntityNotFoundException, InvalidRequestException, InvalidInputException, InternalServerException, InvalidOnboardingStepException;

}
