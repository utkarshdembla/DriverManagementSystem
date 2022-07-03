package com.app.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverInfo {
    private String d_first_name;
    private String d_last_name;
    private String phone;
    private String email;
    private String d_licenseNumber;
    private String d_adhaarNumber;
    private String d_panNumber;
    private String d_carCompany;
    private String d_carNumberPlate;
    private String d_address;
    private String d_city;
    private String d_state;
    private String d_pincode;
}
