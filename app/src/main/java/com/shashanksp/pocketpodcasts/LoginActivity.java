package com.shashanksp.pocketpodcasts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.shashanksp.pocketpodcasts.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private boolean passwordVisbile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        EditText passwordedt = binding.passwordEdittext;

        //Logging in the  user
        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log in the user
            }
        });

        //Move to Register Account Activity
        binding.createaccTxtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        passwordedt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right = 2;
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    if(motionEvent.getRawX()>=passwordedt.getRight()-passwordedt.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = passwordedt.getSelectionEnd();
                        if(passwordVisbile){
                            passwordedt.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.visibility_off_icon,0);
                            passwordedt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            passwordVisbile= false;
                        }else{
                            passwordedt.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.visibility_icon,0);
                            passwordedt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            passwordVisbile= true;
                        }
                        passwordedt.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
    }
}