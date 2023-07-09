package com.shashanksp.pocketpodcasts;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.shashanksp.pocketpodcasts.databinding.ActivitySummarizedBinding;


public class SummarizedActivity extends AppCompatActivity {
    ClipboardManager clipboardManager;
    String input;
    private static TextToSpeech textToSpeech;
    static int count = 111;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference storeRef = database.getReference("Bookmarked_text");



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
                    textToSpeech.setSpeechRate(0.8F);
                } else {
                    // TTS initialization failed
                    Toast.makeText(SummarizedActivity.this,"TTS Initialization Failed!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //TTS button
        binding.micBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!textToSpeech.isSpeaking()) {
                    createNotificationChannel();
                    showTTSNotification();
                    convertTextToSpeech(binding.resText.getText().toString());
                    binding.micBtn.setImageDrawable(getResources().getDrawable(R.drawable.mic_on));
                } else {
                    textToSpeech.stop();
                    hideTTSNotification();
                    binding.micBtn.setImageDrawable(getResources().getDrawable(R.drawable.mic_off));
                }
            }
        });
        //Back Btn
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textToSpeech.stop();
                hideTTSNotification();
                onBackPressed();
            }
        });
        //Copy btn
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

        //bookmark text save it in firebase database
        binding.bookmarkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToBookmarks(binding.resText.getText().toString());
            }
        });

        //Show BookmarksActivity
        binding.allBookmarksBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SummarizedActivity.this, BookmarksActivity.class);
                startActivity(i);
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
    private void addToBookmarks(String summ_text){
        storeRef.child(String.valueOf(count++)).setValue(summ_text).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(SummarizedActivity.this,"Text Bookmarked!",Toast.LENGTH_LONG).show();
            }
        });
    }

}