package com.app.handler;

import com.app.exception.EntityNotFoundException;
import com.app.exception.InternalServerException;
import com.app.exception.InvalidInputException;
import com.app.exception.InvalidOnboardingStepException;
import com.app.model.DriverOnboardingStatusDTO;
import org.springframework.stereotype.Service;

@Service
public interface OnboardingServiceHandler {
    DriverOnboardingStatusDTO getOnboardingStatus(String driverId) throws EntityNotFoundException, InternalServerException;
    DriverOnboardingStatusDTO updateOnboardingStatus(String driverId) throws InvalidInputException, EntityNotFoundException, InvalidOnboardingStepException, InternalServerException;
}
