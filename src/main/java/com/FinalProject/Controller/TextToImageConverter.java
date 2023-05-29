package com.FinalProject.Controller;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class TextToImageConverter {//文本解析成图片，用于在线查看文件功能

    public static void convertTextToImage(String textFilePath, String outputImagePath) throws IOException {

        // 读取文本文件内容
        StringBuilder textContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(textFilePath), "UTF-8"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                textContent.append(line).append(System.lineSeparator());
            }
        }

        // 设置图片尺寸和字体样式
        int width = 800;
        int height = 600;
        Font font = new Font("SimSun", Font.PLAIN, 18); // 使用宋体字体，可根据需要修改字体名称

        // 创建 BufferedImage 并获取 Graphics2D 上下文
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();

        // 设置背景颜色和字体颜色
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, width, height);
        graphics.setColor(Color.BLACK);

        // 设置字体
        graphics.setFont(font);

        // 在图片上绘制文本内容
        String[] lines = textContent.toString().split(System.lineSeparator());
        int lineHeight = graphics.getFontMetrics().getHeight();
        int y = lineHeight;
        for (String line : lines) {
            graphics.drawString(line, 10, y);
            y += lineHeight;
        }

        // 保存图片
        ImageIO.write(image, "png", new File(outputImagePath));

        // 释放资源
        graphics.dispose();
    }
}

