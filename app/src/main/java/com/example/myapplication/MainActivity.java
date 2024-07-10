package com.example.myapplication;

// MainActivity.java
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        updateProgressBar(0); // Inicializa com 0%
    }

    private void updateProgressBar(int progress) {
        progressBar.setProgress(progress);

        Drawable drawable = progressBar.getProgressDrawable();

        if (drawable instanceof LayerDrawable) {
            LayerDrawable LayerDrawable = (LayerDrawable) drawable;

            ClipDrawable clipDrawable  = (ClipDrawable) LayerDrawable.findDrawableByLayerId(android.R.id.progress);
            Drawable drawable1 = clipDrawable.getDrawable();

            if (drawable1 instanceof GradientDrawable) {
                GradientDrawable gradientDrawable = (GradientDrawable) drawable1;

                if (progress <= 25) {
                    gradientDrawable.setColor(getResources().getColor(R.color.red, getTheme()));
                } else if (progress <= 50) {
                    gradientDrawable.setColor(getResources().getColor(R.color.yellow, getTheme()));
                } else if (progress <= 75) {
                    gradientDrawable.setColor(getResources().getColor(R.color.green, getTheme()));
                } else {
                    gradientDrawable.setColor(getResources().getColor(R.color.green, getTheme()));
                }
            } else {
                Log.w("MyApp", drawable1.getClass().getSimpleName());
            }
        } else {
            Log.w("MyApp", drawable.getClass().getSimpleName());
        }
    }

    // Atualize a ProgressBar conforme necessário (exemplo)
    private void simulateProgress() {
        new Thread(new Runnable() {
            public void run() {
                for (int progress = 0; progress <= 100; progress++) {
                    final int finalProgress = progress;
                    runOnUiThread(new Runnable() {
                        public void run() {
                            updateProgressBar(finalProgress);
                        }
                    });
                    try {
                        Thread.sleep(100); // Simula algum trabalho sendo feito
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        simulateProgress(); // Inicie a simulação do progresso
    }
}

