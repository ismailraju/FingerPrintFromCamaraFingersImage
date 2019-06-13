/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opencvtest.ExtractingContours;

import java.util.List;
import org.opencv.core.*;
import static org.opencv.core.CvType.CV_8UC1;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author ASUS
 */
public class ExtractingContours {

    public void run() {

        try {
            Processing p = new Processing();
//            Mat img0 = p.get();

// read in the apple (change path to the file)
            Mat img0 = Imgcodecs.imread("D:\\upwork\\opencv fingerprint camara\\finger img\\thumb.jpg", CvType.CV_8UC3);
//            HighGui.imshow("input", img0);
            p.showResult(img0);
//            Mat img1 = null;
//
////        Imgproc.cvtColor(img0, img1, Imgproc.CV_RGB2GRAY);
////            Imgproc.cvtColor(img0, img1, Imgproc.COLOR_RGB2GRAY );
//            Mat image = new Mat(img0.rows(), img0.cols(), CvType.CV_8UC1);
//            Imgproc.cvtColor(img0, image, Imgproc.COLOR_BGR2GRAY);
//
//            // apply your filter
//            Imgproc.Canny(img1, img1, 100, 200);
//
//            // find the contours
//            List<MatOfPoint> contours = null;
//            Imgproc.findContours(img1, contours, img1, Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_NONE);
//
//            HighGui.imshow("threshold_output", img1);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
