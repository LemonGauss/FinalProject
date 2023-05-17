package com.FinalProject.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class FileDownLoadController {
    @RequestMapping("/test-download")
    public ResponseEntity<byte[]> testResponseEntity(@RequestBody String jsonData) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonData);
        // 获取 position 字段的值，并将其作为字符串
        String filePath = jsonNode.get("tdPosition").asText();
        String fileName="";
        // 1. 获得文件的真实路径 : webapp/ 开始
        //String filePath = session.getServletContext().getRealPath("/static/1.png");
        int lastDotIndex = filePath.lastIndexOf('/');
        if (lastDotIndex != -1) {


            // 使用substring方法截取最后一个点之后的部分
            fileName = filePath.substring(lastDotIndex + 1);
        }
        System.out.println("load="+fileName+"     " +filePath);
        // 2. 创建输入流
        ResponseEntity<byte[]> responseEntity;
        try (InputStream inputStream = new FileInputStream(filePath)) {

            // 3. 创建字节数组, 并读取 todo: 为什么要一次读取到内存里面
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);

            // 4. 创建 HttpHeaders 对象设置响应头信息
            MultiValueMap<String, String> headers = new HttpHeaders();

            // 5. 设置下载方式以及下载文件的名字
            headers.add("Content-Disposition", "attachement;filename="+fileName);

            // 6. 设置响应状态码
            HttpStatus statusCode = HttpStatus.OK;

            // 7. 创建ResponseEndity对象 : 响应体，响应头，响应状态码
            responseEntity = new ResponseEntity<>(bytes, headers, statusCode);

            // 8. 关闭输入流
            inputStream.close();
        }
        return responseEntity;
    }
}
