package com.devil.learnbangla.chandra;

import android.media.Image;
import android.widget.ImageView;

public class Word {
    private String mBangla;
    private String mEnglish;
    private  int mAudio;
    private static final int  Not=-1;
    private int mImg=Not;
    public Word(String eng, String bng){
        mEnglish = eng;
        mBangla = bng;
    }
    public Word(String eng, String bng,int audio){
        mEnglish = eng;
        mBangla = bng;
        mAudio  = audio;
    }
    public Word(String eng, String bng,int img,int audio){
        mEnglish = eng;
        mBangla = bng;
        mImg = img;
        mAudio = audio;
    }

    public String getBangla() {

        return mBangla;
    }

    public String getEnglish() {

        return mEnglish;
    }
    public int getImag() {

        return mImg;
    }

    public boolean isImg(){
        if(mImg!=-1)
            return  true;
        return false;
    }
    public int getAudio(){
        return  mAudio;
    }
}
