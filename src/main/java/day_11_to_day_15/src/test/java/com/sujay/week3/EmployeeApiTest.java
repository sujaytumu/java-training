package com.sujay.week3;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest @AutoConfigureMockMvc
class EmployeeApiTest {
 @Autowired MockMvc mvc;
 @Test void anonymousEmployeeReadIsRejected() throws Exception {
  mvc.perform(get("/api/v1/employees")).andExpect(status().isUnauthorized());
 }
 @Test void loginIssuesBearerToken() throws Exception {
  mvc.perform(post("/api/v1/auth/login").contentType(MediaType.APPLICATION_JSON)
   .content("{\"username\":\"admin\",\"password\":\"admin123\"}"))
   .andExpect(status().isOk()).andExpect(jsonPath("$.accessToken").isNotEmpty()).andExpect(jsonPath("$.tokenType").value("Bearer"));
 }
}