package com.ericknathan.libri;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ericknathan.libri.models.User;
import com.ericknathan.libri.remote.APIUtil;
import com.ericknathan.libri.remote.RouterInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    private EditText nameInput;
    private EditText surnameInput;
    private EditText emailInput;
    private EditText usernameInput;
    private EditText passwordInput;
    private EditText passwordConfirmInput;

    RouterInterface routerInterface;

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
        Button signUpUserButton = findViewById(R.id.button_user_signup);

        nameInput.setText("Erick");
        surnameInput.setText("Nathan");
        usernameInput.setText("ericknathan");
        emailInput.setText("erick.capito@hotmail.com");
        passwordInput.setText("12345");
        passwordConfirmInput.setText("12345");

        signUpUserButton.setOnClickListener(view -> {

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
                            User user = new User();
                            user.setEmail(emailInputValue);
                            user.setName(nameInputValue);
                            user.setLogin(usernameInputValue);
                            user.setSurname(surnameInputValue);
                            user.setPassword(passwordInputValue);

                            routerInterface = APIUtil.getUserInterface();
                            createUser(user);

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

    public void createUser(User user) {
        Call<User> call = routerInterface.addUser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(SignUpActivity.this, "USUARIO INSERIDO COM SUCESSO!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(SignUpActivity.this, "ERRO - " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}