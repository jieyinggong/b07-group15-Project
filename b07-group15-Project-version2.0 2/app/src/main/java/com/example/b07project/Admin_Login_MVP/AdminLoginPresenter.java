package com.example.b07project.Admin_Login_MVP;

public class AdminLoginPresenter {

    private AdminLoginModel.AdminLoginViewInterface view;
    public AdminLoginModel model;

    public AdminLoginPresenter(AdminLoginModel.AdminLoginViewInterface view, AdminLoginModel model) {
        this.view = view;
        this.model = model;
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
