// ScheduleDao.java
package com.security.scheduling.dao;

import com.security.scheduling.model.Schedule;
import java.time.LocalDate;
import java.util.List;

public interface ScheduleDao {
    List<Schedule> findAll();
    Schedule findById(int scheduleId);
    List<Schedule> findByDateRange(LocalDate startDate, LocalDate endDate);
    Schedule create(Schedule schedule);
    Schedule update(Schedule schedule);
    boolean delete(int scheduleId);
    boolean publishSchedule(int scheduleId);
    boolean addAssignmentToSchedule(int scheduleId, int assignmentId);
    boolean removeAssignmentFromSchedule(int scheduleId, int assignmentId);
}