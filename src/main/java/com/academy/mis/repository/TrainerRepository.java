package com.academy.mis.repository;

import com.academy.mis.domain.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    @Query("SELECT t FROM Trainer t WHERE CONCAT(t.first_name, ' ', t.last_name, ' ', t.phone, ' ', t.email, ' ', t.primarySkill, ' ', t.secondarySkill, ' ', t.otherSkill) LIKE %?1%")
    List<Trainer> search(String keyword);

}
