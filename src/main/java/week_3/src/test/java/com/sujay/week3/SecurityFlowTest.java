package com.sujay.week3;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
@SpringBootTest
@AutoConfigureMockMvc
class SecurityFlowTest {
 @Autowired MockMvc mvc;
 @Test void healthEndpointIsPublic() throws Exception {
  mvc.perform(get("/actuator/health")).andExpect(status().isOk());
 }
 @Test void employeeEndpointRequiresAuthentication() throws Exception {
  mvc.perform(get("/api/v1/employees")).andExpect(status().isUnauthorized());
 }
}