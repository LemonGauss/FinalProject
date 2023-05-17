package com.FinalProject.Controller;


import com.FinalProject.Service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;


@Controller
public class FileUploadController {

    @Autowired
    private FileUploadService fileUploadService;
    private static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 10 MB
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<ArrayList<String>> uploadFileHandler(@RequestParam("file") MultipartFile file, Model model) {
        String fileName = file.getOriginalFilename();
        ArrayList<String> list1 =new ArrayList<>();
        ArrayList<String> list2 =new ArrayList<>();
        ArrayList<String> list3 =new ArrayList<>();
        list1.add("文件上传失败！");
        list2.add("文件上传成功！");
        list3.add("文件太大，请选择小于5MB的文件！");
        // 检查上传的文件是否是PDF格式
        if (!fileName.toLowerCase().endsWith(".txt") && !fileName.toLowerCase().endsWith(".pdf")) {
            return new ResponseEntity<>(list1, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        // 检查上传的文件大小是否超过限制
        if (file.getSize() > MAX_FILE_SIZE) {
            return new ResponseEntity<>(list3, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        try {
            // 调用Service层方法保存PDF文件
            fileUploadService.saveFile(file);
            return new ResponseEntity<>(list2, HttpStatus.OK);

        } catch (Exception e) {
            model.addAttribute("error", "上传文件出现异常！");
            return new ResponseEntity<>(list1, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping("/G")
    public String gotoUpload()
    {
        return "Upload";
    }

}