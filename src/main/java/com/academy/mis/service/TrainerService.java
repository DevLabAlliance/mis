package com.academy.mis.service;

import com.academy.mis.domain.Trainer;
import com.academy.mis.repository.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainerService {

    @Autowired
    private final TrainerRepository trainerRepository;

    public TrainerService(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    public List<Trainer> searchTrainer(String keyword) {
        if(keyword != null){
            return trainerRepository.search(keyword);
        }
        return trainerRepository.findAll();
    }
}
