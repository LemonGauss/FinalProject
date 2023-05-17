package com.FinalProject.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Resume {
    private int RNO;
    private String RName;
    private  String Position;
    private String content;
}
