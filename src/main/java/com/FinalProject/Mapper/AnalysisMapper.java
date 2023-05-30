package com.FinalProject.Mapper;

import com.FinalProject.Pojo.Analysis;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AnalysisMapper {
    void insertAnalysis(Analysis analysis);
    boolean isFilenameExists(String filename);
}
