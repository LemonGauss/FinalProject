package com.FinalProject.finalproject;

import com.FinalProjectApplication;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FinalProjectApplicationTests {
	public static void main(String[] args) {
		try {
			System.out.println("1SpringBoot Start....");
			SpringApplication.run(FinalProjectApplication.class,args);
			System.out.println("2SpringBoot Start....");
		}catch(Throwable e) {
			System.out.println("错误出现....");
			e.printStackTrace();
		}
		System.out.println("3SpringBoot Start....");
	}


}
