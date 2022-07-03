package com.app.controller;

import com.app.exception.EntityNotFoundException;
import com.app.exception.InternalServerException;
import com.app.exception.InvalidInputException;
import com.app.exception.InvalidOnboardingStepException;
import com.app.model.DriverDTO;
import com.app.model.DriverOnboardingStatusDTO;
import com.app.response.MetaData;
import com.app.response.Response;
import com.app.service.DriverService;
import com.app.service.OnboardingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/staff")
public class OnboardingController {

    @Autowired
    private OnboardingService onboardingService;

    @Autowired
    private DriverService driverService;

    @GetMapping("/getAllDriverInfo")
    public Response getDriverInfo() {
        Map<String, DriverDTO> output = driverService.getAllDriverInfo();
        return new Response(output,new MetaData(HttpStatus.OK,"Driver info retrieved"));
    }

    @GetMapping("/getOnboardingStatus/{driverId}")
    public Response getOnboardingStatus(@PathVariable("driverId") String driverId) throws InvalidInputException, EntityNotFoundException, InvalidOnboardingStepException, InternalServerException {
        DriverOnboardingStatusDTO output = onboardingService.getOnboardingStatus(driverId);
        return new Response(output,new MetaData(HttpStatus.OK,"On boarding status retrieved"));
    }

    @PostMapping("/updateOnboardingStatus/{driverId}")
    public Response updateOnboardingStatus(@PathVariable("driverId") String driverId) throws InvalidInputException, EntityNotFoundException, InvalidOnboardingStepException, InternalServerException {
         DriverOnboardingStatusDTO output = onboardingService.updateOnboardingStatus(driverId);
         return new Response(output,new MetaData(HttpStatus.CREATED,"On boarding status updated"));
    }
}
