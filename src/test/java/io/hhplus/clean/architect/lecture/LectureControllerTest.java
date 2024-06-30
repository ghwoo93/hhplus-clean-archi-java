package io.hhplus.clean.architect.lecture;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import io.hhplus.clean.architect.lecture.adapter.controller.LectureController;
import io.hhplus.clean.architect.lecture.application.LectureService;
import io.hhplus.clean.architect.lecture.domain.dto.LectureDTO;
import io.hhplus.clean.architect.lecture.domain.dto.LectureRegistrationDTO;
import io.hhplus.clean.architect.lecture.domain.dto.LectureScheduleDTO;
import io.hhplus.clean.architect.lecture.exception.LectureBusinessException;

public class LectureControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LectureService lectureService;

    @InjectMocks
    private LectureController lectureController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(lectureController).build();
    }

    @Test
    public void testGetAllLectures() throws Exception {
        List<LectureDTO> lectures = Arrays.asList(new LectureDTO(1L, "Spring Boot"), new LectureDTO(2L, "Java Programming"));
        when(lectureService.getAllLectures()).thenReturn(lectures);

        mockMvc.perform(get("/api/lectures"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].lectureId").value(1L))
                .andExpect(jsonPath("$[0].title").value("Spring Boot"))
                .andExpect(jsonPath("$[1].lectureId").value(2L))
                .andExpect(jsonPath("$[1].title").value("Java Programming"));
    }

    @Test
    public void testGetLectureSchedules() throws Exception {
        List<LectureScheduleDTO> schedules = Arrays.asList(new LectureScheduleDTO(1L, 1L, null, 30));
        when(lectureService.getLectureSchedules(anyLong())).thenReturn(schedules);

        mockMvc.perform(get("/api/lectures/1/schedules"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].scheduleId").value(1L))
                .andExpect(jsonPath("$[0].lectureId").value(1L))
                .andExpect(jsonPath("$[0].capacity").value(30));
    }

    @Test
    public void testApplyForLecture_Success() throws Exception {
        when(lectureService.applyForLecture(anyLong(), anyLong())).thenReturn(true);

        mockMvc.perform(post("/api/lectures/apply")
                        .param("userId", "1")
                        .param("scheduleId", "1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isOk())
                .andExpect(content().string("Application successful"));
    }

    @Test
    public void testApplyForLecture_Failure() throws Exception {
        when(lectureService.applyForLecture(anyLong(), anyLong())).thenThrow(new LectureBusinessException("Application failed"));

        mockMvc.perform(post("/api/lectures/apply")
                        .param("userId", "1")
                        .param("scheduleId", "1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Application failed: Application failed"));
    }

    @Test
    public void testCheckRegistrationStatus() throws Exception {
        when(lectureService.checkRegistrationStatus(anyLong())).thenReturn(true);

        mockMvc.perform(get("/api/lectures/application/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void testGetRegistrationHistory() throws Exception {
        List<LectureRegistrationDTO> registrations = Arrays.asList(new LectureRegistrationDTO(1L, 1L, 1L, null));
        when(lectureService.getRegistrationHistory(anyLong())).thenReturn(registrations);

        mockMvc.perform(get("/api/lectures/1/registrations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].registrationId").value(1L))
                .andExpect(jsonPath("$[0].userId").value(1L))
                .andExpect(jsonPath("$[0].scheduleId").value(1L));
    }
}
