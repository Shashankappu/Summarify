package com.shashanksp.pocketpodcasts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.shashanksp.pocketpodcasts.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {
    private boolean passwordVisbile;
    private boolean cnfpasswordVisbile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRegisterBinding binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EditText passwordedt = binding.passwordEdittext;
        EditText cnfpasswordedt = binding.confirmpasswordText;

        //Registering user
        binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Register User
            }
        });

        //Moving back to Login Activity
        binding.loginhereTxtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //show password option first field
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

        //show password option for confirming
        cnfpasswordedt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                final int Right = 2;
                if(motionEvent.getAction()==MotionEvent.ACTION_UP){
                    if(motionEvent.getRawX()>=cnfpasswordedt.getRight()-cnfpasswordedt.getCompoundDrawables()[Right].getBounds().width()){
                        int selection = cnfpasswordedt.getSelectionEnd();
                        if(cnfpasswordVisbile){
                            cnfpasswordedt.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.visibility_off_icon,0);
                            cnfpasswordedt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                            cnfpasswordVisbile= false;
                        }else{
                            cnfpasswordedt.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.visibility_icon,0);
                            cnfpasswordedt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                            cnfpasswordVisbile= true;
                        }
                        cnfpasswordedt.setSelection(selection);
                        return true;
                    }
                }
                return false;
            }
        });
    }
}