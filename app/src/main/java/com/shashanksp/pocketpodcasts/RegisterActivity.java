package com.shashanksp.pocketpodcasts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.shashanksp.pocketpodcasts.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {
    private boolean passwordVisbile;
    private boolean cnfpasswordVisbile;
    private FirebaseAuth mAuth;

    @Override
    protected void onStart() {
        super.onStart();
        // Check if the user is already logged in
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);
        if (isLoggedIn) {
            // User is logged in, start the HomeActivity and finish LoginActivity
            Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRegisterBinding binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EditText passwordedt = binding.passwordEdittext;
        EditText emailedt = binding.emailEdittext;
        EditText cnfpasswordedt = binding.confirmpasswordText;
        mAuth = FirebaseAuth.getInstance();
        //Registering user
        binding.registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Register User
                Register(emailedt.getText().toString(),passwordedt.getText().toString());
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
    private void  Register(String email,String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Register", "createUserWithEmail:success");
                            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putBoolean("isLoggedIn", true);
                            editor.apply();
                            Intent intent = new Intent(RegisterActivity.this,HomeActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Register", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}