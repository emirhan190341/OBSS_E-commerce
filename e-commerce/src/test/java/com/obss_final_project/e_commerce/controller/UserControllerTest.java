package com.obss_final_project.e_commerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.obss_final_project.e_commerce.dto.request.user.CreateUserRequest;
import com.obss_final_project.e_commerce.dto.request.user.UpdateUserRequest;
import com.obss_final_project.e_commerce.dto.response.user.UserResponse;
import com.obss_final_project.e_commerce.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class) // only instantiate UserController
@ActiveProfiles(value = "integration") // use application-integration.properties in memory db
@AutoConfigureMockMvc(addFilters = false) // disable security for testing
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void saveUser_ValidRequest_ShouldReturnCreatedUser() throws Exception {

        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("emirhan123");
        request.setPassword("Password123");
        request.setEmail("emirhan.arici@example.com");
        request.setName("Emirhan");
        request.setSurname("Arici123");
        request.setPhoneNumber("1234567890");
        request.setAddress("Kocaeli123");

        UserResponse response = new UserResponse();
        response.setId(1L);
        response.setUsername("emirhan123");
        response.setEmail("emirhan.arici@example.com");
        response.setFullName("Emirhan Arici123");
        response.setPhoneNumber("1234567890");
        response.setAddress("Kocaeli123");


        when(userService.saveUser(any(CreateUserRequest.class))).thenReturn(response);

        ResultActions resultActions = mockMvc.perform(post("/api/v1/users/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))

        );

        resultActions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.response.username", is("emirhan123")))
                .andExpect(jsonPath("$.response.email", is("emirhan.arici@example.com")))
                .andExpect(jsonPath("$.response.fullName", is("Emirhan Arici123")))
                .andExpect(jsonPath("$.response.phoneNumber", is("1234567890")))
                .andExpect(jsonPath("$.response.address", is("Kocaeli123")));

    }

    @Test
    void updateUserById_ValidRequest_ShouldReturnUpdatedUser() throws Exception {

        UpdateUserRequest request = new UpdateUserRequest();
        request.setName("Updated Name");
        request.setSurname("Updated Surname");
        request.setPhoneNumber("9876543210");
        request.setAddress("Updated Address");

        UserResponse response = new UserResponse();
        response.setId(1L);
        response.setUsername("emirhan123");
        response.setEmail("updated.email@example.com");
        response.setFullName("Updated Name Updated Surname");
        response.setPhoneNumber("9876543210");
        response.setAddress("Updated Address");

        when(userService.updateUserById(anyLong(), any(UpdateUserRequest.class))).thenReturn(response);

        ResultActions resultActions = mockMvc.perform(put("/api/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        );

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.email", is("updated.email@example.com")))
                .andExpect(jsonPath("$.response.fullName", is("Updated Name Updated Surname")))
                .andExpect(jsonPath("$.response.phoneNumber", is("9876543210")))
                .andExpect(jsonPath("$.response.address", is("Updated Address")));
    }

    @Test
    void deleteUserById_ValidRequest_ShouldReturnSuccessMessage() throws Exception {

        when(userService.deleteUserById(anyLong())).thenReturn("User deleted successfully");

        ResultActions resultActions = mockMvc.perform(delete("/api/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON)
        );

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response", is("User deleted successfully")));
    }

    @Test
    void findUserById_ValidRequest_ShouldReturnUser() throws Exception {

        UserResponse response = new UserResponse();
        response.setId(1L);
        response.setUsername("emirhan123");
        response.setEmail("emirhan.arici@example.com");
        response.setFullName("Emirhan Arici123");
        response.setPhoneNumber("1234567890");
        response.setAddress("Kocaeli123");

        when(userService.findUserById(anyLong())).thenReturn(response);

        ResultActions resultActions = mockMvc.perform(get("/api/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON)
        );

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.username", is("emirhan123")))
                .andExpect(jsonPath("$.response.email", is("emirhan.arici@example.com")))
                .andExpect(jsonPath("$.response.fullName", is("Emirhan Arici123")))
                .andExpect(jsonPath("$.response.phoneNumber", is("1234567890")))
                .andExpect(jsonPath("$.response.address", is("Kocaeli123")));
    }
}
