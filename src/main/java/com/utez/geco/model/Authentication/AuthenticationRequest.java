package com.utez.geco.model.Authentication;

import com.google.firebase.database.annotations.NotNull;

public class AuthenticationRequest {
    @NotNull
    private String login;

    @NotNull
    private String password;
}
