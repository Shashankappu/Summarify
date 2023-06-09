package com.shashanksp.pocketpodcasts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.shashanksp.pocketpodcasts.databinding.ActivitySummarizeTextBinding;

public class SummarizeTextActivity extends AppCompatActivity {
    ClipboardManager clipboardManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySummarizeTextBinding binding = ActivitySummarizeTextBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);

        binding.summarizeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SummarizeTextActivity.this,SummarizedActivity.class);
                startActivity(i);
            }
        });
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        binding.pasteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    ClipData.Item item = clipboardManager.getPrimaryClip().getItemAt(0);
                    String output = item.getText().toString();

                    if(!output.isEmpty()){
                        output = binding.inputEdt.getText() +output;
                        binding.inputEdt.setText(output);
                        binding.inputEdt.moveCursorToVisibleOffset();
                    }else{
                        Toast.makeText(SummarizeTextActivity.this,"No Text to paste",Toast.LENGTH_LONG).show();
                    }
            }
        });
    }
}