package com.academy.mis.controller;

import com.academy.mis.domain.Trainer;
import com.academy.mis.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HelloController {

    private final TrainerRepository trainerRepository;

    @Autowired
    public HelloController(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    @GetMapping("index")
    @PreAuthorize("hasAuthority('APPROLE_Admin')")
    public String Admin(Model model) {
        model.addAttribute("trainer", trainerRepository.findAll());
        return "index";
    }

    @GetMapping( "home" )
    public String home( @AuthenticationPrincipal(expression = "claims['name']") String name ) {
//        return String.format( "Hello %s!  welcome to the Security app", name);
        return "index";
    }

}