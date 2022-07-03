package com.app.controller;

import com.app.exception.*;
import com.app.model.DriverAvailabilityDTO;
import com.app.model.DriverDTO;
import com.app.model.DriverOnboardingStatusDTO;
import com.app.model.DriverSignInDTO;
import com.app.request.DriverInfo;
import com.app.response.MetaData;
import com.app.response.Response;
import com.app.service.DriverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/driver")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @PostMapping("/signUp")
    public Response signUp(@RequestBody DriverInfo driverInfo) throws EntityPersistentException, InvalidInputException, DriverAlreadyExists, InternalServerException {
        DriverDTO output =  driverService.signUpService(driverInfo);
        return new Response(output,new MetaData(HttpStatus.CREATED,"Sign up successful"));
    }

    @PostMapping("/signIn/{driverId}")
    public Response signIn(@PathVariable("driverId") String driverId) throws EntityNotFoundException, DriverSignInOutException, InvalidInputException, InternalServerException {
        DriverSignInDTO output =  driverService.signIn(driverId);
        return new Response(output,new MetaData(HttpStatus.ACCEPTED,"Signed In"));
    }

    @PostMapping("/signOut/{driverId}")
    public Response signOut(@PathVariable("driverId") String driverId) throws EntityNotFoundException, DriverSignInOutException, InvalidInputException, InternalServerException {
        DriverSignInDTO output =  driverService.signOut(driverId);
        return new Response(output,new MetaData(HttpStatus.ACCEPTED,"Signed Out"));
    }

    @PostMapping("/markForRide/{driverId}")
    public Response readyToTakeRide(@PathVariable("driverId") String driverId) throws InvalidInputException, DriverAvailableException, EntityNotFoundException, InternalServerException, InvalidOnboardingStepException, DriverSignInOutException {
        DriverAvailabilityDTO output =  driverService.markForRide(driverId);
        return new Response(output,new MetaData(HttpStatus.ACCEPTED,"Driver marked as ready to take ride"));
    }

    @PostMapping("/markForNoRide/{driverId}")
    public Response notReadyToTakeRide(@PathVariable("driverId") String driverId) throws InvalidInputException, DriverAvailableException, EntityNotFoundException, InternalServerException, DriverSignInOutException, InvalidOnboardingStepException {
        DriverAvailabilityDTO output =  driverService.markForNoRide(driverId);
        return new Response(output,new MetaData(HttpStatus.ACCEPTED,"Driver marked as not ready to take ride"));
    }

    @PostMapping("/sendDocs/{driverId}")
    public Response sendDocuments(@PathVariable("driverId") String driverId) throws InvalidRequestException, EntityNotFoundException, InvalidInputException, InternalServerException, InvalidOnboardingStepException {
        DriverOnboardingStatusDTO output =  driverService.sendDriverDocument(driverId);
        return new Response(output,new MetaData(HttpStatus.ACCEPTED,"Docs for driver sent"));
    }
}
