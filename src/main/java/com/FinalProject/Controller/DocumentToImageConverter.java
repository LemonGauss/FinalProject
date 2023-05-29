package com.FinalProject.Controller;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Controller;

import java.awt.image.BufferedImage;
import java.io.File;

import java.io.IOException;
@Controller
public  class DocumentToImageConverter {//pdf解析成图片，用于在线查看文件功能

    // 将PDF文件转换为图片
    public static void convertPDFToImages(String pdfFilePath, String outputDirectory)  {
        String imagePath="";//转换后图片地址
        try{
        File file=new File(pdfFilePath);
        PDDocument document = PDDocument.load(file);
        PDFRenderer pdfRenderer = new PDFRenderer(document);
        //获取文件名前缀
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex != -1 && dotIndex < fileName.length() - 1) {
            fileName = fileName.substring(0, dotIndex);
        }
        int pageCount = document.getNumberOfPages();
        //目前只取第一页
            for (int pageIndex = 0; pageIndex < pageCount; pageIndex++) {
                BufferedImage image = pdfRenderer.renderImageWithDPI(pageIndex, 300, ImageType.RGB);
                 imagePath = outputDirectory + fileName  + (pageIndex + 1) + ".png";
                ImageIOUtil.writeImage(image, imagePath, 300);
            }

        document.close();
            System.out.println("转换成功");

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("错误");

        }
    }
}
