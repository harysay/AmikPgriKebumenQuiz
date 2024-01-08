package com.harysaydev.amikpgrikbmquiz;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Timer;
import java.util.TimerTask;

public class ForgotPassActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private EditText forgotEmail;
    private Button resetPassButton;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        mToolbar = findViewById(R.id.fp_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Reset Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        auth = FirebaseAuth.getInstance();

        forgotEmail = findViewById(R.id.forgotEmail);
        resetPassButton = findViewById(R.id.resetPassButton);
        resetPassButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = forgotEmail.getText().toString();
                if(TextUtils.isEmpty(email)){
                    //Toasty.error(ForgotPassActivity.this, "Email is required", Toast.LENGTH_SHORT).show();
                    Toast.makeText(ForgotPassActivity.this, "Enter your email address", Toast.LENGTH_LONG).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(ForgotPassActivity.this, "Your email format is invalid", Toast.LENGTH_LONG).show();
                    //Toasty.error(ForgotPassActivity.this,"Email format is not valid.", Toast.LENGTH_SHORT).show();
                } else {
                    // send email to reset password
                    auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                emailSentSuccessPopUp();

                                // LAUNCH activity after certain time period
                                new Timer().schedule(new TimerTask(){
                                    public void run() {
                                        ForgotPassActivity.this.runOnUiThread(new Runnable() {
                                            public void run() {
                                                auth.signOut();

                                                Intent mainIntent =  new Intent(ForgotPassActivity.this, LoginActivity.class);
                                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(mainIntent);
                                                finish();
                                                Toast.makeText(ForgotPassActivity.this, "Please check your email", Toast.LENGTH_LONG).show();
                                                //Toasty.info(ForgotPassActivity.this, "Please check your email.", Toast.LENGTH_LONG).show();

                                            }
                                        });
                                    }
                                }, 8000);
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //Toasty.error(ForgotPassActivity.this, "Oops!! "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(ForgotPassActivity.this, "Oops!! "+e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });

    }

    private void emailSentSuccessPopUp() {
        // Custom Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(ForgotPassActivity.this);
        View view = LayoutInflater.from(ForgotPassActivity.this).inflate(R.layout.register_success_popup, null);
        TextView successMessage = view.findViewById(R.id.successMessage);
        successMessage.setText("the password reset link has been successfully sent.\nPlease check your email.");
        builder.setCancelable(true);
        builder.setView(view);
        builder.show();
    }

}
