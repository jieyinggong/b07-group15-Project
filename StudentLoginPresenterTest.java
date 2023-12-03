package com.example.b07project;

import static org.mockito.Mockito.*;

import com.example.b07project.Student_Login_MVP.StudentLoginModel;
import com.example.b07project.Student_Login_MVP.StudentLoginPresenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StudentLoginPresenterTest {

    @Mock
    private StudentLoginModel.StudentLoginViewInterface mockView;
    @Mock
    private StudentLoginModel mockModel;

    StudentLoginPresenter presenter;

    @Before
    public void setup() {
        presenter = new StudentLoginPresenter(mockView, mockModel);
    }
    @Test
    public void testAttemptLoginWithEmptyUsernameAndEmptyPassword_should_be_failed() {
        String username = "";
        String password = "";
        presenter.attemptLogin(username, password);
        verify(mockView).onLoginFailure();
    }

    @Test
    public void testAttemptLoginWithValidUsernameAndEmptyPassword_should_be_failed() {
        String username = "UserName";
        String password = "";
        presenter.attemptLogin(username, password);
        verify(mockView).onLoginFailure();
    }

    @Test
    public void testAttemptLoginWithEmptyUsernameAndValidPassword_should_be_failed() {
        String username = "";
        String password = "Password";
        presenter.attemptLogin(username, password);
        verify(mockView).onLoginFailure();
    }

    @Test
    public void testAttemptLoginWithValidUsernameAndValidPassword() {
        String username = "UserName";
        String password = "Password";

        presenter.attemptLogin(username, password);

        verify(mockModel).authenticateUser(eq(username), eq(password), any());
    }

}