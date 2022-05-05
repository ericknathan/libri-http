package com.ericknathan.libri;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ericknathan.libri.helpers.MenuHelper;
import com.ericknathan.libri.models.Book;
import com.ericknathan.libri.models.User;
import com.ericknathan.libri.remote.APIUtil;
import com.ericknathan.libri.remote.RouterInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateBookActivity extends AppCompatActivity {
    final MenuHelper menuHelper = new MenuHelper(this);

    private EditText titleInput;
    private EditText authorInput;
    private EditText descriptionInput;
    private EditText photoInput;

    RouterInterface routerInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);

        titleInput = findViewById(R.id.input_book_title);
        authorInput = findViewById(R.id.input_book_author);
        descriptionInput = findViewById(R.id.input_book_description);
        photoInput = findViewById(R.id.input_book_photo);
        Button updateButton = findViewById(R.id.button_book_update);
        int book_id = getIntent().getExtras().getInt("book_id");

        routerInterface = APIUtil.getUserInterface();
        getBookData(book_id);

        updateButton.setOnClickListener(view -> {

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
                        .setTitle(getString(R.string.br_book_update_title))
                        .setMessage(getString(R.string.br_book_update_message))
                        .setPositiveButton(
                                getString(R.string.br_confirm_positive),
                                (dialog, which) -> {
                                    Book book = new Book();
                                    book.setTitle(titleInputValue);
                                    book.setDescription(descriptionInputValue);
                                    book.setImage(photoInputValue);
                                    book.setAuthor(authorInputValue);
                                    book.setUserId(1);
                                    book.setBookId(book_id);

                                    updateBook(book);

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

    public void getBookData(int bookId) {
        Log.d("xpto", String.valueOf(bookId));
        Call<Book> call = routerInterface.getBook(bookId);
        call.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                if(response.isSuccessful()) {
                    titleInput.setText(response.body().getTitle());
                    authorInput.setText(response.body().getAuthor());
                    descriptionInput.setText(response.body().getDescription());
                    photoInput.setText(response.body().getImage());

                    Toast.makeText(UpdateBookActivity.this, "LIVRO ATUALIZADO COM SUCESSO!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Toast.makeText(UpdateBookActivity.this, "ERRO - " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void updateBook(Book book) {
        Call<Book> call = routerInterface.updateBook(book.getBookId(), book);
        call.enqueue(new Callback<Book>() {
            @Override
            public void onResponse(Call<Book> call, Response<Book> response) {
                Toast.makeText(UpdateBookActivity.this, "LIVRO ATUALIZADO COM SUCESSO!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(UpdateBookActivity.this, BookFeedActivity.class));
            }

            @Override
            public void onFailure(Call<Book> call, Throwable t) {
                Toast.makeText(UpdateBookActivity.this, "ERRO - " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}