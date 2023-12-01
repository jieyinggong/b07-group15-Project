package com.example.b07project.Student_Login_MVP;

public class StudentLoginPresenter {
    private StudentLoginView view;
    private StudentLoginModel model;

    public StudentLoginPresenter(StudentLoginView view) {
        this.view = view;
        this.model = new StudentLoginModel();
    }

    public void attemptLogin(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            view.onLoginFailure();
            return;
        }

        model.authenticateUser(username, password, new StudentLoginModel.OnUserAuthenticationListener() {
            @Override
            public void onSuccess(String username) {
                view.onLoginSuccess(username);
            }

            @Override
            public void onFailure() {
                view.onLoginFailure();
            }
        });
    }
}
