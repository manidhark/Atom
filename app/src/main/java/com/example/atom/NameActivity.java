package com.example.atom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class NameActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText nameEdt;
    Button continueBtn;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        progressDialog=new ProgressDialog(this);

        nameEdt=findViewById(R.id.nameEdt);
        continueBtn=findViewById(R.id.continueBtn);

        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Registering...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                String name=nameEdt.getText().toString();

                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build();

                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    progressDialog.dismiss();
                                    Intent intent=new Intent(NameActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    progressDialog.dismiss();
                                    Toast.makeText(NameActivity.this, "Error occured", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}