package com.example.game;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundPlayer {
    private static SoundPool soundPool;
    private static int hitSound;
    private static int overSound;
    private static int gameSound;

    public SoundPlayer (Context context){
        soundPool=new SoundPool(3, AudioManager.STREAM_MUSIC,0);
        hitSound=  soundPool.load(context,R.raw.orange,1);
        overSound= soundPool.load(context,R.raw.pink,1);
        gameSound= soundPool.load(context,R.raw.fishtune,1);

    }
    public void playHitsound(){

        soundPool.play(hitSound,1.0f,1.0f,1,0,1.0f);
    }
    public  void playOverSound(){

        soundPool.play(overSound,1.0f,1.0f,1,0,1.0f);
    }
    public void playGameSound(){
        soundPool.play(gameSound,3.0f,1.0f,1,0,1.0f);
    }
}
