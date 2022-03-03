package com.ericknathan.libri;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ericknathan.libri.adapters.BookAdapter;
import com.ericknathan.libri.database.SQLProvider;
import com.ericknathan.libri.helpers.MenuHelper;
import com.ericknathan.libri.models.Item;

import java.util.List;

public class BookFeedActivity extends AppCompatActivity {
    final MenuHelper menuHelper = new MenuHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_feed);

        List<Item> books = SQLProvider.getInstance(this).listBooks();

        RecyclerView recyclerView = findViewById(R.id.recyclerview_book_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        BookAdapter adapter = new BookAdapter(books);
        recyclerView.setAdapter(adapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menuHelper.createOptionsMenu(menu));
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(menuHelper.handleMenuOptions(item));
    }

    @Override
    public void onBackPressed() {}
}