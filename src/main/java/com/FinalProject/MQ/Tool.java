package com.FinalProject.MQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Tool{

    public static final String  QUEUE_NAME="hello";

    public static final String IP="192.168.144.136";

    public static final String user="guest";

    public static final String password="guest";

    private static ConnectionFactory factory=new ConnectionFactory();

    private static Connection connection;
    private static Channel channel;


    public static Connection getConnection() throws Exception
    {
        factory.setUsername(user);
        factory.setPassword(password);
        factory.setHost(IP);
        connection=factory.newConnection();
        return connection;
    }

    public static Channel getChannel() throws Exception
    {
        channel=getConnection().createChannel();
        return channel;
    }

}
