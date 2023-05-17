package com.FinalProject.MQ;

import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.ConcurrentSkipListMap;

public class Producer extends Thread{

    @Override
    public void run() {
        ConcurrentSkipListMap concurrentSkipListMap=new ConcurrentSkipListMap<>();
        for(int i=0;i<20;i++)
        {
            //记录
            //concurrentSkipListMap.put(Tool.getChannel().getNextPublishSeqNo());//37
            try {
                //声明队列，参数分别为：队列名，是否持久化，，，
                Tool.getChannel().queueDeclare(Tool.QUEUE_NAME, false, false, false, null);
                String message = "hello_world"+Integer.toString(i);
                //发送消息：  第二个参数改为如下表示消息持久化，如果不需要则填写为null
                //为了确认持久化已经完成，引入发布确认机制
                    // A 信道持久化
                    // B 消息持久化
                    // C 确认发布
                        //第一类： 单个发布确认   发一条确认一条，同步的 但是发布效率低   channel.confirmSelect()
                        //第二类： 批量发布确认   发生故障时难以定位   if(channel.waitForConfirms())bool值
                        //第三类： 异步发布确认   确认和发送消息异步，效率最高且能够检查消息，需要自定义确认和未被确认的监听函数


                ConfirmCallback confirmCallback=(deliverTag,Multiple)->{
                    System.out.println(deliverTag+" 已被确认");
                };
                ConfirmCallback nackCallback=(deliverTag,Multiple)->{
                    System.out.println(deliverTag+" 未被确认");
                };

                Tool.getChannel().addConfirmListener(confirmCallback,nackCallback);
                Tool.getChannel().basicPublish("", Tool.QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
