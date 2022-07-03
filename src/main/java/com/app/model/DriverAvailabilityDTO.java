package com.app.model;

import com.app.enums.AvailabilityStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverAvailabilityDTO {
    private String d_id;
    private AvailabilityStatus availability_status;
}
