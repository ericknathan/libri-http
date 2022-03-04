package com.ericknathan.libri.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ericknathan.libri.R;
import com.ericknathan.libri.models.Book;
import com.ericknathan.libri.models.Item;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private final List<Item> books;

    public BookAdapter(List<Item> dataSet) {
        books = dataSet;
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        private int bookId;
        private final TextView textBookTitle, textBookDescription;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            textBookTitle = itemView.findViewById(R.id.card_book_title);
            textBookDescription = itemView.findViewById(R.id.card_book_description);
        }

        public void setBookData(Book book) {
            textBookTitle.setText(book.getTitle());
            textBookDescription.setText(book.getDescription());
        }
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_book, parent, false);
            return new BookViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Item book = books.get(position);
        if (book.getType() == 0) {
            holder.setBookData((Book) book.getObject());
        }
    }

    @Override
    public int getItemCount() {
        return books.size();
    }
}
