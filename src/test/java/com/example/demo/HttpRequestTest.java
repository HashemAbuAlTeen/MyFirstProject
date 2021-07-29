package com.example.demo;

import com.example.demo.Security.AuthenticationRequest;
import com.example.demo.Security.AuthenticationResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;



import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    Logger logger = LoggerFactory.getLogger(HttpRequestTest.class);

    @LocalServerPort
    private int port;

    @Autowired
    public TestRestTemplate restTemplate;


    private  String adminJwt = "";
    private  String userJwt = "";


    @BeforeEach
    public  void getJwts() {
        logger.trace("getjwts has accessed");

        ObjectMapper objectMapper = new ObjectMapper();

        TestRestTemplate template =new TestRestTemplate();
        AuthenticationRequest userRequest = new AuthenticationRequest("user","user");
        AuthenticationRequest adminRequest = new AuthenticationRequest("admin","admin");

        HttpEntity<AuthenticationRequest> request = new HttpEntity<>(userRequest);


        ResponseEntity<AuthenticationResponse> response = template.postForEntity(
                "http://localhost:" + port + "/authenticate", request, AuthenticationResponse.class);

        if(response.getBody() == null)
            logger.error("response body of /authorize is null for user");
        else
            userJwt = response.getBody().getJwt();

        request = new HttpEntity<>(adminRequest);
        response = template.postForEntity(
                "http://localhost:" + port + "/authenticate", request, AuthenticationResponse.class);
        if(response.getBody() == null)
            logger.error("response body of /authorize is null for admin");
        else
            adminJwt = response.getBody().getJwt();
    }


    @Test
    public void adminHiPageTestWithAdminCredentials()  {

        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer " + adminJwt);



        ResponseEntity<String> responseEntity= this.restTemplate.exchange("http://localhost:" + port + "/admin/hi",
                HttpMethod.GET,
                new HttpEntity(header),
                String.class);

        assertThat( responseEntity.getStatusCode() ).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotEmpty();
    }

    @Test
    public void adminHiPageTestWithUserCredentials()  {

        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer " + userJwt);



        ResponseEntity<String> responseEntity= this.restTemplate.exchange("http://localhost:" + port + "/admin/hi",
                HttpMethod.GET,
                new HttpEntity(header),
                String.class);

        assertThat( responseEntity.getStatusCode() ).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void adminHiPageTestWithWrongCredentials()  {

        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer " +adminJwt + "x");


        ResponseEntity<String> responseEntity= this.restTemplate.exchange("http://localhost:" + port + "/admin/hi",
                HttpMethod.GET,
                new HttpEntity(header),
                String.class);

        assertThat( responseEntity.getStatusCode() ).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @ParameterizedTest
    @ValueSource(ints = {1 ,2 ,3})
    public void getUserByIdWithExistingUser(int id) throws Exception{
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer "+ userJwt);

        ResponseEntity<String> responseEntity= this.restTemplate.exchange("http://localhost:" + port + "/users/" + id,
                HttpMethod.GET,
                new HttpEntity(header),
                String.class);

        assertThat( responseEntity.getStatusCode() ).isEqualTo(HttpStatus.OK);
        assertThat( responseEntity.getBody()).isNotEmpty();

    }

    @ParameterizedTest
    @ValueSource(ints = {1 ,2 ,3})
    public void getUserByIdWithAdmin(int id) throws Exception{
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer " + adminJwt);

        ResponseEntity<String> responseEntity= this.restTemplate.exchange("http://localhost:" + port + "/users/" + id,
                HttpMethod.GET,
                new HttpEntity(header),
                String.class);

        assertThat( responseEntity.getStatusCode() ).isEqualTo(HttpStatus.OK);
        assertThat( responseEntity.getBody()).isNotEmpty();

    }

    @ParameterizedTest
    @ValueSource(ints = {1 ,2 ,3})
    public void getUserByIdWithoutAuth(int id) {
        HttpHeaders header = new HttpHeaders();


        ResponseEntity<String> responseEntity= this.restTemplate.exchange("http://localhost:" + port + "/users/" + id,
                HttpMethod.GET,
                new HttpEntity(header),
                String.class);

        assertThat( responseEntity.getStatusCode() ).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @ParameterizedTest
    @ValueSource(ints = {1000})
    public void getUserByIdForWrongToken(int id) throws Exception{
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer " + adminJwt + "x");

        ResponseEntity<String> responseEntity= this.restTemplate.exchange("http://localhost:" + port + "/users/" + id,
                HttpMethod.GET,
                new HttpEntity(header),
                String.class);

        assertThat( responseEntity.getStatusCode() ).isEqualTo(HttpStatus.FORBIDDEN);


    }




}
