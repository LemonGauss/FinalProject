package com.FinalProject.Mapper;

import com.FinalProject.Pojo.Occupation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OccupationMapper {

    void InsertOccupation(Occupation occupation);

    List GetOccupation();
}
