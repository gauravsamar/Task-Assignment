package com.example.taskassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class NewIntern extends AppCompatActivity {
    EditText email,password,fullName;
    FirebaseFirestore fstore = FirebaseFirestore.getInstance();
    FirebaseAuth fAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_intern);
        createProfile();
    }

    private void createProfile()
    {
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        fullName = findViewById(R.id.fullName);
        String mail = email.getText().toString();
        String pass = password.getText().toString();
        String name = fullName.getText().toString();

        fAuth.createUserWithEmailAndPassword(mail,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult)
            {
                FirebaseUser user = fAuth.getCurrentUser();

                Toast.makeText(NewIntern.this, "Account Created", Toast.LENGTH_SHORT).show();
                DocumentReference df = fstore.collection("Interns").document(user.getUid());
                HashMap<String,Object> userInfo = new HashMap<>();
                userInfo.put("FullName",name);
                userInfo.put("Email",mail);
                userInfo.put("Password",pass);
                df.set(userInfo);
                startActivity(new Intent(getApplicationContext(),Profile.class));
            }

        });
    }

}