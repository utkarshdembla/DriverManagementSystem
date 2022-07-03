package com.app.repository;

import com.app.enums.AvailabilityStatus;
import com.app.exception.*;
import com.app.model.DriverAvailabilityDTO;
import com.app.model.DriverDTO;
import com.app.model.DriverSignInDTO;
import com.app.enums.SignInStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Repository
public class DriverRepoImp implements DriverRepo{

    private static final Map<String, DriverDTO> driverInfoRepo = new HashMap<>();
    private static final Map<String, DriverSignInDTO> driverSignInRepo = new HashMap<>();
    private static final Map<String, DriverAvailabilityDTO> driverAvailabilityRepo = new HashMap<>();

    @Override
    public DriverDTO getDriverInfo(String driverId) {
        return driverInfoRepo.get(driverId);
    }

    @Override
    public Map<String, DriverDTO> getDriverInfoRepo() {
        return driverInfoRepo;
    }

    @Override
    public Map<String, DriverSignInDTO> getDriverSignInRepo() {
        return driverSignInRepo;
    }

    @Override
    public Map<String, DriverAvailabilityDTO> getDriverAvailabilityRepo() {
        return driverAvailabilityRepo;
    }

    @Override
    public DriverSignInDTO updateDriverSignInStatus(String driverId,SignInStatus signInStatus) throws EntityNotFoundException {
        DriverSignInDTO driverSignInDTO = driverSignInRepo.get(driverId);

        if(driverSignInDTO!=null)
            driverSignInDTO.setD_signInStatus(signInStatus);
        else{
            String errorMessage = String.format("No driver available in DriverSignInRepo with driverId :: %s",driverId);
            log.error(errorMessage);
            throw new EntityNotFoundException(errorMessage);
        }
       return driverSignInDTO;
    }


    @Override
    public DriverAvailabilityDTO updateDriverAvailabilityStatus(String driverId,AvailabilityStatus availabilityStatus) throws EntityNotFoundException {
       DriverAvailabilityDTO driverAvailabilityDTO = driverAvailabilityRepo.get(driverId);

        if(driverAvailabilityDTO!=null)
            driverAvailabilityDTO.setAvailability_status(availabilityStatus);
        else{
            String errorMessage = String.format("No driver available in DriverAvailabilityRepo with driverId :: %s",driverId);
            log.error(errorMessage);
            throw new EntityNotFoundException(errorMessage);
        }
        return driverAvailabilityDTO;
    }

    @Override
    public DriverDTO saveDriverInfo(DriverDTO driverDTO) throws EntityPersistentException {
        try {
            log.info("Trying to save driver info in the database for driver :: {}",driverDTO.getD_id());
            driverInfoRepo.put(driverDTO.getD_id(),driverDTO);
            driverSignInRepo.put(driverDTO.getD_id(),new DriverSignInDTO(driverDTO.getD_id(),SignInStatus.SIGNED_OUT));
            driverAvailabilityRepo.put(driverDTO.getD_id(),new DriverAvailabilityDTO(driverDTO.getD_id(),AvailabilityStatus.NOT_AVAILABLE));
            log.info("Information saved for driver :: {}",driverDTO.getD_id());
            return driverDTO;
        }catch(Exception ex){
            String errorMessage = String.format("Not able to save driver info in the database for driver ==> %s for exception ==>%s",driverDTO.getD_id(),ex.getMessage());
            log.error(errorMessage);
            throw new EntityPersistentException(errorMessage);
        }
    }
}
