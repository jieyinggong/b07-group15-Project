package com.example.b07project.Admin_Register_MVP;

public class AdminRegisterPresenter {
    private AdminRegisterView view;
    private AdminRegisterModel model;

    public AdminRegisterPresenter(AdminRegisterView view) {
        this.view = view;
        this.model = new AdminRegisterModel();
    }

    public void registerAdmin(String username, String fullName, String password) {
        if (username.isEmpty() || fullName.isEmpty() || password.isEmpty()) {
            view.showRegistrationFailure();
            return;
        }

        model.checkUsernameUnique(username, new AdminRegisterModel.OnUsernameCheckListener() {
            @Override
            public void onUsernameAvailable() {
                model.registerNewAdmin(username, fullName, password, new AdminRegisterModel.OnRegistrationCompleteListener() {
                    @Override
                    public void onSuccess() {
                        view.showRegistrationSuccess();
                    }

                    @Override
                    public void onFailure() {
                        view.showRegistrationFailure();
                    }
                });
            }

            @Override
            public void onUsernameTaken() {
                view.showUsernameTaken();
            }

            @Override
            public void onFailure() {
                view.showRegistrationFailure();
            }
        });
    }
}

