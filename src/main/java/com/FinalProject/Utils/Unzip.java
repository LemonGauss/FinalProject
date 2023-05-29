package com.FinalProject.Utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
public class Unzip {
    public static void unzipFile(String sourceFile, String destFolder) throws IOException {
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
            String path=destFolder + File.separator + fileName;
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

