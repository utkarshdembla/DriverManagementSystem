package com.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverLegalInfoDTO {
    private String d_licenseNumber;
    private String d_adhaarNumber;
    private String d_panNumber;
}
