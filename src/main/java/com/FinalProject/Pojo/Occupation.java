package com.FinalProject.Pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Occupation {
    private String PNO;

    private String PName;

    private String Company;

    private String Description;

    private Date STime;

    private String ETime;

    private String Salary;
}
