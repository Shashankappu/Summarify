package com.shashanksp.pocketpodcasts;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.shashanksp.pocketpodcasts.databinding.ActivitySummarizedBinding;

public class SummarizedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySummarizedBinding binding = ActivitySummarizedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.menuFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //convert on fab to multi fab and show bookmark and copy fabs optional convert to word or docs
            }
        });
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}