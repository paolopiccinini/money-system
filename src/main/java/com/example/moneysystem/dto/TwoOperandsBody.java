package com.example.moneysystem.dto;

import com.example.moneysystem.constants.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class TwoOperandsBody extends BaseDto {

    @NotBlank(message = "second can't be and empty string")
    @Pattern(regexp = Constants.VALIDATION_REGEX, message = "second must follow the pattern XP Ys Zd")
    private String second;

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }

}
