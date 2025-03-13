package com.okiri_george.kcb.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;



import java.math.BigDecimal;

@Data
public class B2CRequest {

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^(254|0)?(7(?:(?:[0-2]|[9][0-9]))[0-9]{7})$", message = "Invalid Kenyan phone number format")
    private String phoneNumber;

    @DecimalMin(value = "1.00", message = "Amount must be greater than or equal to 1.00")
    private BigDecimal amount;

    @NotBlank(message = "Reference is required")
    @Pattern(regexp = "^[a-zA-Z0-9]{1,20}$", message = "Reference must be alphanumeric and between 1 and 20 characters")
    private String narration;


}
