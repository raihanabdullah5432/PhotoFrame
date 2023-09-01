package com.photoeditor.Adapter;

public class FrameModel {
    String frame,position,text1,text2;

    public FrameModel(String frame, String position, String text1, String text2) {
        this.frame = frame;
        this.position = position;
        this.text1 = text1;
        this.text2 = text2;
    }


    public String getFrame() {
        return frame;
    }

    public void setFrame(String frame) {
        this.frame = frame;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }
}
