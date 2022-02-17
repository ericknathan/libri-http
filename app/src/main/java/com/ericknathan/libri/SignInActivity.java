package com.ericknathan.libri;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ericknathan.libri.database.SQLProvider;
import com.ericknathan.libri.helpers.LoginHelper;

public class SignInActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private Button signInUserButton;
    private Button signUpUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        usernameInput = findViewById(R.id.input_user_username);
        passwordInput = findViewById(R.id.input_user_password);
        signUpUserButton = findViewById(R.id.button_user_signup);
        signInUserButton = findViewById(R.id.button_user_signin);

        usernameInput.setText("ericknathan");
        passwordInput.setText("12345");

        signInUserButton.setOnClickListener(view -> {
            // TODO: Check if username and password is corrects
            String username = usernameInput.getText().toString();
            String password = passwordInput.getText().toString();
            int userId = SQLProvider.getInstance(this).login(username, password);

            if(userId > 0) {
                LoginHelper.setUserId(userId);
                startActivity(new Intent(
                        SignInActivity.this,
                        BookFeedActivity.class
                ));
            } else {
                Toast.makeText(this, "Usuário ou senha incorretos!", Toast.LENGTH_LONG).show();
            }
        });

        signUpUserButton.setOnClickListener(view -> {
            startActivity(new Intent(
                    SignInActivity.this,
                    SignUpActivity.class
            ));
        });


    }
}