/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opencvtest.ExtractingContours;

import org.opencv.core.Core;

/**
 *
 * @author ASUS
 */
public class main {

    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        ExtractingContours contours;
        contours = new ExtractingContours();
        contours.run();
    }
}
