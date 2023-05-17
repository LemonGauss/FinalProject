package com.FinalProject.Controller;

import com.FinalProject.Pojo.Occupation;
import com.FinalProject.Service.OccupationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OccupationController {
    @Autowired
    OccupationService occupationService;

    @RequestMapping("/OccupationUpload")
    public String OccupationUploadMethod(Occupation occupation)
    {
        occupationService.InsertOccu(occupation);
        return "Occupation";
    }

}
