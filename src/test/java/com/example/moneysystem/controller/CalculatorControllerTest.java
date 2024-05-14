package com.example.moneysystem.controller;


import com.example.moneysystem.domain.SterlingCurrency;
import com.example.moneysystem.dto.BaseDto;
import com.example.moneysystem.dto.TwoOperandsBody;
import com.example.moneysystem.mapper.Mapper;
import com.example.moneysystem.service.CalculatorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(CalculatorController.class)
@ExtendWith(SpringExtension.class)
public class CalculatorControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CalculatorService calculatorService;
    @MockBean
    private Mapper mapper;

    private JacksonTester<TwoOperandsBody> twoOperandsBodyJacksonTester;
    private JacksonTester<BaseDto> baseDtoJacksonTester;
    private JacksonTester<String[]> stringArrayJacksonTester;

    @BeforeEach
    public void setup() {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void sumOkTest() throws Exception {
        TwoOperandsBody twoOperandsBody = new TwoOperandsBody();
        twoOperandsBody.setData("5p 17s 8d");
        twoOperandsBody.setSecond("3p 4s 10d");
        SterlingCurrency result = new SterlingCurrency(6, 2, 9);
        SterlingCurrency first = new SterlingCurrency(8, 17, 5);
        SterlingCurrency second = new SterlingCurrency(10, 4, 3);
        BaseDto baseDto = new BaseDto() {{
            setData("9p 2s 6d");
        }};
        given(calculatorService.sum(eq(first), eq(second))).willReturn(result);
        given(mapper.toSterlingCurrency(eq("5p 17s 8d"))).willReturn(first);
        given(mapper.toSterlingCurrency(eq("3p 4s 10d"))).willReturn(second);
        given(mapper.toString(eq(result))).willReturn("9p 2s 6d");

        MockHttpServletResponse response = mockMvc.perform(
                        post("/api/calculations/sum")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(twoOperandsBodyJacksonTester.write(twoOperandsBody).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(baseDtoJacksonTester.write(baseDto).getJson());

    }

    @Test
    public void subtractOKTest() throws Exception {
        TwoOperandsBody twoOperandsBody = new TwoOperandsBody();
        twoOperandsBody.setData("5p 17s 8d");
        twoOperandsBody.setSecond("3p 4s 10d");
        SterlingCurrency result = new SterlingCurrency(10, 12, 2);
        SterlingCurrency first = new SterlingCurrency(8, 17, 5);
        SterlingCurrency second = new SterlingCurrency(10, 4, 3);
        BaseDto baseDto = new BaseDto() {{
            setData("2p 12s 10d");
        }};
        given(calculatorService.subtract(eq(first), eq(second))).willReturn(result);
        given(mapper.toSterlingCurrency(eq("5p 17s 8d"))).willReturn(first);
        given(mapper.toSterlingCurrency(eq("3p 4s 10d"))).willReturn(second);
        given(mapper.toString(eq(result))).willReturn("2p 12s 10d");

        MockHttpServletResponse response = mockMvc.perform(
                        post("/api/calculations/subtract")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(twoOperandsBodyJacksonTester.write(twoOperandsBody).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(baseDtoJacksonTester.write(baseDto).getJson());

    }

    @Test
    public void multiplyOKTest() throws Exception {
        BaseDto body = new BaseDto() {{
            setData("5p 17s 8d");
        }};
        int i = 2;
        SterlingCurrency result = new SterlingCurrency(4, 15, 11);
        SterlingCurrency first = new SterlingCurrency(8, 17, 5);
        BaseDto expected = new BaseDto() {{
            setData("11p 15s 4d");
        }};

        given(calculatorService.multiply(eq(first), eq(i))).willReturn(result);
        given(mapper.toSterlingCurrency(eq("5p 17s 8d"))).willReturn(first);
        given(mapper.toString(eq(result))).willReturn("11p 15s 4d");

        MockHttpServletResponse response = mockMvc.perform(
                        post("/api/calculations/multiply")
                                .queryParam("i", "" + i)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(baseDtoJacksonTester.write(body).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(baseDtoJacksonTester.write(expected).getJson());

    }

    @Test
    public void divideOKTest() throws Exception {
        BaseDto body = new BaseDto() {{
            setData("5p 17s 8d");
        }};
        int i = 3;
        SterlingCurrency result = new SterlingCurrency(2, 19, 1);
        SterlingCurrency reminder = new SterlingCurrency(2, 0, 0);
        SterlingCurrency first = new SterlingCurrency(8, 17, 5);
        BaseDto expected = new BaseDto() {{
           setData("1p 19s 2d (2p)");
        }};
        given(calculatorService.divide(eq(first), eq(i))).willReturn(new SterlingCurrency[]{result, reminder});
        given(mapper.toSterlingCurrency(eq("5p 17s 8d"))).willReturn(first);
        given(mapper.toString(eq(result), eq(reminder))).willReturn("1p 19s 2d (2p)");

        MockHttpServletResponse response = mockMvc.perform(
                        post("/api/calculations/divide")
                                .queryParam("i", "" + i)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(baseDtoJacksonTester.write(body).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(baseDtoJacksonTester.write(expected).getJson());

    }

    @Test
    public void sumBadRequestTest() throws Exception {
        TwoOperandsBody twoOperandsBody = new TwoOperandsBody();
        twoOperandsBody.setData("5p17s 8d");
        twoOperandsBody.setSecond("3p4s 10d");

        MockHttpServletResponse response = mockMvc.perform(
                        post("/api/calculations/sum")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(twoOperandsBodyJacksonTester.write(twoOperandsBody).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void subtractBadRequestTest() throws Exception {
        TwoOperandsBody twoOperandsBody = new TwoOperandsBody();
        twoOperandsBody.setData("5p17s 8d");
        twoOperandsBody.setSecond("3p4s 10d");

        MockHttpServletResponse response = mockMvc.perform(
                        post("/api/calculations/subtract")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(twoOperandsBodyJacksonTester.write(twoOperandsBody).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    public void multiplyBadRequestTest() throws Exception {
        BaseDto body = new BaseDto() {{
            setData("5p17s 8d");
        }};
        int i = -2;

        MockHttpServletResponse response = mockMvc.perform(
                        post("/api/calculations/multiply")
                                .queryParam("i", "" + i)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(baseDtoJacksonTester.write(body).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void divideBadRequestTest() throws Exception {
        BaseDto body = new BaseDto() {{
            setData("5p17s 8d");
        }};
        int i = -2;

        MockHttpServletResponse response = mockMvc.perform(
                        post("/api/calculations/divide")
                                .queryParam("i", "" + i)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(baseDtoJacksonTester.write(body).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

}
