package com.example.asm_group_4.controller;

import com.example.asm_group_4.model.Asset;
import com.example.asm_group_4.repository.AssetRepository;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/asset")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AssetController {
    AssetRepository assetRepository;

    @GetMapping("/list")
    public String getListAsset (Model model, @RequestParam(defaultValue = "0") int page) {
        int pageSize = 5;
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Asset> pageAsset = assetRepository.findAll(pageable);

        model.addAttribute("listA", pageAsset.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageAsset.getTotalPages());

        return "/pages/asset-list";
    }

    @GetMapping("/add-asset")
    public String addPages(@ModelAttribute("asset") Asset asset) {
        return "/pages/asset";

    }
    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("asset") Asset asset, Errors errors) {
        if (errors.hasErrors()) {
            return "/pages/asset";
        }
        assetRepository.save(asset);
        return "redirect:/asset/list";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        assetRepository.deleteById(id);
        return "redirect:/asset/list";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {
        Asset asset = assetRepository.findById(id).get();
        model.addAttribute("asset", asset);
        return "/pages/asset-detail";
    }
    @GetMapping("/update-asset/{id}")
    public String updatePage(@PathVariable("id") Integer id, Model model) {
        Asset asset = assetRepository.findById(id).get();
        model.addAttribute("asset", asset);
        return "/pages/asset-update";
    }

    @PostMapping("/update/{id}")
    public String update(@Valid
                         @ModelAttribute("asset") Asset asset,
                         Errors errors) {
        if (errors.hasErrors()) {
            return "/pages/asset-update";
        }
        assetRepository.save(asset);
        return "redirect:/asset/list";
    }

    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model model) {
        model.addAttribute("listA", assetRepository.search(keyword));
        model.addAttribute("keyword", keyword);
        return "/pages/asset";
    }
}
