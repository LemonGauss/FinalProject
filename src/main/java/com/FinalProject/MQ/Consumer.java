package com.FinalProject.MQ;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.DeliverCallback;

public class Consumer extends Thread{

    @Override
    public void run() {
            DeliverCallback deliverCallback = (consumerTag, m) -> {
                System.out.println(new String(m.getBody()));
                try {

                    Tool.getChannel().basicAck(m.getEnvelope().getDeliveryTag(),false);
                    //手动确认方法，
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };

            CancelCallback cancelCallback = consumerTag -> {
                System.out.println("消息被中断");
            };
            //四个参数  分别为消费队列，是否自动回复，消费成功调用函数和消费不成功调用函数
            try {
                //设置消费者不公平分发，1为不公平分发，0为公平分发，其他数值表示预取值
                //类似于一个缓冲区，将队列中n个消息先存入该缓冲区，等待处理，提高不同消费线程的总效率
                Tool.getChannel().basicQos(1);
                Tool.getChannel().basicConsume(Tool.QUEUE_NAME, false, deliverCallback, cancelCallback);
                //四个参数分别表示：队列名，是否自动应答，成功处理相应函数和取消处理的相应函数
            } catch (Exception e) {
                e.printStackTrace();
            }


            /*消息应答机制，为了防止消息的丢失，当消费线程结束了一个对某一个消息的处理之后，会像队列应答，队列便
            删除对应消息
                应答包含自定应答和手动应答，能用手动应答就尽量用
                好处：可以用于批量应答减少网络拥堵
                应答方法：
                    1.channel.basicAck(channel，bool)  以接受且被处理，肯定应答
                        第二个参数为true，批量应答
                        为false时，只应答当个消息
                    2.channel.basicNack()    否定应答
                    3.channel.basicReject()  否定应答,与2区别于参数的数量，少其一个参数，
            */
        }
    }

