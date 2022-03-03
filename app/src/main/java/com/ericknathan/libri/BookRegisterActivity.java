package com.ericknathan.libri;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ericknathan.libri.database.SQLProvider;
import com.ericknathan.libri.helpers.MenuHelper;

public class BookRegisterActivity extends AppCompatActivity {
    final MenuHelper menuHelper = new MenuHelper(this);

    private EditText titleInput;
    private EditText authorInput;
    private EditText descriptionInput;
    private EditText photoInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_register);

        titleInput = findViewById(R.id.input_book_title);
        authorInput = findViewById(R.id.input_book_author);
        descriptionInput = findViewById(R.id.input_book_description);
        photoInput = findViewById(R.id.input_book_photo);
        Button registerButton = findViewById(R.id.button_book_register);

        titleInput.setText("Diário de um banana");
        authorInput.setText("Jeff Kiney");
        descriptionInput.setText("Isso é um exemplo de descrição");
        photoInput.setText("https://localhost:3333/foto.png");

        registerButton.setOnClickListener(view -> {

            String titleInputValue = titleInput.getText().toString();
            String authorInputValue = authorInput.getText().toString();
            String descriptionInputValue = descriptionInput.getText().toString();
            String photoInputValue = photoInput.getText().toString();

            boolean isValid = validate(
                    titleInputValue,
                    authorInputValue,
                    descriptionInputValue,
                    photoInputValue
            );

            if(isValid) {
                AlertDialog dialogBuilder = new AlertDialog.Builder(this)
                        .setTitle(getString(R.string.br_book_register_title))
                        .setMessage(getString(R.string.br_book_register_message))
                        .setPositiveButton(
                                getString(R.string.br_confirm_positive),
                                (dialog, which) -> {
                                    boolean registeredUser = SQLProvider.getInstance(this).registerBook(
                                            1,
                                            titleInputValue,
                                            authorInputValue,
                                            descriptionInputValue,
                                            photoInputValue
                                    );

                                    if(registeredUser) {
                                        Toast.makeText(this, getString(R.string.br_book_register_message_success), Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(this, getString(R.string.br_book_register_message_error), Toast.LENGTH_LONG).show();
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

    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menuHelper.createOptionsMenu(menu));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(menuHelper.handleMenuOptions(item));
    }

    private boolean validate(String title, String author, String description, String photo) {
        if(title.isEmpty() || author.isEmpty() || description.isEmpty() || photo.isEmpty()) {
            Toast.makeText(this, "Todos os campos devem ser preenchidos.", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}