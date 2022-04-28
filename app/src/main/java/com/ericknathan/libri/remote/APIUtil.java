package com.ericknathan.libri.remote;

public class APIUtil {
    private static final String API_URL = "http://10.107.144.3:3333";

    public static RouterInterface getUserInterface() {
        return RetrofitClient.getClient(API_URL).create(RouterInterface.class);
    }
}
