package com.example.asm_group_4.controller;

import com.example.asm_group_4.model.Staff;
import com.example.asm_group_4.repository.StaffRepository;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/staff")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StaffController {

    StaffRepository staffRepository;

    // 1. Danh sách nhân viên có phân trang
    @GetMapping("/list")
    public String listStaff(Model model, @RequestParam(defaultValue = "0") int page) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "id"));
        Page<Staff> staffPage = staffRepository.findAll(pageable);

        model.addAttribute("listStaff", staffPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", staffPage.getTotalPages());

        return "pages/staff-list.html";
    }

    // 2. Hiển thị form thêm
    @GetMapping("/add-form")
    public String showAddForm(Model model) {
        model.addAttribute("staff", new Staff());
        return "pages/staff-add";
    }

    // 3. Xử lý thêm nhân viên
    @PostMapping("/add")
    public String addStaff(@Valid @ModelAttribute("staff") Staff staff,
                           BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "pages/staff-add";
        }
        staff.setStartDate(new Date());
        staff.setEndDate(new Date());
        staffRepository.save(staff);
        return "redirect:/staff/list";
    }

    // 4. Xem chi tiết nhân viên
    @GetMapping("/detail/{id}")
    public String detailStaff(@PathVariable("id") Integer id, Model model) {
        Staff staff = staffRepository.findById(id).orElse(null);
        model.addAttribute("staff", staff);
        return "pages/staff-detail.html";
    }

    // 5. Hiển thị form cập nhật
    @GetMapping("/update-form/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Staff staff = staffRepository.findById(id).orElse(null);
        staff.setStartDate(new Date());
        staff.setEndDate(new Date());
        model.addAttribute("staff", staff);
        return "pages/staff-update";
    }

    // 6. Xử lý cập nhật nhân viên
    @PostMapping("/update/{id}")
    public String updateStaff(@PathVariable("id") Integer id,
                              @Valid @ModelAttribute("staff") Staff staffUpdate,
                              BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "pages/staff-update";
        }

        Staff existingStaff = staffRepository.findById(id).orElse(null);
        if (existingStaff != null) {
            existingStaff.setEmployeeCode(staffUpdate.getEmployeeCode());
            existingStaff.setFullName(staffUpdate.getFullName());
            existingStaff.setDayOfBirth(staffUpdate.getDayOfBirth());
            existingStaff.setGender(staffUpdate.getGender());
            existingStaff.setPhoneNumber(staffUpdate.getPhoneNumber());
            existingStaff.setIdCard(staffUpdate.getIdCard());
            existingStaff.setStartDate(new Date());
            existingStaff.setEndDate(new Date());
            existingStaff.setDuration(staffUpdate.getDuration());
            existingStaff.setStatus(staffUpdate.getStatus());

            staffRepository.save(existingStaff);
        }
        return "redirect:/staff/list";
    }

    // 7. Xóa nhân viên
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        staffRepository.deleteById(id);
        return "redirect:/staff/list";
    }

    // 8. Tìm kiếm nhân viên
    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model model) {
        keyword = keyword.trim();
        model.addAttribute("listStaff", staffRepository.searchStaff(keyword));
        model.addAttribute("keyword", keyword);
        return "pages/staff-list.html";
    }
}
