package com.FinalProject.Utils;
import com.FinalProject.Mapper.FileUploadMapper;
import com.FinalProject.Pojo.Resume;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
public class Unzip {

    public static void unzipFile(String sourceFile, String destFolder, FileUploadMapper fileUploadMapper) throws IOException {
        String savePath = "D:/upload/";
        // = "D:\\dataset_CV\\CV.zip";
       // = "D:\\upload\\docx";
        byte[] buffer = new byte[10240];
        File folder = new File(destFolder);
        // create output directory if it doesn't exist
        if (!folder.exists()) {
            folder.mkdir();
        }
        // get the zip file content
        ZipInputStream zis = new ZipInputStream(new FileInputStream(sourceFile));
        // get the zipped file list entry
        ZipEntry ze = zis.getNextEntry();
        while (ze != null) {
            String fileName = ze.getName();

            int dox=fileName.lastIndexOf('/');
            fileName = fileName.substring(dox + 1);
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
             fileName = uuid.substring(0,4) +"-"+ fileName;
            String path=destFolder+"/"  + fileName;
            System.out.println("path="+path);
            System.out.println("destFolder="+destFolder);
            System.out.println("fileName="+fileName);
            try {
                Resume resume = new Resume(0, fileName, path, null);
                fileUploadMapper.insertFile(resume);
            } catch (Exception e) {
                e.printStackTrace();
            }
            File newFile = new File(path);
            if (ze.isDirectory()) {
                // create directory if it doesn't exist
                newFile.mkdirs();
            } else {
                // create file if it doesn't exist, and copy the content
                new File(newFile.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
            }
            ze = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
    }
}

