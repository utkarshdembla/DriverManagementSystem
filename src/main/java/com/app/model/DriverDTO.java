package com.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDTO {
    private String d_id;
    private String d_first_name;
    private String d_last_name;
    private String d_phone;
    private String d_email;
    private DriverAddressDTO d_address;
    private DriverCarDetailsDTO d_car_details;
    private DriverLegalInfoDTO d_legal_info;
}
