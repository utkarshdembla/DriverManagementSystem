package com.app.handler;

import com.app.enums.AvailabilityStatus;
import com.app.enums.OnboardingStatus;
import com.app.enums.SignInStatus;
import com.app.exception.*;
import com.app.model.*;
import com.app.repository.DriverRepo;
import com.app.repository.OnboardingRepo;
import com.app.request.DriverInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class DriverServiceHandlerImpl implements DriverServiceHandler{

    @Autowired
    private DriverRepo driverRepo;

    @Autowired
    private OnboardingRepo onboardingRepo;


    @Override
    public Map<String, DriverDTO>  getAllDriverInfo() {
        log.info("Getting all driver info..");
        return driverRepo.getDriverInfoRepo();
    }

    @Override
    public DriverDTO signUpService(DriverInfo driverInfo) throws EntityPersistentException, InvalidInputException, DriverAlreadyExists, InternalServerException {
        log.info("Saving information for Driver in the database...");
        try {

            if(driverInfo==null){
                String errorMessage = "Driver information not available for SignUp, please check again";
                log.error(errorMessage);
                throw new InvalidInputException(errorMessage);
            }

            if(driverRepo.getDriverInfo(driverInfo.getPhone())!=null){
                String errorMessage = String.format("Driver already exists for driver_id :: %s",driverInfo.getPhone());
                log.error(errorMessage);
                throw new DriverAlreadyExists(errorMessage);
            }

            DriverDTO driverDTO = new DriverDTO();
            driverDTO.setD_id(driverInfo.getPhone());
            driverDTO.setD_first_name(driverInfo.getD_first_name());
            driverDTO.setD_last_name(driverInfo.getD_last_name());
            driverDTO.setD_phone(driverInfo.getPhone());
            driverDTO.setD_email(driverInfo.getEmail());
            driverDTO.setD_car_details(new DriverCarDetailsDTO(driverInfo.getD_carCompany(),driverInfo.getD_carNumberPlate()));
            driverDTO.setD_legal_info(new DriverLegalInfoDTO(driverInfo.getD_licenseNumber(),driverInfo.getD_adhaarNumber(),driverInfo.getD_panNumber()));
            driverDTO.setD_address(new DriverAddressDTO(driverInfo.getD_address(),driverInfo.getD_city(),driverInfo.getD_state(),driverInfo.getD_pincode()));

            log.info("Saving driver info for phone :: {}",driverInfo.getPhone());
            return driverRepo.saveDriverInfo(driverDTO);
        }
        catch (EntityPersistentException | InvalidInputException | DriverAlreadyExists ex){
            throw ex;
        }
        catch (Exception ex){
            String errorMessage = String.format("Some internal error occurred for phone :: %s",driverInfo.getPhone());
            log.error(errorMessage);
            throw new InternalServerException(errorMessage);
        }
    }

    @Override
    public DriverSignInDTO signIn(String driverId) throws EntityNotFoundException, DriverSignInOutException, InvalidInputException, InternalServerException {
        try {
            if (driverId == null) {
                String errorMessage = "Driver information is not provided";
                log.error(errorMessage);
                throw new InvalidInputException(errorMessage);
            }
            log.info("Sign in request received for driver :: {}", driverId);
            DriverSignInDTO driverSignInDTO = driverRepo.getDriverSignInRepo().get(driverId);

            if(driverSignInDTO==null){
                String errorMessage = String.format("Driver not found for driverID :: %s", driverId);
                log.error(errorMessage);
                throw new EntityNotFoundException(errorMessage);
            }

            if(driverSignInDTO.getD_signInStatus().equals(SignInStatus.SIGNED_IN)){
                String errorMessage = String.format("Driver already signed in for driverID :: %s", driverId);
                log.error(errorMessage);
                throw new DriverSignInOutException(errorMessage);
            }

            driverSignInDTO.setD_signInStatus(SignInStatus.SIGNED_IN);
            driverRepo.updateDriverSignInStatus(driverId,driverSignInDTO.getD_signInStatus());

            log.info("Driver Sign In/Sign Out Status for driver_id :: {}, {}",driverId,driverSignInDTO.toString());
            return driverSignInDTO;
        }catch (EntityNotFoundException| DriverSignInOutException |InvalidInputException ex) {
            throw ex;
        }catch (Exception ex){
            String errorMessage = String.format("Some internal server error occurred for driverId :: %s",driverId);
            log.error(errorMessage);
            throw new InternalServerException(errorMessage);
        }
    }

    @Override
    public DriverSignInDTO signOut(String driverId) throws EntityNotFoundException, DriverSignInOutException, InvalidInputException, InternalServerException {
        try {
            if (driverId == null) {
                String errorMessage = "Driver information is not provided";
                log.error(errorMessage);
                throw new InvalidInputException(errorMessage);
            }
            log.info("Sign Out request received for driver :: {}", driverId);
            DriverSignInDTO driverSignInDTO = driverRepo.getDriverSignInRepo().get(driverId);

            if(driverSignInDTO==null){
                String errorMessage = String.format("Driver not found for driverID :: %s", driverId);
                log.error(errorMessage);
                throw new EntityNotFoundException(errorMessage);
            }

            if(driverSignInDTO.getD_signInStatus().equals(SignInStatus.SIGNED_OUT)){
                String errorMessage = String.format("Driver already signed out for driverID :: %s", driverId);
                log.error(errorMessage);
                throw new DriverSignInOutException(errorMessage);
            }

            driverSignInDTO.setD_signInStatus(SignInStatus.SIGNED_OUT);
            driverRepo.updateDriverSignInStatus(driverId,driverSignInDTO.getD_signInStatus());
            log.info("Driver Sign In/Sign Out Status for driver_id :: {}, {}",driverId,driverSignInDTO.toString());
            return driverSignInDTO;
        }catch (EntityNotFoundException| DriverSignInOutException |InvalidInputException ex) {
            throw ex;
        }catch (Exception ex){
            String errorMessage = String.format("Some internal server error occurred for driverId :: %s",driverId);
            log.error(errorMessage);
            throw new InternalServerException(errorMessage);
        }
    }

    @Override
    public DriverAvailabilityDTO markForRide(String driverId) throws InvalidInputException, DriverAvailableException, EntityNotFoundException, InternalServerException, InvalidOnboardingStepException, DriverSignInOutException {
        try {
            if (driverId == null) {
                String errorMessage = "Driver information not received";
                log.error(errorMessage);
                throw new InvalidInputException(errorMessage);
            }

            DriverOnboardingStatusDTO driverOnboardingStatusDTO = onboardingRepo.getOnboardingStatus(driverId);

            if(driverOnboardingStatusDTO==null){
                String errorMessage = String.format("Driver on boarding is not started for driverId :: %s", driverId);
                log.error(errorMessage);
                throw new EntityNotFoundException(errorMessage);
            }

            if(!driverOnboardingStatusDTO.getOb_status().equals(OnboardingStatus.COMPLETED)){
                String errorMessage = String.format("Driver on-boarding is not completed for driverId :: %s", driverId);
                log.error(errorMessage);
                throw new InvalidOnboardingStepException(errorMessage);
            }

            DriverSignInDTO driverSignInDTO = driverRepo.getDriverSignInRepo().get(driverId);

            if (driverSignInDTO == null) {
                String errorMessage = String.format("Driver record not found in SignInSignOut repo for driverId :: %s", driverId);
                log.error(errorMessage);
                throw new EntityNotFoundException(errorMessage);
            }

            if(!driverSignInDTO.getD_signInStatus().equals(SignInStatus.SIGNED_IN)){
                String errorMessage = String.format("Driver not signed in for driverId :: %s", driverId);
                log.error(errorMessage);
                throw new DriverSignInOutException(errorMessage);
            }

            DriverAvailabilityDTO driverAvailabilityDTO = driverRepo.getDriverAvailabilityRepo().get(driverId);

            if (driverAvailabilityDTO == null) {
                String errorMessage = String.format("Driver record not found in Availability Database for driverId :: %s", driverId);
                log.error(errorMessage);
                throw new EntityNotFoundException(errorMessage);
            }

            if (!driverAvailabilityDTO.getAvailability_status().equals(AvailabilityStatus.NOT_AVAILABLE)) {
                String errorMessage = String.format("Driver is already AVAILABLE to take rides for driverId :: %s", driverId);
                log.error(errorMessage);
                throw new DriverAvailableException(errorMessage);
            }

            driverAvailabilityDTO.setAvailability_status(AvailabilityStatus.AVAILABLE);
            log.info("Driver availability status is now changing to {}", AvailabilityStatus.AVAILABLE.value);
            driverRepo.updateDriverAvailabilityStatus(driverId, AvailabilityStatus.AVAILABLE);

            log.info("");
            return driverAvailabilityDTO;
        }catch(InvalidInputException| DriverSignInOutException |DriverAvailableException|EntityNotFoundException|InvalidOnboardingStepException ex){
            throw ex;
        } catch (Exception ex){
            String errorMessage = "Some internal server error occurred";
            log.error(errorMessage);
            throw new InternalServerException(errorMessage);
        }
    }

    @Override
    public DriverAvailabilityDTO markForNoRide(String driverId) throws InvalidInputException, DriverAvailableException, EntityNotFoundException, InternalServerException, DriverSignInOutException {
        try {
            if (driverId == null) {
                String errorMessage = "Driver information not received";
                log.error(errorMessage);
                throw new InvalidInputException(errorMessage);
            }

            DriverSignInDTO driverSignInDTO = driverRepo.getDriverSignInRepo().get(driverId);

            if (driverSignInDTO == null) {
                String errorMessage = String.format("Driver record not found in SignInSignOut repo for driverId :: %s", driverId);
                log.error(errorMessage);
                throw new EntityNotFoundException(errorMessage);
            }

            if(!driverSignInDTO.getD_signInStatus().equals(SignInStatus.SIGNED_IN)){
                String errorMessage = String.format("Driver not signed in for driverId :: %s", driverId);
                log.error(errorMessage);
                throw new DriverSignInOutException(errorMessage);
            }

            DriverAvailabilityDTO driverAvailabilityDTO = driverRepo.getDriverAvailabilityRepo().get(driverId);

            if (driverAvailabilityDTO == null) {
                String errorMessage = String.format("Driver record not found in Availability Database for driverId :: %s", driverId);
                log.error(errorMessage);
                throw new EntityNotFoundException(errorMessage);
            }

            if (driverAvailabilityDTO.getAvailability_status().equals(AvailabilityStatus.NOT_AVAILABLE)) {
                String errorMessage = String.format("Driver is already NOT_AVAILABLE to take rides for driverId :: %s", driverId);
                log.error(errorMessage);
                throw new DriverAvailableException(errorMessage);
            }

            log.info("Driver availability status is now changing to {}", AvailabilityStatus.NOT_AVAILABLE.value);
            driverRepo.updateDriverAvailabilityStatus(driverId, AvailabilityStatus.NOT_AVAILABLE);

            log.info("Driver Availability Status for driver_id :: {}, {}",driverId,driverAvailabilityDTO.toString());
            return driverAvailabilityDTO;
        }catch (InvalidInputException|DriverSignInOutException |DriverAvailableException|EntityNotFoundException ex){
            throw ex;
        }catch (Exception ex){
            String errorMessage = String.format("Some Internal server error occurred while marking driver :: %s for no Ride",driverId);
            log.error(errorMessage);
            throw new InternalServerException(errorMessage);
        }
    }

    @Override
    public DriverOnboardingStatusDTO sendDriverDocument(String driverId) throws EntityNotFoundException, InvalidInputException, InternalServerException, InvalidOnboardingStepException {
       try {
           if (driverId == null) {
               String errorMessage = "Driver information not found int the request ";
               log.error(errorMessage);
               throw new InvalidInputException(errorMessage);
           }

           if(driverRepo.getDriverInfo(driverId)==null){
               String errorMessage = String.format("Driver information not found for driverId :: %s",driverId);
               log.error(errorMessage);
               throw new EntityNotFoundException(errorMessage);
           }

           if (onboardingRepo.getOnboardingStatus(driverId) != null) {
               String errorMessage = String.format("Docs have been sent for verification already for driverId:: %s", driverId);
               log.error(errorMessage);
               throw new InvalidOnboardingStepException(errorMessage);
           }

           DriverOnboardingStatusDTO driverOnboardingStatusDTO = new DriverOnboardingStatusDTO(driverId, OnboardingStatus.findByValue(1));

           log.info("Updating on boarding status for driver :: {}",driverId);
           onboardingRepo.updateOnboardingStatus(driverId, driverOnboardingStatusDTO.getOb_status());

           return driverOnboardingStatusDTO;
       }catch (EntityNotFoundException|InvalidInputException|InvalidOnboardingStepException ex){ throw ex; }
       catch (Exception ex){
           String errorMessage = String.format("Some internal server occurred... while sending document for Driver for driver_id :: %s",driverId);
           log.error(errorMessage);
           throw new InternalServerException(errorMessage);
       }
    }
}
