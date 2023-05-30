package com.FinalProject.Service.Impl;

import org.json.JSONException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import org.springframework.transaction.annotation.Transactional;

@Service  // 表明这是一个Spring服务类
@Transactional  // 表明这个服务类的方法需要数据库事务支持
public class PythonApiCaller {
    public static String generateFile(String strInput) throws IOException, JSONException {

        String url = "http://localhost:5000/process";  // FastAPI服务器的URL
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();  // 创建一个到FastAPI服务器的连接
        con.setRequestMethod("POST");  // 设置请求类型为POST
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");  // 设置请求内容类型为JSON，并指定字符集为UTF-8
        JSONObject json = new JSONObject();  // 创建一个新的JSONObject
        json.put("filePath", strInput);  // 在JSONObject中添加文件路径

        String postJsonData = json.toString();  // 将JSONObject转化为字符串
        System.out.println("postJsonData+"+postJsonData);
        con.setDoOutput(true);  // 允许连接进行输出
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());  // 创建一个新的数据输出流
        byte[] postDataBytes = postJsonData.getBytes("UTF-8");  // 将JSON数据写入输出流，使用UTF-8编码
        wr.write(postDataBytes);  // 将字节流写入输出流
        wr.flush();  // 清空该流，使所有缓冲的输出字节被写出
        wr.close();  // 关闭该流并释放所有系统资源

        int responseCode = con.getResponseCode();  // 获取服务器响应的HTTP状态码
        if (responseCode == HttpURLConnection.HTTP_OK) { // 如果状态码为200，表示请求成功
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    con.getInputStream()));  // 创建一个新的缓冲读取器来读取服务器的响应
            String inputLine;
            StringBuffer response = new StringBuffer();  // 创建一个新的字符串缓冲区来保存服务器的响应

            while ((inputLine = in.readLine()) != null) {  // 在服务器的响应中读取每一行，直到没有更多的行
                response.append(inputLine);  // 将读取的行添加到字符串缓冲区
            }
            in.close();  // 关闭读取器
            JSONObject jsonResponse = new JSONObject(response.toString());  // 将服务器的响应字符串转换为JSONObject
            System.out.println(jsonResponse);  // 打印JSON对象的内容
            // Considering the response is a string message
            return response.toString();  // 返回服务器的响应
        } else {
            return null;  // 如果请求失败，则返回null
        }
    }
}
