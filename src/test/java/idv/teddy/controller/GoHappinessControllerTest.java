package idv.teddy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import idv.teddy.dto.ActivityDto;
import idv.teddy.repository.ActivityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class GoHappinessControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ActivityRepository activityRepository;

    @Test
    void testInfo() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/info"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("0"));
    }

    @Test
    void testAddForNullBody() throws Exception {
        ValidationErrorResponse response = new ValidationErrorResponse();
        response.getViolations().add(new Violation("Payload must not be null."));

        mockMvc.perform(MockMvcRequestBuilders.post("/activity").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void testAddForNullTitle() throws Exception {
        ValidationErrorResponse response = new ValidationErrorResponse();
        response.getViolations().add(new Violation("title must not be blank"));
        response.getViolations().add(new Violation("startDate must not be blank"));
        response.getViolations().add(new Violation("url must not be blank"));
        response.getViolations().add(new Violation("source must not be blank"));

        mockMvc.perform(MockMvcRequestBuilders.post("/activity").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(ActivityDto.builder().build())))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void testAddForEmptyTitle() throws Exception {
        ValidationErrorResponse response = new ValidationErrorResponse();
        response.getViolations().add(new Violation("title must not be blank"));
        response.getViolations().add(new Violation("startDate must not be blank"));
        response.getViolations().add(new Violation("url must not be blank"));
        response.getViolations().add(new Violation("source must not be blank"));

        mockMvc.perform(MockMvcRequestBuilders.post("/activity").contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(ActivityDto.builder().title("").build())))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void testAddForInvalidDateFormat() throws Exception {
        ValidationErrorResponse response = new ValidationErrorResponse();
        response.getViolations().add(new Violation("startDate [2021/09/25] must be format of [yyyy-MM-dd]."));
        response.getViolations().add(new Violation("endDate [2022/09/25] must be format of [yyyy-MM-dd]."));

        mockMvc.perform(MockMvcRequestBuilders.post("/activity").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(ActivityDto.builder()
                        .title("舞誥頌")
                        .startDate("2021/09/25")
                        .endDate("2022/09/25")
                        .source("idv.teddy")
                        .url("http://localhost")
                        .build())))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void testAddForInvalidTimeFormat() throws Exception {
        ValidationErrorResponse response = new ValidationErrorResponse();
        response.getViolations().add(new Violation("startTime [8:00] must be format of [HH:MM:SS]."));
        response.getViolations().add(new Violation("endTime [18.00.00] must be format of [HH:MM:SS]."));

        mockMvc.perform(MockMvcRequestBuilders.post("/activity").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(ActivityDto.builder()
                        .title("舞誥頌")
                        .startDate("2021-09-25")
                        .startTime("8:00")
                        .endTime("18.00.00")
                        .source("idv.teddy")
                        .url("http://localhost")
                        .build())))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void testAddForInvalidUrl() throws Exception {
        ValidationErrorResponse response = new ValidationErrorResponse();
        response.getViolations().add(new Violation("url must be a valid URL"));

        mockMvc.perform(MockMvcRequestBuilders.post("/activity").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(ActivityDto.builder()
                        .title("舞誥頌")
                        .startDate("2021-09-25")
                        .source("idv.teddy")
                        .url("htt://localhost")
                        .build())))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(response)));
    }

    @Test
    void testAdd() throws Exception {
        ActivityDto activity1 = ActivityDto.builder()
                .title("舞誥頌")
                .startDate("2021-09-25")
                .endDate("2022-09-25")
                .startTime("08:30:00")
                .startTime("18:30:00")
                .source("idv.teddy")
                .url("http://localhost/1").build();
        mockMvc.perform(MockMvcRequestBuilders.post("/activity").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(activity1)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(activity1.withId(1L))));
        assertEquals(1, activityRepository.count());

        ActivityDto activity2 = ActivityDto.builder()
                .title("舞誥頌 2")
                .startDate("2021-09-25")
                .endDate("2021-09-09")
                .startTime("08:00:00")
                .endTime("18:30:00")
                .source("idv.teddy")
                .url("http://localhost/2").build();
        mockMvc.perform(MockMvcRequestBuilders.post("/activity").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(activity2)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(activity2.withId(2L))));
        assertEquals(2, activityRepository.count());

        ValidationErrorResponse errorReqponse = new ValidationErrorResponse();
        errorReqponse.getViolations().add(new Violation("Url of activity must be unique."));
        mockMvc.perform(MockMvcRequestBuilders.post("/activity").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(activity1)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(errorReqponse)));

        mockMvc.perform(MockMvcRequestBuilders.post("/activity").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(ActivityDto
                        .builder()
                        .title("舞誥頌 3")
                        .startDate("2021-09-25")
                        .source("idv.teddy")
                        .url("http://localhost/1").build())))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(errorReqponse)));
    }
}