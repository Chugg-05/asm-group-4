package com.example.asm_group_4.controller;

import com.example.asm_group_4.model.Staff;
import com.example.asm_group_4.repository.StaffRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/staff")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StaffController {
    StaffRepository staffRepository;

    @GetMapping("/field")
    public String FieldStaff(Model model){
        model.addAttribute("listStaff",staffRepository.findAll(Sort.by(Sort.Direction.DESC,"id")));
        return "pages/staff.html";
    }

    @PostMapping("/add")
    public String AddStaff(Staff staff){
        staff.setStartDate(new Date());
        staff.setEndDate(new Date());
        staffRepository.save(staff);
        return "redirect:/staff/field";
    }

    @GetMapping("/detail/{id}")
    public String DetailStaff(@PathVariable("id") Integer id, Model model){
        Staff staff = staffRepository.findById(id).get();
        model.addAttribute("staff",staff);
        return "pages/staff-detail.html";
    }

    @PostMapping("/update/{id}")
    public String UpdateStaff(Staff staff){
        staff.setEndDate(new Date());
        staffRepository.save(staff);
        return "redirect:/staff/field";
    }

    @GetMapping("/delete/{id}")
    public String Delete(@PathVariable("id") Integer id){
        staffRepository.deleteById(id);
        return "redirect:/staff/field";
    }

    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model model) {
        keyword = keyword.trim();
        model.addAttribute("listStaff", staffRepository.searchStaff(keyword));
        model.addAttribute("keyword", keyword);
        return "/pages/staff.html";
    }
}
