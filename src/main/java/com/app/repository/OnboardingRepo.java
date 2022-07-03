package com.app.repository;

import com.app.enums.OnboardingStatus;
import com.app.exception.EntityNotFoundException;
import com.app.exception.InternalServerException;
import com.app.exception.InvalidInputException;
import com.app.model.DriverOnboardingStatusDTO;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
public interface OnboardingRepo {
    DriverOnboardingStatusDTO getOnboardingStatus(String driverId) throws EntityNotFoundException, InternalServerException;
    DriverOnboardingStatusDTO updateOnboardingStatus(String driverId,OnboardingStatus updatedOnboardingStatus) throws EntityNotFoundException, InvalidInputException, InternalServerException;
}
