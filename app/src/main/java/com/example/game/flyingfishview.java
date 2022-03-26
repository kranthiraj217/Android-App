package com.example.game;

import android.app.slice.SliceItem;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import android.widget.Toast;
 // Activity for drawing a fish
public class flyingfishview extends View {

    private Bitmap fish[] = new Bitmap[2]; //creation of 2 fish
    private int fishX = 10; //fish position w.r.t x-axis
    private  int fishY; // fish position w.r.t y-axis
    private  int fishSpeed; // fish speed
    private  int canvasWidth, canvasHeight; //layout variables
    private SoundPlayer sound; //background music variable of game

     // food object variable declaration
    private  int yellowX, yellowY, yellowSpeed = 16;
    private Paint yellowPaint = new Paint();

    private  int greenX, greenY, greenSpeed = 20;
    private Paint greenPaint = new Paint();

    private  int redX, redY, redSpeed = 25;
    private Paint redPaint = new Paint();

    // variable declaration for score and life of fish
    private  int score, lifeCounterOfFish;


    private boolean touch = false;

    private Bitmap backgroundImage; //variable for background
    private Paint scorePaint = new Paint();// variable to add score
    private Bitmap life[] = new Bitmap[2]; // variable to add life of fish

    //Constructor
    public flyingfishview(Context context) {
        super(context);
        sound= new SoundPlayer(context);
        sound=new SoundPlayer(context);


        fish[0] = BitmapFactory.decodeResource(getResources(), R.drawable.fish1); //go to drawable folder and view the icon
        fish[1]= BitmapFactory.decodeResource(getResources(),R.drawable.fish1);

        backgroundImage = BitmapFactory.decodeResource(getResources(), R.drawable.background); //adding backgorund image

        // initialization of food in colors
        yellowPaint.setColor(Color.YELLOW);
        yellowPaint.setAntiAlias(false);

        greenPaint.setColor(Color.GREEN);
        greenPaint.setAntiAlias(false);

        redPaint.setColor(Color.RED);
        redPaint.setAntiAlias(false);

       // to display score
        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(70);
        scorePaint.setTypeface(Typeface.DEFAULT_BOLD);
        scorePaint.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.hearts); //life of fish
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.greyheart1); //if a fish loses a life

        fishY = 550;
        score = 0;
        lifeCounterOfFish = 3;
    }



    // method to show the actions performed by the fish
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvasWidth = canvas.getWidth(); //intializing getwidth()
        canvasHeight = canvas.getHeight(); // intitializing getheight()


        canvas.drawBitmap(backgroundImage, 0, 0, null);
        int minFishY = fish[0].getHeight(); // movement of fish
        int maxFishY = canvasHeight - fish[0].getHeight() * 3;
        fishY = fishY + fishSpeed;

        if (fishY < minFishY) {
            fishY = minFishY;
        }

        if (fishY > maxFishY) {
            fishY = maxFishY;
        }
        fishSpeed = fishSpeed + 2;

        // toouchevent mthod intialization
        if (touch) {
            canvas.drawBitmap(fish[1], fishX, fishY, null);
            touch = false;
        } else {
            canvas.drawBitmap(fish[0], fishX, fishY, null);

        }

        yellowX = yellowX - yellowSpeed;
        if (hitBallChecker(yellowX, yellowY)) {
            score = score + 5;
            yellowX = -100;
            sound.playHitsound();
        }
        if (yellowX < 0) {
            yellowX = canvasWidth + 21;
            yellowY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }
        canvas.drawCircle(yellowX, yellowY, 25, yellowPaint);


        greenX = greenX - greenSpeed;
        if (hitBallChecker(greenX, greenY)) {
            score = score + 10;
            greenX = -100;
            sound.playHitsound();
        }
        if (greenX < 0) {
            greenX = canvasWidth + 21;
            greenY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }
        canvas.drawCircle(greenX, greenY, 25, greenPaint);


        redX = redX - redSpeed;

        // whenever fish hits the red ball its life gets -1
        if (hitBallChecker(redX, redY)) {

            redX = -70;
            lifeCounterOfFish--;
            sound.playOverSound();

             // when fish has no lives it moves to the end screen and play again
            if (lifeCounterOfFish == 0) {
                Toast.makeText(getContext(), "Game Over", Toast.LENGTH_SHORT).show();

                Intent gameOverIntent = new Intent(getContext(), GameOver.class);
                gameOverIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                gameOverIntent.putExtra("score", score);
                getContext().startActivity(gameOverIntent);

            }
        }
        if (redX < 0) {
            redX = canvasWidth + 21;
            redY = (int) Math.floor(Math.random() * (maxFishY - minFishY)) + minFishY;
        }
        canvas.drawCircle(redX, redY, 30, redPaint);

        canvas.drawText("Score : " + score, 20, 60, scorePaint); //to display the score in spalsh
        // for loop for life of fish
        for (int i = 0; i < 3; i++) {
            int x = (int) (580 + life[0].getWidth() * 1.5 * i);
            int y = 30;
          // if condition to check the life of fish and display grey geart
            if (i < lifeCounterOfFish) {
                canvas.drawBitmap(life[0], x, y, null);
            } else {
                canvas.drawBitmap(life[1], x, y, null);
            }
        }


    }
    // method for hitball action
    public boolean hitBallChecker(int x, int y)
    {
        if (fishX < x && x < (fishX + fish[0].getWidth()) && fishY < y && y < (fishY + fish[0].getHeight()))
        {
            return  true;
        }
        return  false;
    }
    //movement of fish onTouchEvent method declaration
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            touch = true;
            fishSpeed = -22;
        }
        return true;
    }
}
