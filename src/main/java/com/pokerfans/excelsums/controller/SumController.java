package com.pokerfans.excelsums.controller;

import com.pokerfans.excelsums.service.CombinationSumService;
import com.pokerfans.excelsums.util.ExcelUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Controller
@RequestMapping("/sum")
public class SumController {

    private final CombinationSumService service = new CombinationSumService();

    @GetMapping
    public String uploadPage() {
        return "upload";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file,
                         @RequestParam("targets") String targetsStr,
                         Model model) throws Exception {

        List<Double> numbers =
                ExcelUtil.readFirstColumn(file.getInputStream());

        List<Double> targets = Arrays.stream(targetsStr.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Double::valueOf)
                .distinct()
                .toList();

        if (numbers.stream().anyMatch(n -> n < 0)) {
            model.addAttribute("error", "Excel 中不允许出现负数。");
            return "upload";
        }

        if (targets.stream().anyMatch(t -> t < 0)) {
            model.addAttribute("error", "目标值不允许为负数。");
            return "upload";
        }

        model.addAttribute(
                "resultMap",
                service.calculate(numbers, targets)
        );

        model.addAttribute("numbers", numbers);

        return "result";
    }
}
