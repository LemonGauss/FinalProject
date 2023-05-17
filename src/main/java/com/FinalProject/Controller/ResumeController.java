package com.FinalProject.Controller;

import com.FinalProject.Service.OccupationService;
import com.FinalProject.Service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ResumeController {
    @Autowired
    ResumeService resumeService;

    @Autowired
    OccupationService occupationService;

    @GetMapping("/getResume")
    public List GetResume()
    {
        return resumeService.getAllResume();
    }

    @GetMapping("/getOccupation")
    public List GetOccupation(){ return occupationService.GetOccu(); }

}
