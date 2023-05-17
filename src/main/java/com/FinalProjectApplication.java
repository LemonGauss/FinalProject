package com;



import com.FinalProject.MQ.Producer;
import com.FinalProject.MQ.Consumer;
import com.FinalProject.MQ.Tool;
import com.rabbitmq.client.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.annotation.Configurations;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;


@SpringBootApplication
public class FinalProjectApplication {
    public static void main(String[] args) throws Exception {

        SpringApplication.run(FinalProjectApplication.class, args);
//                AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(Configurations.class);

//        Producer producer=new Producer();
//
//        Consumer consumer_1=new Consumer();
//        Consumer consumer_2=new Consumer();
//
//        producer.start();
//        producer.stop();
//        consumer_1.start();
//        consumer_2.start();

    }

}
