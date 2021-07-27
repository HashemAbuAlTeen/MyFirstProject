package com.example.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void adminHiPageTestWithAdminCredentials()  {

        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Basic YWRtaW46YWRtaW4=");



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
        header.add("Authorization", "Basic dXNlcjp1c2Vy");



        ResponseEntity<String> responseEntity= this.restTemplate.exchange("http://localhost:" + port + "/admin/hi",
                HttpMethod.GET,
                new HttpEntity(header),
                String.class);

        assertThat( responseEntity.getStatusCode() ).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void adminHiPageTestWithWrongCredentials()  {

        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Basic dXNlcjp1c2V");


        ResponseEntity<String> responseEntity= this.restTemplate.exchange("http://localhost:" + port + "/admin/hi",
                HttpMethod.GET,
                new HttpEntity(header),
                String.class);

        assertThat( responseEntity.getStatusCode() ).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @ParameterizedTest
    @ValueSource(ints = {1 ,2 ,3})
    public void getUserByIdWithExistingUser(int id) throws Exception{
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Basic dXNlcjp1c2Vy");

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
        header.add("Authorization", "Basic YWRtaW46YWRtaW4=");

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

        assertThat( responseEntity.getStatusCode() ).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @ParameterizedTest
    @ValueSource(ints = {1000})
    public void getUserByIdForNonExistingUser(int id) throws Exception{
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Basic dXNlcjp1c2Vy");

        ResponseEntity<String> responseEntity= this.restTemplate.exchange("http://localhost:" + port + "/users/" + id,
                HttpMethod.GET,
                new HttpEntity(header),
                String.class);

        assertThat( responseEntity.getStatusCode() ).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat( responseEntity.getBody()).isNotEmpty();

    }




}
