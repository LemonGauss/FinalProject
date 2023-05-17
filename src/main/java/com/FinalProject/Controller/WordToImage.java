package com.FinalProject.Controller;

import com.aspose.words.Document;
import com.aspose.words.ImageSaveOptions;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class WordToImage {
    public static void docToImage(String filePath,String outputDirectory){
        //word2pdf("C:/Users/Administrator/Desktop/1.doc","C:/Users/Administrator/Desktop/xxxx.pdf");//wordתpdf
        //wordתͼƬ��ʽ
        String fileFormat="";
        String frontFileName="";
        try {
            File file = new File(filePath);
            InputStream inStream = new FileInputStream(file);
            List<BufferedImage> wordToImg = wordToImg(inStream);//
            BufferedImage mergeImage = mergeImage(false, wordToImg);
            int lastDotIndex = filePath.lastIndexOf('.');
            if (lastDotIndex != -1) {
                frontFileName=filePath.substring(0,lastDotIndex+1);
                // 使用substring方法截取最后一个点之后的部分
                fileFormat = filePath.substring(lastDotIndex + 1);
            }
            ImageIO.write(mergeImage, "jpg", new File(frontFileName+"jpg")); //���䱣����C:/imageSort/targetPIC/��
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }



    /**
     * @Description: ��֤aspose.word����Ƿ���Ȩ������Ȩ���ļ���ˮӡ���
     */
    public static boolean isWordLicense() {
        boolean result = false;
        try {
            String s = "<License><Data><Products><Product>Aspose.Total for Java</Product><Product>Aspose.Words for Java</Product></Products><EditionType>Enterprise</EditionType><SubscriptionExpiry>20991231</SubscriptionExpiry><LicenseExpiry>20991231</LicenseExpiry><SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber></Data><Signature>sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=</Signature></License>";
            ByteArrayInputStream inputStream = new ByteArrayInputStream(s.getBytes());
            License license = new License();
            license.setLicense(inputStream);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }




    /**
     * @Description: word��txt�ļ�ת��ͼƬ
     */
    private static List<BufferedImage> wordToImg(InputStream inputStream) throws Exception {
        if (!isWordLicense()) {
            return null;
        }
        try {
            long old = System.currentTimeMillis();
            Document doc = new Document(inputStream);
            ImageSaveOptions options = new ImageSaveOptions(SaveFormat.PNG);
            options.setPrettyFormat(true);
            options.setUseAntiAliasing(true);
            options.setUseHighQualityRendering(true);
            int pageCount = doc.getPageCount();

            List<BufferedImage> imageList = new ArrayList<BufferedImage>();
            for (int i = 0; i < pageCount; i++) {
                OutputStream output = new ByteArrayOutputStream();
                options.setPageIndex(i);
                doc.save(output, options);
                ImageInputStream imageInputStream = ImageIO.createImageInputStream(parse(output));
                imageList.add(ImageIO.read(imageInputStream));

            }
            return imageList;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    //outputStreamתinputStream
    public static ByteArrayInputStream parse(OutputStream out) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos = (ByteArrayOutputStream) out;
        ByteArrayInputStream swapStream = new ByteArrayInputStream(baos.toByteArray());
        return swapStream;
    }



    /**
     * �ϲ���������ͼƬ��һ��ͼƬ
     *
     * @param isHorizontal true����ˮƽ�ϲ���fasle����ֱ�ϲ�
     * @param imgs         ���ϲ���ͼƬ����
     * @return
     * @throws IOException
     */
    public static BufferedImage mergeImage(boolean isHorizontal, List<BufferedImage> imgs) throws IOException {
        // ������ͼƬ
        BufferedImage destImage = null;
        // ������ͼƬ�ĳ��͸�
        int allw = 0, allh = 0, allwMax = 0, allhMax = 0;
        // ��ȡ�ܳ����ܿ�������
        for (int i = 0; i < imgs.size(); i++) {
            BufferedImage img = imgs.get(i);
            allw += img.getWidth();

            if (imgs.size() != i + 1) {
                allh += img.getHeight() + 5;
            } else {
                allh += img.getHeight();
            }


            if (img.getWidth() > allwMax) {
                allwMax = img.getWidth();
            }
            if (img.getHeight() > allhMax) {
                allhMax = img.getHeight();
            }
        }
        // ������ͼƬ
        if (isHorizontal) {
            destImage = new BufferedImage(allw, allhMax, BufferedImage.TYPE_INT_RGB);
        } else {
            destImage = new BufferedImage(allwMax, allh, BufferedImage.TYPE_INT_RGB);
        }
        Graphics2D g2 = (Graphics2D) destImage.getGraphics();
        g2.setBackground(Color.LIGHT_GRAY);
        g2.clearRect(0, 0, allw, allh);
        g2.setPaint(Color.RED);

        // �ϲ�������ͼƬ����ͼƬ
        int wx = 0, wy = 0;
        for (int i = 0; i < imgs.size(); i++) {
            BufferedImage img = imgs.get(i);
            int w1 = img.getWidth();
            int h1 = img.getHeight();
            // ��ͼƬ�ж�ȡRGB
            int[] ImageArrayOne = new int[w1 * h1];
            ImageArrayOne = img.getRGB(0, 0, w1, h1, ImageArrayOne, 0, w1); // ����ɨ��ͼ���и������ص�RGB��������
            if (isHorizontal) { // ˮƽ����ϲ�
                destImage.setRGB(wx, 0, w1, h1, ImageArrayOne, 0, w1); // �����ϰ벿�ֻ���벿�ֵ�RGB
            } else { // ��ֱ����ϲ�
                destImage.setRGB(0, wy, w1, h1, ImageArrayOne, 0, w1); // �����ϰ벿�ֻ���벿�ֵ�RGB
            }
            wx += w1;
            wy += h1 + 5;
        }


        return destImage;
    }




    public static void word2pdf(String docPath,String savePath){

        try {
            String s = "<License><Data><Products><Product>Aspose.Total for Java</Product><Product>Aspose.Words for Java</Product></Products><EditionType>Enterprise</EditionType><SubscriptionExpiry>20991231</SubscriptionExpiry><LicenseExpiry>20991231</LicenseExpiry><SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber></Data><Signature>sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=</Signature></License>";
            ByteArrayInputStream is = new ByteArrayInputStream(s.getBytes());
            License license = new License();
            license.setLicense(is);
            Document document = new Document(docPath);
            document.save(new FileOutputStream(new File(savePath)),SaveFormat.PDF);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

}
