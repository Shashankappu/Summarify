package com.shashanksp.pocketpodcasts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.res.ResourcesCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Toast;

import com.shashanksp.pocketpodcasts.databinding.ActivitySummarizedBinding;

import java.util.Locale;


public class SummarizedActivity extends AppCompatActivity {
    ClipboardManager clipboardManager;
    String input;
    boolean isMicOn = false;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySummarizedBinding binding = ActivitySummarizedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize ClipboardManager
        clipboardManager = (ClipboardManager) getSystemService(SummarizedActivity.CLIPBOARD_SERVICE);
        String text = getIntent().getExtras().getString("summarized_text");
        binding.resText.setText(text);

        //TTS
        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    // TTS initialization successful
                    textToSpeech.setLanguage(Locale.UK);
                    textToSpeech.setSpeechRate(0.7F);
                } else {
                    // TTS initialization failed
                }
            }
        });
        binding.micBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isMicOn) {
                    createNotificationChannel();
                    showTTSNotification();
                    convertTextToSpeech(binding.resText.getText().toString());

                    binding.micBtn.setImageDrawable(getResources().getDrawable(R.drawable.mic_on));
                    isMicOn = true;
                } else {
                    textToSpeech.stop();
                    hideTTSNotification();
                    binding.micBtn.setImageDrawable(getResources().getDrawable(R.drawable.mic_off));
                    isMicOn = false;
                }
            }
        });

        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToSpeech.stop();
                hideTTSNotification();
                onBackPressed();
            }
        });

        binding.copyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                input = binding.resText.getText().toString();
                if (!input.isBlank()) {
                    ClipData data = ClipData.newPlainText("data", input);
                    clipboardManager.setPrimaryClip(data);
                    Toast.makeText(SummarizedActivity.this, "Text Copied", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(SummarizedActivity.this, "Text not Copied", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void convertTextToSpeech(String text) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "TTS Channel";
            String description = "Channel for Text-to-Speech";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("tts_channel", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void showTTSNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "tts_channel";
            String channelName = "TTS Channel";
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(R.drawable.mic_on)
                    .setContentTitle("TTS is on")
                    .setContentText("Text-to-Speech is active")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            notificationManagerCompat.notify(1, builder.build());
        }
    }
    private void hideTTSNotification() {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.cancel(1); // Cancels the notification with the specified ID (1 in this case)
    }


}