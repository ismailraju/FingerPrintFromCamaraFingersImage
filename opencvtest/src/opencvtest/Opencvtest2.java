/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opencvtest;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Range;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author ASUS
 */
public class Opencvtest2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // TODO code application logic here
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat mat = Mat.eye(3, 3, CvType.CV_8UC1);
        System.out.println("mat = " + mat.dump());

//        Processing p = new Processing();

        try {
            byte[] imageInByte;
//  image read
            BufferedImage bufferedImage = ImageIO.read(new File("D:\\upwork\\opencv fingerprint camara\\finger img\\thumb.jpg"));

            System.out.println("getWidth: " + bufferedImage.getWidth());
            System.out.println("getHeight: " + bufferedImage.getHeight());
            System.out.println("getType: " + bufferedImage.getType());
            System.out.println("getPropertyNames: " + bufferedImage.getPropertyNames());
            System.out.println("getData: " + bufferedImage.getData());
            System.out.println("getColorModel: " + bufferedImage.getColorModel());
            System.out.println("getGraphics: " + bufferedImage.getGraphics());
            System.out.println("getMinTileX: " + bufferedImage.getMinTileX());
            System.out.println("getRaster: " + bufferedImage.getRaster());
            System.out.println("getSampleModel: " + bufferedImage.getSampleModel());
            System.out.println("getSource: " + bufferedImage.getSource());
            System.out.println("getTileWidth: " + bufferedImage.getTileWidth());
            System.out.println("getTileHeight: " + bufferedImage.getTileHeight());

// convert BufferedImage to byte array
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
            byteArrayOutputStream.flush();
            imageInByte = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();
            System.out.println("imageInByte.length : " + imageInByte.length);

// convert byte array back to BufferedImage
            InputStream in = new ByteArrayInputStream(imageInByte);
            BufferedImage bImageFromConvert = ImageIO.read(in);

            ImageIO.write(bImageFromConvert, "jpg", new File(
                    "D:\\upwork\\opencv fingerprint camara\\finger img\\new-darksouls.jpg"));

            System.out.println("imageInByte size:" + imageInByte.length);

//bytes To Mat
            int height = bufferedImage.getWidth();
            int width = bufferedImage.getHeight();
            byte[] pixels = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
            System.out.println("pixels: " + pixels.length);
            System.out.println(" width: " + width + " height: " + height);
            Mat matImg = new Mat(width, height, CvType.CV_8UC3);
            matImg.put(0, 0, pixels);
//            matImg.put(0, 0, imageInByte);

//// rotate Image   
//            Mat matImg2 = new Mat(matImg.rows(), matImg.cols(), CvType.CV_8UC3, new Scalar(3));
//
//            Core.transpose(matImg, matImg2);
//            Core.flip(matImg2, matImg2, 1);
//            matImg = matImg2;
////
//// crop finger print 
//            int rowStart = 10;
//            int rowEnd = 100;
//            int colStart = 10;
//            int colEnd = 100;
//            Range rowRange = new Range(rowStart, rowEnd);
//            Range colRange = new Range(colStart, colEnd);
//            matImg.submat(rowRange, colRange);

//// define the upper and lower boundaries of the HSV pixel
//// intensities to be considered 'skin'
//            Scalar lower = new Scalar(0, 48, 80);
//            Scalar upper = new Scalar(20, 255, 255);
////
//// Convert to HSV
//            Mat hsvFrame = new Mat(matImg.rows(), matImg.cols(), CvType.CV_8U, new Scalar(3));
//            Imgproc.cvtColor(matImg, hsvFrame, Imgproc.COLOR_RGB2HSV, 3);
//            matImg = hsvFrame;
//
////
////Mask the image for skin colors
//            Mat skinMask = new Mat(matImg.rows(), matImg.cols(), CvType.CV_8U, new Scalar(3));
//            Core.inRange(matImg, lower, upper, skinMask);
//            Mat currentSkinMask = new Mat(skinMask.rows(), skinMask.cols(), CvType.CV_8U, new Scalar(3));
//            skinMask.copyTo(currentSkinMask);
//
////            matImg = currentSkinMask;
//// apply a series of erosions and dilations to the mask
//// using an elliptical kernel
//            final Size kernelSize = new Size(11, 11);
//            final Point anchor = new Point(-1, -1);
//            final int iterations = 2;
//
//            Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, kernelSize);
//            Imgproc.erode(skinMask, skinMask, kernel, anchor, iterations);
//            Imgproc.dilate(skinMask, skinMask, kernel, anchor, iterations);
//
//// blur the mask to help remove noise, then apply the
//// mask to the frame
//            final Size ksize = new Size(3, 3);
//
//            Mat skin = new Mat(skinMask.rows(), skinMask.cols(), CvType.CV_8U, new Scalar(3));
//            Imgproc.GaussianBlur(skinMask, skinMask, ksize, 0);
//            Core.bitwise_and(matImg, matImg, skin, skinMask);
//            // convert to grayscale
//            Mat frameGrey = new Mat(matImg.rows(), matImg.cols(), CvType.CV_8UC1);
//            Imgproc.cvtColor(matImg, matImg, Imgproc.COLOR_BGR2GRAY, 1);
//            matImg = p.thinning(matImg);
//            matImg = p.skinDetection(matImg);

            matToJpg(matImg);
        } catch (IOException iOException) {
            iOException.printStackTrace();

        }

    }

    public static void matToJpg(Mat matImg) throws IOException {

        MatOfByte mob = new MatOfByte();
        Imgcodecs.imencode(".jpg", matImg, mob);
        byte ba[] = mob.toArray();
        BufferedImage bi = ImageIO.read(new ByteArrayInputStream(ba));
        ImageIO.write(bi, "jpg", new File("D:\\upwork\\opencv fingerprint camara\\finger img\\out.jpg"));

    }

}
