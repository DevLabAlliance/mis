package com.academy.mis.controller;

import com.academy.mis.domain.Trainer;
import com.academy.mis.repository.TrainerRepository;
import com.academy.mis.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class TrainerController {

    private final TrainerRepository trainerRepository;

    private final TrainerService trainerService;

    @Autowired
    public TrainerController(TrainerRepository trainerRepository, TrainerService trainerService) {
        this.trainerRepository = trainerRepository;
        this.trainerService = trainerService;
    }

    @GetMapping("/addtrainer")
    public String showAddTrainerPage(Trainer trainer) {
        return "addtrainer";
    }

    @PostMapping("/addtrainer")
    public String addUser(@Valid Trainer trainer, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "addtrainer";
        }

        trainerRepository.save(trainer);
        return "redirect:/index";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Trainer trainer = trainerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid trainer Id:" + id));
        model.addAttribute("user", trainer);

        return "updateTrainer";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid Trainer trainer, BindingResult result, Model model) {
        if (result.hasErrors()) {
            trainer.setId(id);
            return "update-trainer";
        }

        trainerRepository.save(trainer);

        return "redirect:/index";
    }

    @GetMapping("/searchtrainer")
    public String showSearchTrainerPage(Model model, Trainer trainer) {
        List<Trainer> trainerList = trainerRepository.findAll();
        List<String> skillCategoryList = trainerList.stream()
                .map(Trainer :: getSkillCategory)
                .distinct()
                .collect(Collectors.toList());
        model.addAttribute("options", skillCategoryList);

        return "searchTrainer";
    }

    @PostMapping("/searchtrainer")
    public String searchTrainer(Model model, @Param("keyword") String keyword) {
        List<Trainer> listTrainers = trainerService.searchTrainer(keyword);
        model.addAttribute("trainer", listTrainers);
        model.addAttribute("keyword", keyword);

        return "searchTrainer";
    }


    @PostMapping("/search/{id}")
    public String searchTrainer(@Valid Trainer trainer, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "update-trainer";
        }

        trainerRepository.save(trainer);

        return "redirect:/index";
    }
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        Trainer user = trainerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        trainerRepository.delete(user);

        return "redirect:/index";
    }

}
