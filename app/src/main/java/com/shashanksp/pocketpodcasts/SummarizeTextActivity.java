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
    Summarizer summarizer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySummarizeTextBinding binding = ActivitySummarizeTextBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        summarizer = new Summarizer();
        binding.summarizeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Summarize the text code or function goes  here.
                String input_text = binding.inputEdt.getText().toString();
                input_text = summarizer.Summarize(input_text,5);
                Intent i = new Intent(SummarizeTextActivity.this, SummarizedActivity.class);
                i.putExtra("summarized_text", input_text);
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

                if (!output.isEmpty()) {
                    output = binding.inputEdt.getText() + output;
                    binding.inputEdt.setText(output);
                    binding.inputEdt.moveCursorToVisibleOffset();
                } else {
                    Toast.makeText(SummarizeTextActivity.this, "No Text to paste", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}