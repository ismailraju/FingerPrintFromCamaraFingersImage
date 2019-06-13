/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opencvtest;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;

/**
 *
 * @author ASUS
 */
class Processing {

    public static void matToJpg(Mat matImg, String n) {
        try {

            MatOfByte mob = new MatOfByte();
            Imgcodecs.imencode(".jpg", matImg, mob);
            byte ba[] = mob.toArray();
            BufferedImage bi = ImageIO.read(new ByteArrayInputStream(ba));
            ImageIO.write(bi, "jpg", new File("D:\\upwork\\opencv fingerprint camara\\finger img\\" + n + ".jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Mat gett() {
        byte[] imageInByte;
        Mat matImg = null;
//  image read
        BufferedImage bufferedImage;
        try {
            bufferedImage = ImageIO.read(new File("D:\\upwork\\opencv fingerprint camara\\finger img\\thumb.jpg"));
            //bytes To Mat
            int height = bufferedImage.getWidth();
            int width = bufferedImage.getHeight();
            byte[] pixels = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
            System.out.println("pixels: " + pixels.length);
            System.out.println(" width: " + width + " height: " + height);
            matImg = new Mat(width, height, CvType.CV_8UC3);
            matImg.put(0, 0, pixels);

        } catch (IOException ex) {
            Logger.getLogger(Processing.class.getName()).log(Level.SEVERE, null, ex);
        }
        return matImg;
    }
}
