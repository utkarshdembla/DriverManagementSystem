package com.app.model;

import com.app.enums.SignInStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverSignInDTO {
    private String d_id;
    private SignInStatus d_signInStatus;
}
