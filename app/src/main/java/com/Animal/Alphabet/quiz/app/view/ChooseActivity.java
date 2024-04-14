package com.Animal.Alphabet.quiz.app.view;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.Animal.Alphabet.quiz.app.R;
import com.Animal.Alphabet.quiz.app.model.MusicManager;


public class ChooseActivity extends AppCompatActivity {
    private boolean isMusicPlaying = false;
    private boolean wasMusicPlayingBeforePause = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        SharedPreferences preferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        boolean isFirstRun = preferences.getBoolean("is_first_run", true);

        if (isFirstRun) {
            MusicManager.play(this);
            isMusicPlaying = true;
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("is_first_run", false);
            editor.apply();
        }

        Button quizButton = findViewById(R.id.quiz_button);
        quizButton.setOnClickListener(v -> {
            Intent intent = new Intent(ChooseActivity.this, AnotherActivity.class);
            startActivity(intent);
        });

        Button exitButton = findViewById(R.id.exit);
        exitButton.setOnClickListener(v -> {
            finishAffinity();
        });

        Button soundButton = findViewById(R.id.sound_button);
        soundButton.setOnClickListener(v -> toggleMusic());

        wasMusicPlayingBeforePause = preferences.getBoolean("was_music_playing_before_pause", false);
        if (wasMusicPlayingBeforePause) {
            MusicManager.play(this);
            isMusicPlaying = true;
            soundButton.setText("Sound on");
        } else {
            soundButton.setText("Sound off");
        }
    }

    private void toggleMusic() {
        Button soundButton = findViewById(R.id.sound_button);
        if (isMusicPlaying) {
            MusicManager.pause(this);
            soundButton.setText("Sound off");
        } else {
            MusicManager.play(this);
            soundButton.setText("Sound on");
        }
        isMusicPlaying = !isMusicPlaying;
        // Сохранение текущего состояния музыки
        SharedPreferences preferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("was_music_playing_before_pause", isMusicPlaying);
        editor.apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MusicManager.release();
    }
}
