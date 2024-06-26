package com.example.moneysystem.controller;

import com.example.moneysystem.constants.Constants;
import com.example.moneysystem.domain.SterlingCurrency;
import com.example.moneysystem.dto.BaseDto;
import com.example.moneysystem.dto.TwoOperandsBody;
import com.example.moneysystem.mapper.Mapper;
import com.example.moneysystem.service.CalculatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Calculations", description = "Calculations management APIs")
@RestController
@RequestMapping("api/calculations")
public class CalculatorController {

    private final Logger logger;
    private final CalculatorService calculatorService;
    private final Mapper mapper;

    public CalculatorController(CalculatorService calculatorService, Mapper mapper) {
        this.calculatorService = calculatorService;
        this.mapper = mapper;
        logger = LoggerFactory.getLogger(CalculatorController.class);
    }

    @Operation(
            summary = "Sums data to second",
            description = "Sums data to second and returns an XP Ys Zd representation."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = BaseDto.class), mediaType = Constants.VERSION_1_HEADER) }),
            @ApiResponse(responseCode = "400", content = { @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)), mediaType = Constants.VERSION_1_HEADER) }),
            @ApiResponse(responseCode = "415", content = { @Content(schema = @Schema(ref = "#/components/schemas/Map"), mediaType = Constants.VERSION_1_HEADER) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(ref = "#/components/schemas/Map"), mediaType = Constants.VERSION_1_HEADER) }),
    })
    @PostMapping(value = "/sum", produces = {Constants.VERSION_1_HEADER})
    public BaseDto sumV1(@RequestBody @Valid TwoOperandsBody body) {
        SterlingCurrency first = mapper.toSterlingCurrency(body.getData());
        SterlingCurrency second = mapper.toSterlingCurrency(body.getSecond());
        logger.info("summing first : " + body.getData() + " second: " + body.getSecond());
        SterlingCurrency result = calculatorService.sum(first, second);
        String resultString = mapper.toString(result);
        logger.info("result " + resultString);
        // I think default constructor is needed for jakson
        // so I used it
        return new BaseDto() {{
            setData(resultString);
        }};
    }

    @Operation(
            summary = "Subtract data to second",
            description = "Subtract data to second and returns an XP Ys Zd representation."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = BaseDto.class), mediaType = Constants.VERSION_1_HEADER) }),
            @ApiResponse(responseCode = "400", content = { @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)), mediaType = Constants.VERSION_1_HEADER) }),
            @ApiResponse(responseCode = "415", content = { @Content(schema = @Schema(ref = "#/components/schemas/Map"), mediaType = Constants.VERSION_1_HEADER) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(ref = "#/components/schemas/Map"), mediaType = Constants.VERSION_1_HEADER) }),
    })
    @PostMapping(value = "/subtract", produces = {Constants.VERSION_1_HEADER})
    public BaseDto subtractV1(@RequestBody @Valid TwoOperandsBody body) {
        SterlingCurrency first = mapper.toSterlingCurrency(body.getData());
        SterlingCurrency second = mapper.toSterlingCurrency(body.getSecond());
        logger.info("subtracting first : " + body.getData() + " second: " + body.getSecond());
        SterlingCurrency result = calculatorService.subtract(first, second);
        String resultString = mapper.toString(result);
        logger.info("result " + resultString);
        return new BaseDto() {{
            setData(resultString);
        }};
    }

    @Operation(
            summary = "Multiply the request body by i",
            description = "Multiply the request body by times and returns an XP Ys Zd representation."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = BaseDto.class), mediaType = Constants.VERSION_1_HEADER) }),
            @ApiResponse(responseCode = "400", content = { @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)), mediaType = Constants.VERSION_1_HEADER) }),
            @ApiResponse(responseCode = "415", content = { @Content(schema = @Schema(ref = "#/components/schemas/Map"), mediaType = Constants.VERSION_1_HEADER) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(ref = "#/components/schemas/Map"), mediaType = Constants.VERSION_1_HEADER) }),
    })
    @PostMapping(value = "/multiply", produces = {Constants.VERSION_1_HEADER})
    public BaseDto multiplyV1(@RequestBody
                                 @Valid
                                 BaseDto body,
                           @RequestParam("i")
                           @Valid
                           @Min(value = 0, message = "i must be greater than or equals to 0")
                           int i) {
        SterlingCurrency toMultiply = mapper.toSterlingCurrency(body.getData());
        logger.info("multiplying : " + body.getData() + " by: " + i);
        SterlingCurrency result = calculatorService.multiply(toMultiply, i);
        String resultString = mapper.toString(result);
        logger.info("result " + resultString);
        return new BaseDto() {{
            setData(resultString);
        }};
    }

    @Operation(
            summary = "Divide the request body by i",
            description = "Multiply the request body by times and returns an XP Ys Zd representation."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = BaseDto.class), mediaType = Constants.VERSION_1_HEADER) }),
            @ApiResponse(responseCode = "400", content = { @Content(array = @ArraySchema(schema = @Schema(implementation = String.class)), mediaType = Constants.VERSION_1_HEADER) }),
            @ApiResponse(responseCode = "415", content = { @Content(schema = @Schema(ref = "#/components/schemas/Map"), mediaType = Constants.VERSION_1_HEADER) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(ref = "#/components/schemas/Map"), mediaType = Constants.VERSION_1_HEADER) }),
    })
    @PostMapping(value = "/divide", produces = {Constants.VERSION_1_HEADER})
    public BaseDto divideV1(@RequestBody
                             @Valid
                             BaseDto body,
                         @RequestParam("i")
                         @Valid
                         @Min(value = 1, message = "i must be greater than or equals to 1")
                         int i) {
        SterlingCurrency toDivide = mapper.toSterlingCurrency(body.getData());
        logger.info("dividing : " + body.getData() + " by: " + i);
        SterlingCurrency[] result = calculatorService.divide(toDivide, i);
        String resultString = mapper.toString(result[0], result[1]);
        logger.info("result " + resultString);
        return new BaseDto() {{
            setData(resultString);
        }};
    }

}
