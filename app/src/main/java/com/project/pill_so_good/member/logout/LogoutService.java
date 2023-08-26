package com.project.pill_so_good.member.logout;

import com.google.firebase.auth.FirebaseAuth;

public class LogoutService {
    private final FirebaseAuth firebaseAuth;


    public LogoutService() {
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    public void logout() {
        firebaseAuth.signOut();
    }
}
