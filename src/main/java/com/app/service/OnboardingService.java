package com.app.service;

import com.app.exception.EntityNotFoundException;
import com.app.exception.InternalServerException;
import com.app.exception.InvalidInputException;
import com.app.exception.InvalidOnboardingStepException;
import com.app.model.DriverOnboardingStatusDTO;
import org.springframework.stereotype.Service;

@Service
public interface OnboardingService {
    DriverOnboardingStatusDTO getOnboardingStatus(String driverId) throws InternalServerException, EntityNotFoundException;
    DriverOnboardingStatusDTO updateOnboardingStatus(String driverId) throws InvalidInputException, EntityNotFoundException, InvalidOnboardingStepException, InternalServerException;
}
