package com.example.b07project;

import org.junit.Test;


import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

import com.example.b07project.Student_Login_MVP.StudentLoginModel;
import com.example.b07project.Student_Login_MVP.StudentLoginPresenter;
import com.google.firebase.database.FirebaseDatabase;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.anyString;
@RunWith(MockitoJUnitRunner.class)
public class StudentLoginPresenterTest {

    @Mock
    private StudentLoginModel.StudentLoginViewInterface mockView;
    @Mock
    private FirebaseDatabase mockFirebaseDatabase;
    @Mock
    private StudentLoginModel mockModel;

    private StudentLoginPresenter presenter;

    @Before
    public void setUp() {
        //MockitoAnnotations.initMocks(this);
        mockModel = Mockito.mock(StudentLoginModel.class);
        presenter = new StudentLoginPresenter(mockView, mockModel);
    }
    @Test
    public void attemptLogin_withValidCredentials_shouldLoginSuccessfully() {
        // Mock the behavior of authenticateAdmin
        doAnswer(invocation -> {
            ((StudentLoginModel.OnUserAuthenticationListener)invocation.getArgument(2)).onSuccess();
            return null;
        }).when(mockModel).authenticateUser(anyString(), anyString(), any());

        presenter.attemptLogin("validUsername", "validPassword");
        verify(mockView).onLoginSuccess();
    }

    @Test
    public void attemptLogin_withInvalidCredentials_shouldShowLoginFailure() {
        doAnswer(invocation -> {
            StudentLoginModel.OnUserAuthenticationListener callback = invocation.getArgument(2);
            callback.onFailure();
            return null;
        }).when(mockModel).authenticateUser(anyString(), anyString(), any(StudentLoginModel.OnUserAuthenticationListener.class));

        presenter.attemptLogin("invalidUsername", "invalidPassword");
        verify(mockView).onLoginFailure();
    }
    @Test
    public void attemptLogin_withEmptyUsername_shouldCallOnLoginFailure() {
        presenter.attemptLogin("", "password");
        verify(mockView).onLoginFailure();
    }
    @Test
    public void attemptLogin_withEmptyPassword_shouldCallOnLoginFailure() {
        presenter.attemptLogin("username", "");
        verify(mockView).onLoginFailure();
    }

    @Test
    public void attemptLogin_withValidCredentials_shouldCallOnLoginSuccess() {
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) throws Throwable {
                StudentLoginModel.OnUserAuthenticationListener listener = (StudentLoginModel.OnUserAuthenticationListener) invocation.getArguments()[2];
                listener.onSuccess();
                return null;
            }
        }).when(mockModel).authenticateUser(anyString(), anyString(), any(StudentLoginModel.OnUserAuthenticationListener.class));

        presenter.attemptLogin("admin", "password");
        verify(mockView).onLoginSuccess();
    }
}
