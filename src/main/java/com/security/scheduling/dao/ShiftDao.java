// ShiftDao.java
package com.security.scheduling.dao;

import com.security.scheduling.model.Shift;
import java.util.List;

public interface ShiftDao {
    List<Shift> findAll();
    Shift findById(int shiftId);
    Shift create(Shift shift);
    Shift update(Shift shift);
    boolean delete(int shiftId);
}