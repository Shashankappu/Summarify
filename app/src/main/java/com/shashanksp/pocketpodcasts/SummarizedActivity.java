package com.shashanksp.pocketpodcasts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.shashanksp.pocketpodcasts.databinding.ActivitySummarizedBinding;


public class SummarizedActivity extends AppCompatActivity {
    ClipboardManager clipboardManager;
    String input;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySummarizedBinding binding = ActivitySummarizedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Initialize ClipboardManager
        clipboardManager = (ClipboardManager) getSystemService(SummarizedActivity.CLIPBOARD_SERVICE);
        //binding.resText.setText("Summarized text appears here!");


        String text = getIntent().getExtras().getString("summarized_text");


        //just for now.
        binding.resText.setText(text);

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input = binding.resText.getText().toString();
                if(!input.isBlank()){
                    ClipData data = ClipData.newPlainText("data",input);
                    clipboardManager.setPrimaryClip(data);
                    Toast.makeText(SummarizedActivity.this,"Text Copied",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(SummarizedActivity.this,"Text not Copied",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}