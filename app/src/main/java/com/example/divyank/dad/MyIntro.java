package com.example.divyank.dad;

/**
 * Created by Divyank on 06-06-2016.
 */

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro;

public class MyIntro extends AppIntro {

    @Override
    public void init(Bundle savedInstanceState) {
        //Here we are adding the four slides
        addSlide(SampleSlide.newInstance(R.layout.intro));
        addSlide(SampleSlide.newInstance(R.layout.intro1));
        addSlide(SampleSlide.newInstance(R.layout.intro2));
        addSlide(SampleSlide.newInstance(R.layout.intro3));
        addSlide(SampleSlide.newInstance(R.layout.intro4));
        // Hide Skip/Done button
        showSkipButton(true);
        showStatusBar(false);

        setVibrate(true);
        setVibrateIntensity(30);
        setDepthAnimation();
    }

    @Override
    public void onSkipPressed() {
        // Do something when users tap on Skip button.
        finish();
        Toast.makeText(getApplicationContext(),
                getString(R.string.skip), Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onNextPressed() {
        // Do something when users tap on Next button.
    }
    @Override
    public void onDonePressed() {
        // Do something when users tap on Done button.
        finish();
    }

    @Override
    public void onSlideChanged() {
        // Do something when slide is changed
    }
}