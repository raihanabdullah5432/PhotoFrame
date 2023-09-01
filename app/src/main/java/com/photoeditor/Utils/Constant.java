package com.photoeditor.Utils;

public class Constant {


    public static String url = "https://drive.google.com/u/0/uc?id=%id%&export=download";

    public static String getUrl(String id){

        String uri = url.replace("%id%",id);

        return  uri;

    }


}
