package com.app.repository;

import com.app.enums.AvailabilityStatus;
import com.app.enums.SignInStatus;
import com.app.exception.*;
import com.app.model.DriverAvailabilityDTO;
import com.app.model.DriverDTO;
import com.app.model.DriverSignInDTO;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface DriverRepo {
     DriverDTO getDriverInfo(String driverId);
     Map<String,DriverDTO> getDriverInfoRepo();
     Map<String, DriverSignInDTO> getDriverSignInRepo();
     Map<String, DriverAvailabilityDTO> getDriverAvailabilityRepo();
     DriverSignInDTO updateDriverSignInStatus(String driverId,SignInStatus signInStatus) throws EntityNotFoundException;
     DriverAvailabilityDTO updateDriverAvailabilityStatus(String driverId, AvailabilityStatus availabilityStatus) throws EntityNotFoundException;
     DriverDTO saveDriverInfo(DriverDTO driverDTO) throws EntityPersistentException;
}
