package com.reports;

import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.reports.entity.StudentMarks;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource("/application.properties")
public class AppTest {

    // It will be Autowired, if @SpringBootTest is annotated without any
    // specific class.
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    public void validateUser() throws Exception {
        String userId = "admin";
        String password = "admin";

        HttpHeaders headers = getHeaders(userId, password, null, null);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/v1/auth/token").headers(headers))
                .andExpect(status().isCreated())
                .andReturn();
        String token = mvcResult.getResponse().getContentAsString();
        boolean flag = (null != token && !token.toLowerCase().equals("Invalid username or password") && token.length() > 30);

        assertTrue("Toke should not be empty ", flag);
    }

    @Test
    public void signUpUnauthorized() throws Exception {

        String userId = "admin";
        String password = "admin";
        String role = "ADMIN";

        HttpHeaders headers = getHeaders(userId, password, role, "Bearer eykdfhahflkjhflakjhflakhfljkadshflkjsdh");
        // MvcResult mvcResult =
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/auth/signup/student1").headers(headers))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    public void getMarksByStudent() throws Exception {
        String userId = "S001";
        String password = "student1";

        String token = getToken(userId, password);

        MvcResult mvcResult = mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/v1/reports/get/marks").headers(getHeaders(userId, password, null, "Bearer " + token)))
                .andExpect(status().isOk())
                .andReturn();

        TypeFactory typeFactory = mapper.getTypeFactory();
        List<StudentMarks> markList = mapper.readValue(mvcResult.getResponse().getContentAsString(),
                typeFactory.constructCollectionType(List.class, StudentMarks.class));
        assertTrue("userId is not same ", userId.equals(markList.get(0).getRollNo()));

    }

    private HttpHeaders getHeaders(String userId, String password, String role, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("userId", userId);
        headers.add("password", password);
        if (null != role) {
            headers.add("role", role);
        }
        if (null != token) {
            headers.add("Authorization", token);
        }
        return headers;
    }

    private String getToken(String userId, String password) throws Exception {

        HttpHeaders headers = getHeaders(userId, password, null, null);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/v1/auth/token").headers(headers))
                .andExpect(status().isCreated())
                .andReturn();
        String token = mvcResult.getResponse().getContentAsString();
        return token;
    }

}
