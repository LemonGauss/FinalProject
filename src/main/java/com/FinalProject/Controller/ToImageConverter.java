package com.FinalProject.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
public class ToImageConverter {//在线查看文件的功能

    @PostMapping("/fileToImage")
    public ResponseEntity<byte[]> sendDataToBackend(@RequestBody String jsonData) {
        // 在这里处理接收到的 JSON 字符串
        // 可以使用任何 JSON 库来解析字符串，例如使用 Jackson、Gson 等库
        // 示例中使用 Jackson 将 JSON 字符串转换为 Java 对象

        //String filePath = "E:\\dataset_CV\\CV\\测试1.pdf";
       // String outputDirectory = "src/main/resources/images_temp/";
        String fileName="";
        String fileFormat="";
        String imagePath="";
        try {

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(jsonData);
            // 获取 position 字段的值，并将其作为字符串
            String position = jsonNode.get("position").asText();
            System.out.println("----------------str="+position);
            // 获取最后一个点的索引
            int lastDotIndex = position.lastIndexOf('.');
            if (lastDotIndex != -1) {

                 fileName=position.substring(0,lastDotIndex);
                // 使用substring方法截取最后一个点之后的部分
                 fileFormat = position.substring(lastDotIndex + 1);
            }
            imagePath=fileName+"1.png";
            System.out.println("fileName="+fileName+"pdf="+fileFormat+"  "+"imagePath="+imagePath);
            // 读取图片文件
            File imageFile = new File(imagePath);
            Path imagePathObj = imageFile.toPath();
            byte[] imageBytes = Files.readAllBytes(imagePathObj);
            // 设置响应头，指定内容类型为图像类型
            HttpHeaders headers = new HttpHeaders();
            if(fileFormat.equals("pdf") || fileFormat.equals("txt"))
            {headers.setContentType(MediaType.IMAGE_PNG);}
            else if(fileFormat.equals("docx") || fileFormat.equals("doc"))
            {headers.setContentType(MediaType.IMAGE_JPEG);}
            System.out.println("toimage转换成功");
            // 返回响应实体
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(imageBytes);
        } catch (JsonProcessingException e) {
            // 处理解析异常
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}

