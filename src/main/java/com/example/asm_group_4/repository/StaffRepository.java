package com.example.asm_group_4.repository;

import com.example.asm_group_4.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository  extends JpaRepository<Staff, Integer> {
    @Query("SELECT s FROM Staff s WHERE " +
            "s.employeeCode LIKE %:keyword% OR " +
            "s.fullName LIKE %:keyword% OR " +
            "s.phoneNumber LIKE %:keyword% OR " +
            "s.idCard LIKE %:keyword%")
    List<Staff> searchStaff(@Param("keyword") String keyword);
}
