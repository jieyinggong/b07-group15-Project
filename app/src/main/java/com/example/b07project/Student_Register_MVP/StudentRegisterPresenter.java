package com.example.b07project.Student_Register_MVP;

public class StudentRegisterPresenter {
    private StudentRegisterView view;
    private StudentRegisterModel model;

    public StudentRegisterPresenter(StudentRegisterView view) {
        this.view = view;
        this.model = new StudentRegisterModel();
    }

    public void registerStudent(String username, String fullName, String password) {
        if (username.isEmpty() || fullName.isEmpty() || password.isEmpty()) {
            view.showRegistrationFailure();
            return;
        }

        model.checkUsernameUnique(username, new StudentRegisterModel.OnUsernameCheckListener() {
            @Override
            public void onUsernameAvailable() {
                model.registerNewStudent(username, fullName, password, new StudentRegisterModel.OnRegistrationCompleteListener() {
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
