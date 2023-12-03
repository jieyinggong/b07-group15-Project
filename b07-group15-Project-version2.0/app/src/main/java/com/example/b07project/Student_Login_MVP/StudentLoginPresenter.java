package com.example.b07project.Student_Login_MVP;

public class StudentLoginPresenter {
    private StudentLoginModel.StudentLoginViewInterface view;
    private StudentLoginModel model;

    public StudentLoginPresenter(StudentLoginModel.StudentLoginViewInterface view, StudentLoginModel model) {
        this.view = view;
        this.model = model;
    }

    public void attemptLogin(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            view.onLoginFailure();
            return;
        }

        model.authenticateUser(username, password, new StudentLoginModel.OnUserAuthenticationListener() {
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
