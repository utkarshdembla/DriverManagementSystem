package com.app.service;

import com.app.exception.EntityNotFoundException;
import com.app.exception.InternalServerException;
import com.app.exception.InvalidInputException;
import com.app.exception.InvalidOnboardingStepException;
import com.app.handler.OnboardingServiceHandler;
import com.app.model.DriverOnboardingStatusDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OnboardingServiceImpl implements OnboardingService{

    @Autowired
    private OnboardingServiceHandler onboardingServiceHandler;

    @Override
    public DriverOnboardingStatusDTO getOnboardingStatus(String driverId) throws InternalServerException, EntityNotFoundException {
        return onboardingServiceHandler.getOnboardingStatus(driverId);
    }

    @Override
    public DriverOnboardingStatusDTO updateOnboardingStatus(String driverId) throws InvalidInputException, EntityNotFoundException, InvalidOnboardingStepException, InternalServerException {
        return onboardingServiceHandler.updateOnboardingStatus(driverId);
    }
}
