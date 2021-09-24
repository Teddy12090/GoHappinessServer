package idv.teddy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import idv.teddy.dto.ActivityDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@AutoConfigureDataJpa
public class GoHappinessControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testInfo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/info"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("0"));
    }

    @Test
    public void testAddForNullBody() throws Exception {
        ValidationErrorResponse response = new ValidationErrorResponse();
        response.getViolations().add(new Violation("add.activityDto", "must not be null"));

        mockMvc.perform(MockMvcRequestBuilders.post("/activity").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(response)));
    }

    @Test
    public void testAddForNullTitle() throws Exception {
        ValidationErrorResponse response = new ValidationErrorResponse();
        response.getViolations().add(new Violation("title", "must not be blank"));

        mockMvc.perform(MockMvcRequestBuilders.post("/activity").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(ActivityDto.builder().build())))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(response)));
    }

    @Test
    public void testAddForEmptyTitle() throws Exception {
        ValidationErrorResponse response = new ValidationErrorResponse();
        response.getViolations().add(new Violation("title", "must not be blank"));

        mockMvc.perform(MockMvcRequestBuilders.post("/activity").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(ActivityDto.builder().title("").build())))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string(objectMapper.writeValueAsString(response)));
    }

    @Test
    public void testAdd() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/activity").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(ActivityDto.builder().title("舞誥頌").build())))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}