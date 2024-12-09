package com.bryant.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/jsp")
@RestController
public class JspController {

    @GetMapping("/getValue")
    public String getValue(Model model) {
        model.addAttribute("name", "bryant");
        return "value";
    }

}

