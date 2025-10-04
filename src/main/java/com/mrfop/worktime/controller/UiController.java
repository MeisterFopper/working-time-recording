package com.mrfop.worktime.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UiController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("appName", "Worktime Tracker");
        return "index"; // will resolve to src/main/resources/templates/index.html (if Thymeleaf)
    }

    @GetMapping("/projects")
    public String projects() {
        return "projects";
    }

    @GetMapping("/worktime")
    public String worktime() {
        return "worktime";
    }
}