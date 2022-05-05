package com.ericknathan.libri.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.ericknathan.libri.BookFeedActivity;
import com.ericknathan.libri.R;
import com.ericknathan.libri.UpdateBookActivity;
import com.ericknathan.libri.models.Book;
import com.ericknathan.libri.models.Item;
import com.ericknathan.libri.remote.APIUtil;
import com.ericknathan.libri.remote.RouterInterface;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {
    private final List<Item> books;
    private final Context context;

    public BookAdapter(List<Item> dataSet, Context ctx) {
        books = dataSet;
        context = ctx;
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        RouterInterface routerInterface;
        private int bookId;
        private final TextView textBookTitle;
        private final ImageView imageViewBookCape;
        private final Button updateBookButton;
        private final Button deleteBookButton;

        public BookViewHolder(@NonNull View itemView, Context context) {
            super(itemView);

            textBookTitle = itemView.findViewById(R.id.card_book_title);
            imageViewBookCape = itemView.findViewById(R.id.card_book_image);

            updateBookButton = itemView.findViewById(R.id.button_book_update);
            deleteBookButton = itemView.findViewById(R.id.button_book_delete);

            updateBookButton.setOnClickListener(view -> {
                Intent updateBookActivityIntent = new Intent(context, UpdateBookActivity.class);
                updateBookActivityIntent.putExtra("book_id", bookId);
                context.startActivity(updateBookActivityIntent);
            });

            deleteBookButton.setOnClickListener(view -> {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(context)
                        .setMessage("Escolha a ação que deseja executar")
                        .setPositiveButton("Excluir", (dialog, witch) -> {
                            routerInterface = APIUtil.getUserInterface();
                            Call<Book> call = routerInterface.deleteBook(bookId);
                            call.enqueue(new Callback<Book>() {
                                @Override
                                public void onResponse(Call<Book> call, Response<Book> response) {
                                    Toast.makeText(
                                            context,
                                            "Livro excluido com sucesso!",
                                            Toast.LENGTH_LONG
                                    ).show();

                                    context.startActivity(new Intent(context, BookFeedActivity.class));
                                }

                                @Override
                                public void onFailure(Call<Book> call, Throwable t) {
                                    throw new RuntimeException(t);
                                }
                            });
                        })
                        .setNegativeButton("Cancelar", (dialog, witch) -> {});

                alertDialog.show();
            });
        }

        public void setBookData(Book book) {
            bookId = book.getBookId();
            textBookTitle.setText(book.getTitle());
            Picasso.get().load(book.getImage()).into(imageViewBookCape);
        }
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_book, parent, false);
            return new BookViewHolder(view, context);
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
