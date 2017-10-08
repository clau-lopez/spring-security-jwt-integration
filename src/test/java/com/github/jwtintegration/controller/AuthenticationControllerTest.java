package com.github.jwtintegration.controller;

import com.github.jwtintegration.domain.AuthenticationRequest;
import com.github.jwtintegration.domain.AuthenticationResponse;
import com.github.jwtintegration.token.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper;

    @Autowired
    private TokenService tokenService;

    @Before
    public void before() {
        objectMapper = new ObjectMapper();
    }

    @Test
    public void authenticate() throws Exception {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("admin", "123");
        MvcResult mvcResult = this.mockMvc.perform(
                post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationRequest)))
                .andExpect(status().isOk())
                .andReturn();

        AuthenticationResponse authenticationResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), AuthenticationResponse.class);

        assertNotNull(authenticationResponse);

        assert (tokenService.isValidToken(authenticationResponse.getToken()));

    }

    @Test
    public void authenticateWithWrongPassword() throws Exception {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("admin", "fake");
        this.mockMvc.perform(
                post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationRequest)))
                .andExpect(status().isForbidden());

    }

    @Test
    public void getUnauthorizedException() throws Exception {

        AuthenticationRequest authenticationRequest = new AuthenticationRequest("admin", "123");


        MvcResult mvcResult = this.mockMvc.perform(
                post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authenticationRequest)))
                .andExpect(status().isOk())
                .andReturn();

        AuthenticationResponse authenticationResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), AuthenticationResponse.class);


        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("authorization", authenticationResponse.getToken());

        this.mockMvc.perform(
                get("/hello").headers(httpHeaders))
                .andExpect(status().isOk());

    }

    @Test
    public void getauthorizedAccess() throws Exception {
        this.mockMvc.perform(
                get("/hello"))
                .andExpect(status().isForbidden());

    }

    @Test
    public void getauthorizedAccessWithHeaderNull() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("authorization", null);


        this.mockMvc.perform(
                get("/hello"))
                .andExpect(status().isForbidden());

    }

}