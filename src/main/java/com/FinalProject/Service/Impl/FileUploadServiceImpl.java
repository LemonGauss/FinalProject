package com.FinalProject.Service.Impl;


import com.FinalProject.Controller.DocumentToImageConverter;
import com.FinalProject.Controller.TextToImageConverter;
import com.FinalProject.Mapper.AnalysisMapper;
import com.FinalProject.Mapper.FileUploadMapper;
import com.FinalProject.Pojo.Analysis;
import com.FinalProject.Pojo.Resume;
import com.FinalProject.Service.FileUploadService;
import com.FinalProject.Utils.Unzip;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
@Transactional
public class FileUploadServiceImpl implements FileUploadService {

    @Autowired
    private FileUploadMapper fileUploadMapper;
    @Autowired
    private AnalysisMapper analysisMapper;  // 添加注入 AnalysisMapper
    @Override
    public void saveFile(MultipartFile file) throws IOException {
        // 获取文件的字节数组
        byte[] pdfBytes = file.getBytes();

        // 获取保存文件的路径
        String savePath = "D:/upload/";

        // 创建文件保存目录
        File saveDir = new File(savePath);
        if (!saveDir.exists()) {
            saveDir.mkdirs();
        }

        // 生成唯一的文件名
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String fileName = uuid.substring(0,4) +"-"+ file.getOriginalFilename();
        int lastDotIndex1=file.getOriginalFilename().lastIndexOf('.');
        String frontOriginName=file.getOriginalFilename().substring(0,lastDotIndex1+1);
        // 保存PDF文件
        File saveFile = new File(savePath + fileName);
        FileOutputStream fos = new FileOutputStream(saveFile);
        fos.write(pdfBytes);
        fos.flush();
        fos.close();
        // 保存图片
        int lastDotIndex = fileName.lastIndexOf('.');
        String fileFormat="";
        String frontFileName="";
        if (lastDotIndex != -1) {
            frontFileName=fileName.substring(0,lastDotIndex+1);
            // 使用substring方法截取最后一个点之后的部分
            fileFormat = fileName.substring(lastDotIndex + 1);
        }
        if (fileFormat.equals("zip"))
        {
            Unzip.unzipFile(savePath + fileName, savePath+"zip/"+frontOriginName,fileUploadMapper);
            try {
                PythonApiCaller.generateFile(savePath+"zip/"+frontOriginName);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(fileFormat.equals("docx"))
        {
            try {
                PythonApiCaller.generateFile(savePath +fileName);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if(fileFormat.equals("pdf"))
        {
            try {
                PythonApiCaller.generateFile(savePath+fileName);//发送文件绝对地址
            } catch (JSONException e) {
                e.printStackTrace();
            }
            DocumentToImageConverter.convertPDFToImages(savePath + fileName,savePath);
        }
        if(fileFormat.equals("jpeg"))
        {
            System.out.println("--------"+savePath+fileName);
            try {
                PythonApiCaller.generateFile(savePath+fileName);//发送文件绝对地址
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //savePath + fileName示例
        if(fileFormat.equals("txt"))
        {
            try {
                PythonApiCaller.generateFile(savePath+fileName);//发送文件绝对地址
            } catch (JSONException e) {
                e.printStackTrace();
            }
            TextToImageConverter.convertTextToImage(savePath + fileName,savePath+frontFileName+"png");
        }
        if(!fileFormat.equals("zip")) {
            try {
                Resume resume = new Resume(0, fileName, savePath + fileName, null);
                fileUploadMapper.insertFile(resume);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try{
            ExcelToDatabase excelToDatabase=new ExcelToDatabase(analysisMapper);
            excelToDatabase.readExcelAndInsertIntoDatabase("D:\\upload\\excel\\testExcel.xlsx");}
        catch (Exception e){
            e.printStackTrace();
        }
    }
}