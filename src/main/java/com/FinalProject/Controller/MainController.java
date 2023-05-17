package com.FinalProject.Controller;


import com.FinalProject.Service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class MainController {
    @Autowired
    ResumeService resumeService;

    @RequestMapping("/p")
    public String getStarted()
    {
        return "list";
    }

    @RequestMapping("/q")
    public String getGp()
    {
        return "list_1";
    }
    //管理员登录

    @RequestMapping("/O")
    public String getOccupation()
    {
        return "Occupation";
    }

}
