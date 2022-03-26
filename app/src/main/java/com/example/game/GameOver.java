package com.example.game;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOver extends AppCompatActivity {
    private Button startGameAgain;
    private TextView displayscore;
    private String score1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        startGameAgain=findViewById(R.id.btn);
        displayscore=findViewById(R.id.displayscore);
        score1= getIntent().getExtras().get("score").toString();

        startGameAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainIntent = new Intent(GameOver.this, MainActivity.class);
                startActivity(mainIntent);
            }
        });
        displayscore.setText("score:"+score1);

    }
}
