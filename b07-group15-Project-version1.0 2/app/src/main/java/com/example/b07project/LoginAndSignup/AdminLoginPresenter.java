package com.example.b07project.LoginAndSignup;

public class AdminLoginPresenter {

    private AdminLoginModel.AdminLoginViewInterface view;
    private AdminLoginModel model;

    public AdminLoginPresenter(AdminLoginModel.AdminLoginViewInterface view) {
        this.view = view;
        this.model = new AdminLoginModel();
    }

    public void attemptLogin(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            view.onLoginFailure();
            return;
        }

        model.authenticateAdmin(username, password, new AdminLoginModel.OnAdminAuthenticationListener() {
            @Override
            public void onSuccess() {
                view.onLoginSuccess();
            }

            @Override
            public void onFailure() {
                view.onLoginFailure();
            }
        });
    }
}
