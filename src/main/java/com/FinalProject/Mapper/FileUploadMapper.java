package com.FinalProject.Mapper;

import com.FinalProject.Pojo.FileUpload;

import com.FinalProject.Pojo.Resume;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileUploadMapper {

    void insertFile(Resume resume);
}