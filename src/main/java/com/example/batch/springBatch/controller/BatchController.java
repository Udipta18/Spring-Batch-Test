//package com.example.batch.springBatch.controller;
//
//
//import com.example.batch.springBatch.config.JobService;
//import com.example.batch.springBatch.domain.Coffee;
//import com.example.batch.springBatch.repos.CoffeeRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import java.util.Optional;
//
//@Controller
//public class BatchController {
//
//
//    @Autowired
//    JobService jobService;
//
//    @Autowired
//    CoffeeRepo repo;
//
//    @GetMapping("/start")
//    public String startJob() throws Exception {
//        //Optional<Coffee> byId = repo.findById("ndero");
//        //System.out.println(byId.get());
//        return "Job Started...";
//    }
//
//}
