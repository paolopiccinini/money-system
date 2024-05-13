package com.example.moneysystem.dto;

import com.example.moneysystem.constants.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class BaseDto {

    @NotBlank(message = "data can't be and empty string")
    @Pattern(regexp = Constants.VALIDATION_REGEX, message = "data must follow the pattern XP Ys Zd")
    private String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
