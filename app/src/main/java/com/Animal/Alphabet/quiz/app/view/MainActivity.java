package com.Animal.Alphabet.quiz.app.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.Animal.Alphabet.quiz.app.R;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        openChooseActivity();
    }
    private void openChooseActivity() {
        Intent intent = new Intent(this, ChooseActivity.class);
        startActivity(intent);
    }

}
