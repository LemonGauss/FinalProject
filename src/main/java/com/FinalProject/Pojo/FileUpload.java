package com.FinalProject.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileUpload {
    private String RNO;
    private String RName;
    private  String Position;
    private String content;
}