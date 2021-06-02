package com.example.restaurantmenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnSignup, btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnSignup = findViewById(R.id.signUp);
        btnSignIn = findViewById(R.id.signIn);
        btnSignup.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch ((view.getId())){
            case R.id.signUp:
                Intent intent = new Intent(this, SignUp.class);
                startActivity(intent);
                break;
//            case R.id.signIn:
//                Intent intent1 = new Intent (this, SignIn.class);
//                startActivity(intent1);
//                break;
        }
    }
}
