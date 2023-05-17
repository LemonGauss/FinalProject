package com.FinalProject.Service;

import com.FinalProject.Mapper.OccupationMapper;
import com.FinalProject.Pojo.Occupation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OccupationService {
    @Autowired
    OccupationMapper occupationMapper;

    public void InsertOccu(Occupation occupation)
    {
        occupationMapper.InsertOccupation(occupation);
    }

    public List GetOccu()
    {
        return occupationMapper.GetOccupation();
    }
}
