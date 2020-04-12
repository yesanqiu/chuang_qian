package com.ysq.chuang_qian.utils;

import net.sourceforge.lept4j.ILeptonica;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.Word;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ImageUtil {

    private static String imageUrl = "E:\\image\\vc.gif";

    private static String outputUrl = "E:\\output\\vc.gif";

    private static String outputUrl_1 = "E:\\output\\vc1.gif";
    private static String outputUrl_2 = "E:\\output\\vc2.gif";
    private static String outputUrl_3 = "E:\\output\\vc3.gif";
    private static String outputUrl_4 = "E:\\output\\vc4.gif";

    public static  void removeBackground (InputStream ip){

        int blue = 150;
        int green = 1;
        int red = 1;
        try {
//            BufferedImage image = ImageIO.read(new File(imageUrl));
            BufferedImage image = ImageIO.read(ip);
                for (int x = 0; x < image.getWidth(); x++) {
                    for (int y = 0; y < image.getHeight(); y++) {
                        Color color = new Color(image.getRGB(x, y));
//                    System.out.println("bule:" + color.getBlue() + "---" + "green:" + color.getGreen() + "---" + "red:" + color.getRed() + "---");
                        int l = color.getBlue() + color.getGreen() + color.getRed();
                        if (color.getBlue() > blue && color.getGreen() < green && color.getRed() < red) {
                            image.setRGB(x, y, Color.WHITE.getRGB());
                        } else {

                            image.setRGB(x, y, Color.BLACK.getRGB());
                        }
                    }
                }
            System.out.println(excuteTess4j(image));
            for(BufferedImage bi:splitImage(image)){

                System.out.println(excuteTess4j(bi));
            }
            File output = new File(outputUrl);
            if(!output.exists()){
                File dir = output.getParentFile();
                if(!dir.exists()){
                    dir.mkdir();
                }
                output.createNewFile();
            }
            ImageIO.write(image,"gif",output);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<BufferedImage> splitImage(BufferedImage img) throws Exception {

        List<BufferedImage> subImgs = new ArrayList<>();
        BufferedImage bi_1 = img.getSubimage(5, 0, 12, 23);
        BufferedImage bi_2 = img.getSubimage(16, 0, 13, 23);
        BufferedImage bi_3 = img.getSubimage(31, 0, 12, 23);
        BufferedImage bi_4 = img.getSubimage(41, 0, 18, 23);
        subImgs.add(bi_1);
        subImgs.add(bi_2);
        subImgs.add(bi_3);
        subImgs.add(bi_4);
        File output_1 = new File(outputUrl_1);
        File output_2 = new File(outputUrl_2);
        File output_3 = new File(outputUrl_3);
        File output_4 = new File(outputUrl_4);
        ImageIO.write(bi_1,"gif",output_1);
        ImageIO.write(bi_2,"gif",output_2);
        ImageIO.write(bi_3,"gif",output_3);
        ImageIO.write(bi_4,"gif",output_4);
        return subImgs;
    }


    public static String excuteTess4j_4(BufferedImage image){
        String result = "";
        File pic = new File(outputUrl);
        ITesseract iTesseract = new Tesseract();
        try {
            for(Word w:iTesseract.getWords(image,4)){
                result += w.getText();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String excuteTess4j(BufferedImage image){
        String result = "";
        File pic = new File(outputUrl);
        ITesseract iTesseract = new Tesseract();
        try {
            result = iTesseract.getWords(image,1).get(0).getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean isBlue(int colorInt) {
        Color color = new Color(colorInt);
        int rgb = color.getRed() + color.getGreen() + color.getBlue();
        if (rgb == 153) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
//        removeBackground();
    }
}
