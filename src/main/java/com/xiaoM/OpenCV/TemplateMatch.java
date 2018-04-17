package com.xiaoM.OpenCV;


import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;
import static org.bytedeco.javacpp.opencv_imgproc.CV_TM_CCORR_NORMED;
import static org.bytedeco.javacpp.opencv_imgproc.cvMatchTemplate;

import org.bytedeco.javacpp.opencv_core.IplImage;
import org.bytedeco.javacpp.opencv_core;


public class TemplateMatch {

    private opencv_core.IplImage image;

    public void load(String filename) {
        image = cvLoadImage(filename); 
    }

    public boolean matchTemplate(IplImage source) {
        boolean matchRes;
        IplImage result = cvCreateImage(opencv_core.cvSize(
                source.width() - this.image.width() + 1,
                source.height() - this.image.height() + 1),
                opencv_core.IPL_DEPTH_32F, 1);

        opencv_core.cvZero(result);
        cvMatchTemplate(source, this.image, result, CV_TM_CCORR_NORMED);
        double[] minVal = new double[2];
        double[] maxVal = new double[2];
        cvMinMaxLoc(result, minVal, maxVal);
        matchRes = maxVal[0] > 0.99f ? true : false;
        cvReleaseImage(result);//释放图像
        return matchRes;
    }
}
