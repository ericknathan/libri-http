package com.ericknathan.libri.remote;

import com.ericknathan.libri.models.Book;
import com.ericknathan.libri.models.Item;
import com.ericknathan.libri.models.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface RouterInterface {

    @POST("/user")
    Call<User> addUser(@Body User user);

    @POST("/book")
    Call<Book> addBook(@Body Book book);

    @GET("/book")
    Call<List<Book>> listBooks();

    @GET("/book/{book_id}")
    Call<Book> getBook(@Path("book_id") int bookId);

    @DELETE("/book/{book_id}")
    Call<Book> deleteBook(@Path("book_id") int bookId);

    @PUT("/book/{book_id}")
    Call<Book> updateBook(@Path("book_id") int bookId, @Body Book book);
}
