package com.security.scheduling.dao;

import com.security.scheduling.model.Shift;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcShiftDao implements ShiftDao{
    @Override
    public List<Shift> findAll() {
        return List.of();
    }

    @Override
    public Shift findById(int shiftId) {
        return null;
    }

    @Override
    public Shift create(Shift shift) {
        return null;
    }

    @Override
    public Shift update(Shift shift) {
        return null;
    }

    @Override
    public boolean delete(int shiftId) {
        return false;
    }
}
