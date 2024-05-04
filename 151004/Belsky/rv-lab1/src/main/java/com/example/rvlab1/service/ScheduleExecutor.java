package com.example.rvlab1.service;

import com.example.rvlab1.model.entity.IssueEntity;
import com.example.rvlab1.model.entity.LabelEntity;
import com.example.rvlab1.model.entity.UserEntity;
import com.example.rvlab1.repository.IssueRepository;
import com.example.rvlab1.repository.LabelRepository;
import com.example.rvlab1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduleExecutor {
    private final LabelRepository labelRepository;
    private final IssueRepository issueRepository;
    private final UserRepository userRepository;

    @Scheduled(fixedDelay = 10000000L)
    public void test() {
//        UserEntity userEntity = new UserEntity()
//                .setFirstname("First Name")
//                .setLastname("Last Name")
//                .setLogin("login")
//                .setPassword("password");
//        userEntity = userRepository.save(userEntity);
//        IssueEntity issueEntity = new IssueEntity()
//                .setTitle("Title")
//                .setContent("Content")
//                .setUserId(userEntity.getId());
//        issueEntity = issueRepository.save(issueEntity);
//        LabelEntity labelEntity = new LabelEntity()
//                .setName("Label Name");
//        labelEntity = labelRepository.save(labelEntity);
//        labelEntity.getIssues().add(issueEntity);
//
//        labelEntity = labelRepository.save(labelEntity);
//
//        LabelEntity newLabelEntity = labelRepository.findById(labelEntity.getId()).get();
//
//        System.out.println();
    }
}
