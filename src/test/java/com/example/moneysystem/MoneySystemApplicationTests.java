package com.example.moneysystem;

import com.example.moneysystem.constants.Constants;
import com.example.moneysystem.dto.BaseDto;
import com.example.moneysystem.dto.TwoOperandsBody;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.web.util.UriComponentsBuilder;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MoneySystemApplicationTests {

	@LocalServerPort
	private int port;

	private TestRestTemplate restTemplate;
	private HttpHeaders headers;

	@BeforeEach
	public void setUp() {
		restTemplate = new TestRestTemplate();
		headers = new HttpHeaders();
		headers.setAccept(List.of(MediaType.valueOf(Constants.VERSION_1_HEADER)));
		headers.setContentType(MediaType.APPLICATION_JSON);
	}

	@Autowired
	private ObjectMapper jacksonObjectMapper;

	@Test
	void contextLoads() {
	}

	@Test
	public void sumOKTest() throws JsonProcessingException {
		TwoOperandsBody twoOperandsBody = new TwoOperandsBody();
		twoOperandsBody.setData("5p 17s 8d");
		twoOperandsBody.setSecond("3p 4s 10d");
		BaseDto expected = new BaseDto() {{
			setData("9p 2s 6d");
		}};

		HttpEntity<String> entity = new HttpEntity<String>(jacksonObjectMapper.writeValueAsString(twoOperandsBody), headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/sum"), HttpMethod.POST, entity,
				String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo(jacksonObjectMapper.writeValueAsString(expected));
	}

	@Test
	public void subtractOKTest() throws JsonProcessingException {
		TwoOperandsBody twoOperandsBody = new TwoOperandsBody();
		twoOperandsBody.setData("5p 17s 8d");
		twoOperandsBody.setSecond("3p 4s 10d");
		BaseDto expected = new BaseDto() {{
			setData("2p 12s 10d");
		}};

		HttpEntity<String> entity = new HttpEntity<String>(jacksonObjectMapper.writeValueAsString(twoOperandsBody), headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/subtract"), HttpMethod.POST, entity,
				String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo(jacksonObjectMapper.writeValueAsString(expected));
	}

	@Test
	public void multiplyOKTest() throws JsonProcessingException {
		BaseDto body = new BaseDto() {{
			setData("5p 17s 8d");
		}};
		int i = 2;
		BaseDto expected = new BaseDto() {{
			setData("11p 15s 4d");
		}};

		String urlTemplate = UriComponentsBuilder.fromHttpUrl(createURLWithPort("/multiply"))
				.queryParam("i", "{i}")
				.encode()
				.toUriString();
		Map<String, String> params = new HashMap<>();
		params.put("i", "" + i);

		HttpEntity<String> entity = new HttpEntity<String>(jacksonObjectMapper.writeValueAsString(body), headers);
		ResponseEntity<String> response = restTemplate.exchange(urlTemplate, HttpMethod.POST, entity,
				String.class, params);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo(jacksonObjectMapper.writeValueAsString(expected));
	}

	@Test
	public void divideOKTest() throws JsonProcessingException {
		BaseDto body = new BaseDto() {{
			setData("5p 17s 8d");
		}};
		int i = 3;
		BaseDto expected = new BaseDto() {{
			setData("1p 19s 2d (2d)");
		}};

		String urlTemplate = UriComponentsBuilder.fromHttpUrl(createURLWithPort("/divide"))
				.queryParam("i", "{i}")
				.encode()
				.toUriString();
		Map<String, String> params = new HashMap<>();
		params.put("i", "" + i);

		HttpEntity<String> entity = new HttpEntity<String>(jacksonObjectMapper.writeValueAsString(body), headers);
		ResponseEntity<String> response = restTemplate.exchange(urlTemplate, HttpMethod.POST, entity,
				String.class, params);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(response.getBody()).isEqualTo(jacksonObjectMapper.writeValueAsString(expected));
	}

	@Test
	public void sumBadRequestTest() throws JsonProcessingException {
		TwoOperandsBody twoOperandsBody = new TwoOperandsBody();
		twoOperandsBody.setData("5p17s 8d");
		twoOperandsBody.setSecond("3p4s 10d");

		HttpEntity<String> entity = new HttpEntity<String>(jacksonObjectMapper.writeValueAsString(twoOperandsBody), headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/sum"), HttpMethod.POST, entity,
				String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	public void subtractBadRequestTest() throws JsonProcessingException {
		TwoOperandsBody twoOperandsBody = new TwoOperandsBody();
		twoOperandsBody.setData("5p17s 8d");
		twoOperandsBody.setSecond("3p 4s10d");

		HttpEntity<String> entity = new HttpEntity<String>(jacksonObjectMapper.writeValueAsString(twoOperandsBody), headers);

		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/subtract"), HttpMethod.POST, entity,
				String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	public void multiplyBadRequestTest() throws JsonProcessingException {
		BaseDto body = new BaseDto() {{
			setData("5p 17s 8d");
		}};
		int i = -2;

		String urlTemplate = UriComponentsBuilder.fromHttpUrl(createURLWithPort("/multiply"))
				.queryParam("i", "{i}")
				.encode()
				.toUriString();
		Map<String, String> params = new HashMap<>();
		params.put("i", "" + i);

		HttpEntity<String> entity = new HttpEntity<String>(jacksonObjectMapper.writeValueAsString(body), headers);
		ResponseEntity<String> response = restTemplate.exchange(urlTemplate, HttpMethod.POST, entity,
				String.class, params);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	public void divideBadRequestTest() throws JsonProcessingException {
		BaseDto body = new BaseDto() {{
			setData("5p 17s8d");
		}};
		int i = 3;

		String urlTemplate = UriComponentsBuilder.fromHttpUrl(createURLWithPort("/divide"))
				.queryParam("i", "{i}")
				.encode()
				.toUriString();
		Map<String, String> params = new HashMap<>();
		params.put("i", "" + i);

		HttpEntity<String> entity = new HttpEntity<String>(jacksonObjectMapper.writeValueAsString(body), headers);
		ResponseEntity<String> response = restTemplate.exchange(urlTemplate, HttpMethod.POST, entity,
				String.class, params);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
	}

	@Test
	public void sumUnsupportedMediaType() throws JsonProcessingException {
		BaseDto body = new BaseDto() {{
			setData("5p 17s8d");
		}};
		int i = 3;

		String urlTemplate = UriComponentsBuilder.fromHttpUrl(createURLWithPort("/divide"))
				.queryParam("i", "{i}")
				.encode()
				.toUriString();
		Map<String, String> params = new HashMap<>();
		params.put("i", "" + i);
		headers.setAccept(List.of(MediaType.valueOf("application/json")));

		HttpEntity<String> entity = new HttpEntity<String>(jacksonObjectMapper.writeValueAsString(body), headers);
		ResponseEntity<String> response = restTemplate.exchange(urlTemplate, HttpMethod.POST, entity,
				String.class, params);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

}
