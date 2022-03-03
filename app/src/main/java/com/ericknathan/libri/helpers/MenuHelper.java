package com.ericknathan.libri.helpers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import com.ericknathan.libri.BookFeedActivity;
import com.ericknathan.libri.BookRegisterActivity;
import com.ericknathan.libri.R;
import com.ericknathan.libri.SignInActivity;

public class MenuHelper {
    private final Activity activity;

    public MenuHelper(Activity activity) {
        this.activity = activity;
    }

    public Menu createOptionsMenu(Menu menu) {
        this.activity.getMenuInflater().inflate(R.menu.menu, menu);
        return menu;
    }

    @SuppressLint("NonConstantResourceId")
    public MenuItem handleMenuOptions(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_books_feed:
                this.activity.startActivity(new Intent(this.activity, BookFeedActivity.class));
                break;
            case R.id.menu_register_book:
                this.activity.startActivity(new Intent(this.activity, BookRegisterActivity.class));
                break;
            case R.id.menu_logout:
                LoginHelper.setUserId();
                this.activity.startActivity(new Intent(this.activity, SignInActivity.class));
                break;
        }
        return item;
    }
}
