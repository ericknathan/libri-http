package com.ericknathan.libri;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ericknathan.libri.database.SQLProvider;

import java.util.Date;

public class SignUpActivity extends AppCompatActivity {

    private EditText nameInput;
    private EditText surnameInput;
    private EditText emailInput;
    private EditText usernameInput;
    private EditText passwordInput;
    private EditText passwordConfirmInput;
    private Button signUpUserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        nameInput = findViewById(R.id.input_user_name);
        surnameInput = findViewById(R.id.input_user_surname);
        usernameInput = findViewById(R.id.input_user_username);
        emailInput = findViewById(R.id.input_user_mail);
        passwordInput = findViewById(R.id.input_user_password);
        passwordConfirmInput = findViewById(R.id.input_user_confirmpassword);
        signUpUserButton = findViewById(R.id.button_user_signup);

        signUpUserButton.setOnClickListener( view -> {

            String nameInputValue = nameInput.getText().toString();
            String surnameInputValue = surnameInput.getText().toString();
            String emailInputValue = emailInput.getText().toString();
            String usernameInputValue = usernameInput.getText().toString();
            String passwordInputValue = passwordInput.getText().toString();
            String confirmPasswordInputValue = passwordConfirmInput.getText().toString();

            boolean isValid = validate(
                    nameInputValue,
                    surnameInputValue,
                    emailInputValue,
                    usernameInputValue,
                    passwordInputValue,
                    confirmPasswordInputValue
            );

            if(isValid) {
                AlertDialog dialogBuilder = new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.br_user_signup_title))
                    .setMessage(getString(R.string.br_user_signup_message))
                    .setPositiveButton(
                        getString(R.string.br_confirm_positive),
                        (dialog, which) -> {
                            boolean registeredUser = SQLProvider.getInstance(this).addUser(
                                    nameInputValue,
                                    surnameInputValue,
                                    emailInputValue,
                                    usernameInputValue,
                                    passwordInputValue
                            );

                            if(registeredUser) {
                                Toast.makeText(this, getString(R.string.br_user_signup_message_success), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(this, getString(R.string.br_user_signup_message_error), Toast.LENGTH_LONG).show();
                            }

                            dialog.cancel();
                        }
                    )
                    .setNegativeButton(
                        getString(R.string.br_confirm_negative),
                        (dialog, which) -> {
                            dialog.cancel();
                        }
                    ).create();

                dialogBuilder.show();
            }
        });
    }

    private boolean validate(String name, String surname, String email, String username, String password, String confirmPassword) {
        if(name.isEmpty() || surname.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Todos os campos devem ser preenchidos.", Toast.LENGTH_LONG).show();
            return false;
        } else if(!password.equals(confirmPassword)) {
            Toast.makeText(this,"As senhas não coincidem.", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}