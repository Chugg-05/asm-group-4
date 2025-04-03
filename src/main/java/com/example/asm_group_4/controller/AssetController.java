package com.example.asm_group_4.controller;

import com.example.asm_group_4.model.Asset;
import com.example.asm_group_4.repository.AssetRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/asset")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AssetController {
    AssetRepository assetRepository;

    @GetMapping("/field")
    public String fieldAsset (Model model) {
        model.addAttribute("listA", assetRepository.findAll(Sort.by(Sort.Direction.DESC, "id")));
        return "/pages/asset";
    }

    @PostMapping("/add")
    public String add(Asset asset) {
        assetRepository.save(asset);
        return "redirect:/asset/field";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        assetRepository.deleteById(id);
        return "redirect:/asset/field";
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {
        Asset asset = assetRepository.findById(id).get();
        model.addAttribute("asset", asset);
        return "/pages/asset-detail";
    }

    @PostMapping("/update/{id}")
    public String udpate(@ModelAttribute("asset") Asset asset, Model model) {
        assetRepository.save(asset);
        return "redirect:/asset/field";
    }

    @GetMapping("/search")
    public String search(@RequestParam("keyword") String keyword, Model model) {
        model.addAttribute("listA", assetRepository.search(keyword));
        model.addAttribute("keyword", keyword);
        return "/pages/asset";
    }
}
