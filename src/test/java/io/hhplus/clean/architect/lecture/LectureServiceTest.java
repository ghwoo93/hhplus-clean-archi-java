package io.hhplus.clean.architect.lecture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.hhplus.clean.architect.lecture.adapter.repository.LectureRegistrationRepository;
import io.hhplus.clean.architect.lecture.adapter.repository.LectureRepository;
import io.hhplus.clean.architect.lecture.adapter.repository.LectureScheduleRepository;
import io.hhplus.clean.architect.lecture.application.LectureService;
import io.hhplus.clean.architect.lecture.domain.dto.LectureDTO;
import io.hhplus.clean.architect.lecture.domain.dto.LectureRegistrationDTO;
import io.hhplus.clean.architect.lecture.domain.dto.LectureScheduleDTO;
import io.hhplus.clean.architect.lecture.domain.model.Lecture;
import io.hhplus.clean.architect.lecture.domain.model.LectureRegistration;
import io.hhplus.clean.architect.lecture.domain.model.LectureSchedule;
import io.hhplus.clean.architect.lecture.domain.service.LectureDomainService;
import io.hhplus.clean.architect.lecture.exception.LectureBusinessException;
import jakarta.persistence.LockModeType;

public class LectureServiceTest {

    @Mock
    private LectureRepository lectureRepository;

    @Mock
    private LectureScheduleRepository lectureScheduleRepository;

    @Mock
    private LectureRegistrationRepository lectureRegistrationRepository;

    @Mock
    private LectureDomainService lectureDomainService;

    @InjectMocks
    private LectureService lectureService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllLectures() {
        List<LectureDTO> lectures = Arrays.asList(new LectureDTO(1L, "Spring Boot"), new LectureDTO(2L, "Java Programming"));
        when(lectureRepository.findAll()).thenReturn(Arrays.asList(new Lecture(1L, "Spring Boot"), new Lecture(2L, "Java Programming")));

        List<LectureDTO> result = lectureService.getAllLectures();
        assertEquals(2, result.size());
        verify(lectureRepository, times(1)).findAll();
    }

    @Test
    public void testGetLectureSchedules() {
        List<LectureScheduleDTO> schedules = Arrays.asList(new LectureScheduleDTO(1L, 1L, new Timestamp(System.currentTimeMillis()), 30));
        when(lectureScheduleRepository.findByLectureId(anyLong())).thenReturn(Arrays.asList(new LectureSchedule(1L, 1L, new Timestamp(System.currentTimeMillis()), 30)));

        List<LectureScheduleDTO> result = lectureService.getLectureSchedules(1L);
        assertEquals(1, result.size());
        verify(lectureScheduleRepository, times(1)).findByLectureId(anyLong());
    }

    @Test
    public void testApplyForLecture_Success() throws Exception {
        when(lectureRegistrationRepository.existsByUserIdAndScheduleId(anyLong(), anyLong())).thenReturn(false);
        when(lectureScheduleRepository.findByIdWithLock(anyLong(), eq(LockModeType.PESSIMISTIC_WRITE)))
                .thenReturn(Optional.of(new LectureSchedule(1L, 1L, new Timestamp(System.currentTimeMillis()), 30)));
        when(lectureRegistrationRepository.countByScheduleId(anyLong())).thenReturn(10L);

        boolean success = lectureService.applyForLecture(1L, 1L);
        assertTrue(success);
        verify(lectureDomainService, times(1)).registerForLecture(anyLong(), anyLong());
    }

    @Test
    public void testApplyForLecture_Failure_AlreadyRegistered() {
        when(lectureRegistrationRepository.existsByUserIdAndScheduleId(anyLong(), anyLong())).thenReturn(true);

        LectureBusinessException exception = assertThrows(LectureBusinessException.class, () -> {
            lectureService.applyForLecture(1L, 1L);
        });

        assertEquals("Failed to apply for lecture: User has already applied for this schedule", exception.getMessage());
        verify(lectureDomainService, never()).registerForLecture(anyLong(), anyLong());
    }

    @Test
    public void testApplyForLecture_Failure_ScheduleFull() {
        when(lectureRegistrationRepository.existsByUserIdAndScheduleId(anyLong(), anyLong())).thenReturn(false);
        when(lectureScheduleRepository.findByIdWithLock(anyLong(), eq(LockModeType.PESSIMISTIC_WRITE)))
                .thenReturn(Optional.of(new LectureSchedule(1L, 1L, new Timestamp(System.currentTimeMillis()), 30)));
        when(lectureRegistrationRepository.countByScheduleId(anyLong())).thenReturn(30L);

        LectureBusinessException exception = assertThrows(LectureBusinessException.class, () -> {
            lectureService.applyForLecture(1L, 1L);
        });

        assertEquals("Failed to apply for lecture: Lecture is full", exception.getMessage());
        verify(lectureDomainService, never()).registerForLecture(anyLong(), anyLong());
    }

    @Test
    public void testCheckRegistrationStatus() {
        when(lectureRegistrationRepository.existsByUserId(anyLong())).thenReturn(true);

        boolean status = lectureService.checkRegistrationStatus(1L);
        assertTrue(status);
        verify(lectureRegistrationRepository, times(1)).existsByUserId(anyLong());
    }

    @Test
    public void testGetRegistrationHistory() {
        List<LectureRegistrationDTO> registrations = Arrays.asList(new LectureRegistrationDTO(1L, 1L, 1L, new Timestamp(System.currentTimeMillis())));
        when(lectureRegistrationRepository.findByScheduleId(anyLong())).thenReturn(Arrays.asList(new LectureRegistration(1L, 1L, 1L, new Timestamp(System.currentTimeMillis()))));

        List<LectureRegistrationDTO> result = lectureService.getRegistrationHistory(1L);
        assertEquals(1, result.size());
        verify(lectureRegistrationRepository, times(1)).findByScheduleId(anyLong());
    }
}
