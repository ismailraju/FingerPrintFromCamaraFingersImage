/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opencvtest;

import java.util.Arrays;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

/**
 *
 * @author ASUS
 */
class Thinning1 {

    private boolean B[][];

    Mat doJaniThinning(Mat Image) {
        B = new boolean[Image.rows()][Image.cols()];
        // Inverse of B
        boolean[][] B_ = new boolean[Image.rows()][Image.cols()];
        for (int i = 0; i < Image.rows(); i++) {
            for (int j = 0; j < Image.cols(); j++) {
                B[i][j] = (Image.get(i, j)[0] > 10); //not a mistake, in matlab first invert and then morph
            }
        }
        boolean[][] prevB = new boolean[Image.rows()][Image.cols()];
        final int maxIter = 1000;
        for (int iter = 0; iter < maxIter; iter++) {
            // Assign B to prevB
            for (int i = 0; i < Image.rows(); i++) {
                System.arraycopy(B[i], 0, prevB[i], 0, Image.cols());
            }

            //Iteration #1
            for (int i = 0; i < Image.rows(); i++) {
                for (int j = 0; j < Image.cols(); j++) {
                    B_[i][j] = !(B[i][j] && G1(i, j) && G2(i, j) && G3(i, j)) && B[i][j];
                }
            }

            // Assign result of iteration #1 to B, so that iteration #2 will see the results
            for (int i = 0; i < Image.rows(); i++) {
                System.arraycopy(B_[i], 0, B[i], 0, Image.cols());
            }

            //Iteration #2
            for (int i = 0; i < Image.rows(); i++) {
                for (int j = 0; j < Image.cols(); j++) {
                    B_[i][j] = !(B[i][j] && G1(i, j) && G2(i, j) && G3_(i, j)) && B[i][j];
                }
            }

            // Assign result of Iteration #2 to B
            for (int i = 0; i < Image.rows(); i++) {
                System.arraycopy(B_[i], 0, B[i], 0, Image.cols());
            }

            // stop when it doesn't change anymore
            boolean convergence = true;
            for (int i = 0; i < Image.rows(); i++) {
                convergence &= Arrays.equals(B[i], prevB[i]);
            }
            if (convergence) {
                break;
            }
        }

        removeFalseRidgeEndings(Image);

        Mat r = Mat.zeros(Image.size(), CvType.CV_8UC1);

        for (int i = 0; i < Image.rows(); i++) {
            for (int j = 0; j < Image.cols(); j++) {
                if (B[i][j]) {
                    r.put(i, j, 255);
                }
            }
        }
        return r;
    }

    // remove ridge endings shorter than minimumRidgeLength
    private void removeFalseRidgeEndings(Mat Image) {
        int minimumRidgeLength = 5;
        for (int i = 0; i < Image.rows(); i++) {
            for (int j = 0; j < Image.cols(); j++) {
                if (B[i][j] && neighbourCount(i, j) == 1) {
                    removeEnding(i, j, minimumRidgeLength);
                }
            }
        }
    }

    // follow ridge recursively and remove if shorter than minimumlength
    private boolean removeEnding(int i, int j, int minimumLength) {
        if (minimumLength < 0) {
            return true;
        }
        if (neighbourCount(i, j) > 1) {
            return false;
        }
        B[i][j] = false;
        if (neighbourCount(i, j) == 0) {
            return false;
        }
        int index = 0;
        for (int a = 1; a <= 8; a++) {
            if (x(a, i, j)) {
                index = a;
                break;
            }
        }
        int _i = i, _j = j;
        switch (index) {
            case 1:
                _i = i + 1;
                break;
            case 2:
                _i = i + 1;
                _j = j + 1;
                break;
            case 3:
                _j = j + 1;
                break;
            case 4:
                _i = i - 1;
                _j = j + 1;
                break;
            case 5:
                _i = i - 1;
                break;
            case 6:
                _i = i - 1;
                _j = j - 1;
                break;
            case 7:
                _j = j - 1;
                break;
            case 8:
                _i = i + 1;
                _j = j - 1;
                break;
        }
        boolean ok = removeEnding(_i, _j, minimumLength - 1);
        if (ok) {
            B[i][j] = true;
        }
        return ok;
    }

    private int neighbourCount(int i, int j) {
        int cn = 0;
        for (int a = 1; a <= 8; a++) {
            if (x(a, i, j)) {
                cn++;
            }
        }
        return cn;
    }

    private boolean x(int a, int i, int j) {
        try {
            switch (a) {
                case 1:
                    return B[i + 1][j];
                case 2:
                    return B[i + 1][j + 1];
                case 3:
                    return B[i][j + 1];
                case 4:
                    return B[i - 1][j + 1];
                case 5:
                    return B[i - 1][j];
                case 6:
                    return B[i - 1][j - 1];
                case 7:
                    return B[i][j - 1];
                case 8:
                    return B[i + 1][j - 1];
            }
        } catch (IndexOutOfBoundsException e) {
            return false;
        }
        return false;
    }

    private boolean G1(int i, int j) {
        int X = 0;
        for (int q = 1; q <= 4; q++) {
            if (!x(2 * q - 1, i, j) && (x(2 * q, i, j) || x(2 * q + 1, i, j))) {
                X++;
            }
        }
        return X == 1;
    }

    private boolean G2(int i, int j) {
        int m = Math.min(n1(i, j), n2(i, j));
        return (m == 2 || m == 3);
    }

    private int n1(int i, int j) {
        int r = 0;
        for (int q = 1; q <= 4; q++) {
            if (x(2 * q - 1, i, j) || x(2 * q, i, j)) {
                r++;
            }
        }
        return r;
    }

    private int n2(int i, int j) {
        int r = 0;
        for (int q = 1; q <= 4; q++) {
            if (x(2 * q, i, j) || x(2 * q + 1, i, j)) {
                r++;
            }
        }
        return r;
    }

    private boolean G3(int i, int j) {
        return (x(2, i, j) || x(3, i, j) || !x(8, i, j)) && x(1, i, j);
    }

    private boolean G3_(int i, int j) {
        return (x(6, i, j) || x(7, i, j) || !x(4, i, j)) && x(5, i, j);
    }

}
