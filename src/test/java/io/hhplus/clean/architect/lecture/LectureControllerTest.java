package io.hhplus.clean.architect.lecture;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import io.hhplus.clean.architect.lecture.aggregate.dto.LectureDTO;
import io.hhplus.clean.architect.lecture.aggregate.dto.LectureRegistrationDTO;
import io.hhplus.clean.architect.lecture.aggregate.dto.LectureScheduleDTO;
import io.hhplus.clean.architect.lecture.controller.LectureController;
import io.hhplus.clean.architect.lecture.service.LectureService;

@WebMvcTest(LectureController.class)
public class LectureControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LectureService lectureService;

    @Test
    public void testGetAllLectures() throws Exception {
        LectureDTO lecture1 = new LectureDTO(1L, "Lecture 1");
        LectureDTO lecture2 = new LectureDTO(2L, "Lecture 2");
        List<LectureDTO> lectures = Arrays.asList(lecture1, lecture2);

        when(lectureService.getAllLectures()).thenReturn(lectures);

        mockMvc.perform(get("/api/lectures"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"lectureId\":1,\"title\":\"Lecture 1\"},{\"lectureId\":2,\"title\":\"Lecture 2\"}]"));
    }

    @Test
    public void testGetLectureSchedules() throws Exception {
        LectureScheduleDTO schedule1 = new LectureScheduleDTO(1L, 1L, null, 30);
        LectureScheduleDTO schedule2 = new LectureScheduleDTO(2L, 1L, null, 30);
        List<LectureScheduleDTO> schedules = Arrays.asList(schedule1, schedule2);

        when(lectureService.getLectureSchedules(anyLong())).thenReturn(schedules);

        mockMvc.perform(get("/api/lectures/1/schedules"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"scheduleId\":1,\"lectureId\":1,\"date\":null,\"capacity\":30},{\"scheduleId\":2,\"lectureId\":1,\"date\":null,\"capacity\":30}]"));
    }

    @Test
    public void testApplyForLecture_Success() throws Exception {
        when(lectureService.applyForLecture(anyLong(), anyLong())).thenReturn(true);

        mockMvc.perform(post("/api/lectures/apply")
                .param("userId", "1")
                .param("scheduleId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Application successful"));
    }

    @Test
    public void testApplyForLecture_Failure() throws Exception {
        when(lectureService.applyForLecture(anyLong(), anyLong())).thenReturn(false);

        mockMvc.perform(post("/api/lectures/apply")
                .param("userId", "1")
                .param("scheduleId", "1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Application failed"));
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
        LectureRegistrationDTO registration1 = new LectureRegistrationDTO(1L, 1L, 1L, null);
        LectureRegistrationDTO registration2 = new LectureRegistrationDTO(2L, 2L, 1L, null);
        List<LectureRegistrationDTO> registrations = Arrays.asList(registration1, registration2);

        when(lectureService.getRegistrationHistory(anyLong())).thenReturn(registrations);

        mockMvc.perform(get("/api/lectures/1/registrations"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"registrationId\":1,\"userId\":1,\"scheduleId\":1,\"registrationDate\":null},{\"registrationId\":2,\"userId\":2,\"scheduleId\":1,\"registrationDate\":null}]"));
    }
}
