package com.ericknathan.libri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ericknathan.libri.adapters.BookAdapter;
import com.ericknathan.libri.helpers.MenuHelper;
import com.ericknathan.libri.models.Book;
import com.ericknathan.libri.models.Item;
import com.ericknathan.libri.remote.APIUtil;
import com.ericknathan.libri.remote.RouterInterface;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookFeedActivity extends AppCompatActivity {
    final MenuHelper menuHelper = new MenuHelper(this);

    RouterInterface routerInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_feed);

        routerInterface = APIUtil.getUserInterface();
        listBooks();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menuHelper.createOptionsMenu(menu));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(menuHelper.handleMenuOptions(item));
    }

    @Override
    public void onBackPressed() {
    }

    public void listBooks() {
        Call<List<Book>> callback = routerInterface.listBooks();
        List<Item> items = new ArrayList<>();

        callback.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                List<Book> books = response.body();

                for(Book book : books) {
                    Item item = new Item(0, book);
                    items.add(item);
                }

                BookAdapter adapter = new BookAdapter(items, BookFeedActivity.this);
                RecyclerView recyclerView = findViewById(R.id.recyclerview_book_list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Log.e("[LIST BOOKS ERROR]", t.getMessage());
            }
        });
     }
}