package com.example.batch.springBatch.controller;


import com.example.batch.springBatch.config.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class BatchController {


    @Autowired
    JobService jobService;

    @GetMapping("/start")
    public String startJob() throws Exception {
        jobService.launchJob();
        return "Job Started...";
    }

}
