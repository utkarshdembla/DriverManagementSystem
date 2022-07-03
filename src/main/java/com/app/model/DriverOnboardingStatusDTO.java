package com.app.model;

import com.app.enums.OnboardingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverOnboardingStatusDTO {
    private String d_id;
    private OnboardingStatus ob_status;
}
