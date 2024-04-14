package com.Animal.Alphabet.quiz.app.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;

import com.Animal.Alphabet.quiz.app.R;

public class MusicManager {
    private static MediaPlayer mediaPlayer;
    private static SharedPreferences preferences;
    public static boolean isPlaying = false;

    public static void play(Context context) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, R.raw.music);
            mediaPlayer.setLooping(true);
        }
        mediaPlayer.start();
        isPlaying = true;
        saveMusicState(context);
        mediaPlayer.setOnCompletionListener(mp -> {
            mediaPlayer.seekTo(0);
            mediaPlayer.start();
        });
    }

    public static void pause(Context context) {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
        isPlaying = false;
        saveMusicState(context);
    }

    public static void release() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private static void saveMusicState(Context context) {
        if (preferences == null) {
            preferences = context.getSharedPreferences("music_prefs", Context.MODE_PRIVATE);
        }
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("is_music_playing", isPlaying);
        editor.apply();
    }

    public static void loadMusicState(Context context) {
        if (preferences == null) {
            preferences = context.getSharedPreferences("music_prefs", Context.MODE_PRIVATE);
        }
        isPlaying = preferences.getBoolean("is_music_playing", false);
    }
}
