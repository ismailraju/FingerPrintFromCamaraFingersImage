/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opencvtest.ExtractingContours;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.WindowConstants;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.highgui.HighGui;

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

    public Mat get() {
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
            ex.printStackTrace();
        }
        return matImg;
    }

    public void showResult(Mat img) {
        Imgproc.resize(img, img, new Size(img.width(), img.height()));
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", img, matOfByte);

        byte[] byteArray = matOfByte.toArray();
        BufferedImage bufImage = null;
        try {
            InputStream in = new ByteArrayInputStream(byteArray);
            bufImage = ImageIO.read(in);
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frame.getContentPane().add(new JLabel(new ImageIcon(bufImage)));
            frame.pack();
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
