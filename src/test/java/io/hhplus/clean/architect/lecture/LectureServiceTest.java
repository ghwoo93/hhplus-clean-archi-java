package io.hhplus.clean.architect.lecture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.hhplus.clean.architect.lecture.aggregate.dto.LectureDTO;
import io.hhplus.clean.architect.lecture.aggregate.dto.LectureRegistrationDTO;
import io.hhplus.clean.architect.lecture.aggregate.dto.LectureScheduleDTO;
import io.hhplus.clean.architect.lecture.aggregate.entity.Lecture;
import io.hhplus.clean.architect.lecture.aggregate.entity.LectureRegistration;
import io.hhplus.clean.architect.lecture.aggregate.entity.LectureSchedule;
import io.hhplus.clean.architect.lecture.exception.LectureBusinessException;
import io.hhplus.clean.architect.lecture.helper.FairLockHelper;
import io.hhplus.clean.architect.lecture.repository.LectureRegistrationRepositoryImpl;
import io.hhplus.clean.architect.lecture.repository.LectureRepositoryImpl;
import io.hhplus.clean.architect.lecture.repository.LectureScheduleRepositoryImpl;
import io.hhplus.clean.architect.lecture.service.LectureService;

@ExtendWith(SpringExtension.class)
public class LectureServiceTest {

    @Mock
    private LectureRepositoryImpl lectureRepository;

    @Mock
    private LectureScheduleRepositoryImpl lectureScheduleRepository;

    @Mock
    private LectureRegistrationRepositoryImpl lectureRegistrationRepository;

    @Mock
    private FairLockHelper fairLockHelper;

    @InjectMocks
    private LectureService lectureService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllLectures() {
        Lecture lecture1 = new Lecture(1L, "Lecture 1");
        Lecture lecture2 = new Lecture(2L, "Lecture 2");
        List<Lecture> lectures = Arrays.asList(lecture1, lecture2);
    
        when(lectureRepository.findAll()).thenReturn(lectures);
    
        List<LectureDTO> result = lectureService.getAllLectures();
        
        // 디버깅 출력
        System.out.println("Expected size: 2, Actual size: " + result.size());
        System.out.println("Lectures: " + result);
    
        assertEquals(2, result.size());
        verify(lectureRepository, times(1)).findAll();
    }

    @Test
    public void testGetLectureSchedules() {
        LectureSchedule schedule1 = new LectureSchedule(1L, 1L, null, 30);
        LectureSchedule schedule2 = new LectureSchedule(2L, 1L, null, 30);
        List<LectureSchedule> schedules = Arrays.asList(schedule1, schedule2);

        when(lectureScheduleRepository.findByLectureId(anyLong())).thenReturn(schedules);

        List<LectureScheduleDTO> result = lectureService.getLectureSchedules(1L);
        assertEquals(2, result.size());
        verify(lectureScheduleRepository, times(1)).findByLectureId(anyLong());
    }

    @Test
    public void testApplyForLecture_Success() throws Exception {
        doAnswer(invocation -> {
            FairLockHelper.BooleanSupplier task = invocation.getArgument(0);
            try {
                return task.getAsBoolean();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).when(fairLockHelper).executeWithLock(any(FairLockHelper.BooleanSupplier.class));

        LectureSchedule schedule = new LectureSchedule(1L, 1L, null, 30);
        when(lectureScheduleRepository.findById(anyLong())).thenReturn(Optional.of(schedule));
        when(lectureRegistrationRepository.countByScheduleId(anyLong())).thenReturn(10L);
        when(lectureRegistrationRepository.existsByUserIdAndScheduleId(anyLong(), anyLong())).thenReturn(false);

        boolean result = lectureService.applyForLecture(1L, 1L);
        assertTrue(result);
        verify(lectureScheduleRepository, times(1)).findById(anyLong());
        verify(lectureRegistrationRepository, times(1)).countByScheduleId(anyLong());
        verify(lectureRegistrationRepository, times(1)).existsByUserIdAndScheduleId(anyLong(), anyLong());
        verify(lectureRegistrationRepository, times(1)).save(any(LectureRegistration.class));
    }

    @Test
    public void testApplyForLecture_Failure_LectureFull() throws Exception {
        doAnswer(invocation -> {
            FairLockHelper.BooleanSupplier task = invocation.getArgument(0);
            try {
                return task.getAsBoolean();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).when(fairLockHelper).executeWithLock(any(FairLockHelper.BooleanSupplier.class));

        LectureSchedule schedule = new LectureSchedule(1L, 1L, null, 30);
        when(lectureScheduleRepository.findById(anyLong())).thenReturn(Optional.of(schedule));
        when(lectureRegistrationRepository.countByScheduleId(anyLong())).thenReturn(30L);

        LectureBusinessException exception = assertThrows(LectureBusinessException.class, () -> {
            lectureService.applyForLecture(1L, 1L);
        });

        assertEquals("Lecture is full", exception.getMessage());
        verify(lectureScheduleRepository, times(1)).findById(anyLong());
        verify(lectureRegistrationRepository, times(1)).countByScheduleId(anyLong());
        verify(lectureRegistrationRepository, never()).existsByUserIdAndScheduleId(anyLong(), anyLong());
        verify(lectureRegistrationRepository, never()).save(any(LectureRegistration.class));
    }

    @Test
    public void testCheckRegistrationStatus() {
        when(lectureRegistrationRepository.existsByUserId(anyLong())).thenReturn(true);

        boolean result = lectureService.checkRegistrationStatus(1L);
        assertTrue(result);
        verify(lectureRegistrationRepository, times(1)).existsByUserId(anyLong());
    }

    @Test
    public void testGetRegistrationHistory() {
        LectureRegistration registration1 = new LectureRegistration(1L, 1L, 1L, null);
        LectureRegistration registration2 = new LectureRegistration(2L, 2L, 1L, null);
        List<LectureRegistration> registrations = Arrays.asList(registration1, registration2);

        when(lectureRegistrationRepository.findByScheduleId(anyLong())).thenReturn(registrations);

        List<LectureRegistrationDTO> result = lectureService.getRegistrationHistory(1L);
        assertEquals(2, result.size());
        verify(lectureRegistrationRepository, times(1)).findByScheduleId(anyLong());
    }

    // @Test
    // public void testApplyForLecture_ConcurrentRequests() throws Exception {
    //     doAnswer(invocation -> {
    //         FairLockHelper.BooleanSupplier task = invocation.getArgument(0);
    //         try {
    //             return task.getAsBoolean();
    //         } catch (Exception e) {
    //             throw new RuntimeException(e);
    //         }
    //     }).when(fairLockHelper).executeWithLock(any(FairLockHelper.BooleanSupplier.class));

    //     LectureSchedule schedule = new LectureSchedule(1L, 1L, null, 30);
    //     when(lectureScheduleRepository.findById(anyLong())).thenReturn(Optional.of(schedule));
    //     when(lectureRegistrationRepository.countByScheduleId(anyLong())).thenReturn(10L);
    //     when(lectureRegistrationRepository.existsByUserIdAndScheduleId(anyLong(), anyLong())).thenReturn(false);

    //     int numberOfThreads = 10;
    //     ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
    //     CountDownLatch latch = new CountDownLatch(numberOfThreads);

    //     for (int i = 0; i < numberOfThreads; i++) {
    //         final long userId = i + 1;
    //         executorService.submit(() -> {
    //             try {
    //                 lectureService.applyForLecture(userId, 1L);
    //             } finally {
    //                 latch.countDown();
    //             }
    //         });
    //     }

    //     latch.await(10, TimeUnit.SECONDS);

    //     verify(fairLockHelper, times(numberOfThreads)).executeWithLock(any(FairLockHelper.BooleanSupplier.class));
    //     verify(lectureScheduleRepository, times(numberOfThreads)).findById(anyLong());
    //     verify(lectureRegistrationRepository, times(numberOfThreads)).countByScheduleId(anyLong());
    //     verify(lectureRegistrationRepository, times(numberOfThreads)).existsByUserIdAndScheduleId(anyLong(), anyLong());
    //     verify(lectureRegistrationRepository, times(numberOfThreads)).save(any(LectureRegistration.class));
    // }
}
