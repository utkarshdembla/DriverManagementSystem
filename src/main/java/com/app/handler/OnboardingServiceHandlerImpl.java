package com.app.handler;

import com.app.enums.OnboardingStatus;
import com.app.exception.EntityNotFoundException;
import com.app.exception.InternalServerException;
import com.app.exception.InvalidInputException;
import com.app.exception.InvalidOnboardingStepException;
import com.app.model.DriverOnboardingStatusDTO;
import com.app.repository.OnboardingRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public class OnboardingServiceHandlerImpl implements OnboardingServiceHandler{

    @Autowired
    private OnboardingRepo onboardingRepo;

    @Override
    public DriverOnboardingStatusDTO getOnboardingStatus(String driverId) throws EntityNotFoundException, InternalServerException {
        try {
            DriverOnboardingStatusDTO driverOnboardingStatusDTO = onboardingRepo.getOnboardingStatus(driverId);

            if (driverOnboardingStatusDTO == null) {
                String errorMessage = String.format("On boarding process not started for driverId :: %s", driverId);
                log.error(errorMessage);
                throw new EntityNotFoundException(errorMessage);
            }

            log.info("Driver on boarding status for driverId :: {} ==> {}",driverId,driverOnboardingStatusDTO.toString());
            return driverOnboardingStatusDTO;
        }catch (EntityNotFoundException ex){ throw ex;}
        catch (Exception ex){
            String errorMessage = String.format("Some internal server occurred, while fetching on boarding status of driverId :: %s",driverId);
            log.error(errorMessage);
            throw new InternalServerException(errorMessage);
        }
    }

    @Override
    public DriverOnboardingStatusDTO updateOnboardingStatus(String driverId) throws InvalidInputException, EntityNotFoundException, InvalidOnboardingStepException, InternalServerException {

      try {
          if (driverId == null) {
              String errorMessage = "Driver information not received";
              log.error(errorMessage);
              throw new InvalidInputException(errorMessage);
          }

          DriverOnboardingStatusDTO driverOnboardingStatusDTO = onboardingRepo.getOnboardingStatus(driverId);

          if (driverOnboardingStatusDTO == null) {
              String errorMessage = String.format("Driver documents not found for driverId :: %s", driverId);
              log.error(errorMessage);
              throw new EntityNotFoundException(errorMessage);
          }
          OnboardingStatus onboardingStatus = driverOnboardingStatusDTO.getOb_status();
          int step = onboardingStatus.value;
          log.info("Current Onboarding status :: {}", OnboardingStatus.findByValue(step));

          OnboardingStatus onboardingStatusUpdated = OnboardingStatus.findByValue(step + 1);

          if (onboardingStatusUpdated == null) {
              String errorMessage = String.format("On boarding for driverId :: %s has already been completed..", driverId);
              log.error(errorMessage);
              throw new InvalidOnboardingStepException(errorMessage);
          }

          driverOnboardingStatusDTO.setOb_status(onboardingStatusUpdated);
          onboardingRepo.updateOnboardingStatus(driverId, onboardingStatusUpdated);

          log.info("Driver On boarding current status for driverId :: {}, is {}", driverId, driverOnboardingStatusDTO.toString());
          return driverOnboardingStatusDTO;
      }catch (InvalidInputException| EntityNotFoundException| InvalidOnboardingStepException| InternalServerException ex){throw ex;}
      catch (Exception ex){
          String errorMessage = String.format("Some internal server error occurred while updating status for driverId :: %s",driverId);
          log.error(errorMessage);
          throw new InternalServerException(errorMessage);
      }
    }
}
