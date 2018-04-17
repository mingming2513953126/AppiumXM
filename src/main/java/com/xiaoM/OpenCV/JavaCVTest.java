package com.xiaoM.OpenCV;

import static org.bytedeco.javacpp.opencv_imgcodecs.cvLoadImage;

public class JavaCVTest {

    public static void main(String[] args) {
        System.out.println("START...");
        TemplateMatch tm = new TemplateMatch();//实例化TemplateMatch对象
        tm.load("img/585.png");//加载带比对图片，注此图片必须小于源图
        boolean result = tm.matchTemplate(cvLoadImage("img/58home.png"));//校验585.png是否包含于原图58home.png
        if (result){//打印匹配结果，boolean
            System.out.println("match");
        }else{
            System.out.println("un-match");
        }
        System.out.println("END...");
    }
}