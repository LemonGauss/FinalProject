package com.FinalProject.Service;

import com.FinalProject.Mapper.ResumeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ResumeService {
    @Autowired
    ResumeMapper resumeMapper;

    public List getAllResume(){
        return resumeMapper.resumeList();
    }
}
