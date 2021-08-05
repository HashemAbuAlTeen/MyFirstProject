package com.example.demo;

import com.example.demo.company.Company;
import com.example.demo.company.CompanyDto;
import com.example.demo.company.CompanyService;
import com.example.demo.security.AuthenticationRequest;
import com.example.demo.security.AuthenticationResponse;
import com.example.demo.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
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


import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
 class HttpRequestTest {

    Logger logger = LoggerFactory.getLogger(HttpRequestTest.class);

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    CompanyService companyService;


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
    void testAdminHiPageTestWithAdminCredentials()  {

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
    void testAdminHiPageTestWithUserCredentials()  {

        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer " + userJwt);



        ResponseEntity<String> responseEntity= this.restTemplate.exchange("http://localhost:" + port + "/admin/hi",
                HttpMethod.GET,
                new HttpEntity(header),
                String.class);

        assertThat( responseEntity.getStatusCode() ).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void testAdminHiPageTestWithWrongCredentials()  {

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
    void testGetUserByIdWithExistingUser(int id) throws Exception{
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
    void testGetUserByIdWithAdmin(int id) throws Exception{
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
    void testGetUserByIdWithoutAuth(int id) {
        HttpHeaders header = new HttpHeaders();


        ResponseEntity<String> responseEntity= this.restTemplate.exchange("http://localhost:" + port + "/users/" + id,
                HttpMethod.GET,
                new HttpEntity(header),
                String.class);

        assertThat( responseEntity.getStatusCode() ).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @ParameterizedTest
    @ValueSource(ints = {1000})
    void testGetUserByIdForWrongToken(int id) throws Exception{
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer " + adminJwt + "x");

        ResponseEntity<String> responseEntity= this.restTemplate.exchange("http://localhost:" + port + "/users/" + id,
                HttpMethod.GET,
                new HttpEntity(header),
                String.class);

        assertThat( responseEntity.getStatusCode() ).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @ParameterizedTest
    @ValueSource(ints = {2})
    void testGetCompanyUsers(int id) {
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer " + userJwt);

        ResponseEntity<List> responseEntity= this.restTemplate.exchange("http://localhost:" + port + "/companies/" + id +"/users",
                HttpMethod.GET,
                new HttpEntity(header),
                List.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
    }

    @Test
    void testPostCompany () {
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer " + adminJwt);

        CompanyDto companyDto = new CompanyDto();
        companyDto.setLocation("Test Location");
        companyDto.setName("Test Name");

        HttpEntity<CompanyDto> request  = new HttpEntity<>(companyDto , header);
        ResponseEntity<Company> responseEntity= this.restTemplate.postForEntity("http://localhost:" + port + "/admin/companies",
                request , Company.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        int id = responseEntity.getBody().getId() ;
        assertThat(responseEntity.getBody().getId()).isNotNull();

        Company company = companyService.getById(id);
        assertThat(company.getName()).isEqualTo("Test Name");
        assertThat(company.getLocation()).isEqualTo("Test Location");

        companyService.deleteCompany(id);
    }

    @Test
    void testPutCompany () {
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer " + adminJwt);

        Company company = new Company();
        company.setLocation("Test Location");
        company.setName("Test Name");

        company = companyService.createCompany(company);
        assumeTrue(companyService.exists(company.getId()));
        assumeTrue(companyService.getById(company.getId()).getLocation().equals("Test Location"));
        CompanyDto companyDto = new CompanyDto();
        companyDto.setId(company.getId());
        companyDto.setName("Updated Name");
        companyDto.setLocation("Updated Location");

        HttpEntity<CompanyDto> request  = new HttpEntity<>(companyDto , header);
        ResponseEntity<Company> responseEntity= this.restTemplate.exchange("http://localhost:" + port + "/admin/companies",
                HttpMethod.PUT,request , Company.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        int id = responseEntity.getBody().getId() ;
        assertThat(responseEntity.getBody().getId()).isNotNull();

        company = companyService.getById(id);
        assertThat(company.getName()).isEqualTo("Updated Name");
        assertThat(company.getLocation()).isEqualTo("Updated Location");
        companyService.deleteCompany(id);
    }

    @Test
    void testDeleteCompany(){
        HttpHeaders header = new HttpHeaders();
        header.add("Authorization", "Bearer " + adminJwt);

        Company company = new Company();
        company.setLocation("Test Location");
        company.setName("Test Name");

        company = companyService.createCompany(company);
        assumeTrue(companyService.exists(company.getId()));


        HttpEntity<CompanyDto> request  = new HttpEntity<>(header);
        ResponseEntity<Object> responseEntity= this.restTemplate.exchange("http://localhost:" + port + "/admin/companies/" + company.getId(),
                HttpMethod.DELETE,request , Object.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(companyService.exists(company.getId())).isFalse();


    }




}
