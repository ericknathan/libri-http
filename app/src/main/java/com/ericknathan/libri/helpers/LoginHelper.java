package com.ericknathan.libri.helpers;

public class LoginHelper {
    private static int id_user;

    public static int getUserId() {
        return id_user;
    }

    public static void setUserId(int user_id) {
        LoginHelper.id_user = user_id;
    }
}
