package com.example.b07project;

import org.junit.Test;


import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;

import com.example.b07project.Admin_Login_MVP.AdminLoginModel;
import com.example.b07project.Admin_Login_MVP.AdminLoginPresenter;
import com.google.firebase.database.FirebaseDatabase;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.anyString;
@RunWith(MockitoJUnitRunner.class)
public class AdminLoginPresenterTest {

    @Mock
    private AdminLoginModel.AdminLoginViewInterface mockView;
    @Mock
    private FirebaseDatabase mockFirebaseDatabase;
    @Mock
    private AdminLoginModel mockModel;

    private AdminLoginPresenter presenter;

    @Before
    public void setUp() {
        //MockitoAnnotations.initMocks(this);
        mockModel = Mockito.mock(AdminLoginModel.class);
        presenter = new AdminLoginPresenter(mockView, mockModel);
    }
    @Test
    public void attemptLogin_withValidCredentials_shouldLoginSuccessfully() {
        // Mock the behavior of authenticateAdmin
        doAnswer(invocation -> {
            ((AdminLoginModel.OnAdminAuthenticationListener)invocation.getArgument(2)).onSuccess();
            return null;
        }).when(mockModel).authenticateAdmin(anyString(), anyString(), any());

        presenter.attemptLogin("validUsername", "validPassword");
        verify(mockView).onLoginSuccess();
    }

    @Test
    public void attemptLogin_withInvalidCredentials_shouldShowLoginFailure() {
        doAnswer(invocation -> {
            AdminLoginModel.OnAdminAuthenticationListener callback = invocation.getArgument(2);
            callback.onFailure();
            return null;
        }).when(mockModel).authenticateAdmin(anyString(), anyString(), any(AdminLoginModel.OnAdminAuthenticationListener.class));

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
                AdminLoginModel.OnAdminAuthenticationListener listener = (AdminLoginModel.OnAdminAuthenticationListener) invocation.getArguments()[2];
                listener.onSuccess();
                return null;
            }
        }).when(mockModel).authenticateAdmin(anyString(), anyString(), any(AdminLoginModel.OnAdminAuthenticationListener.class));

        presenter.attemptLogin("admin", "password");
        verify(mockView).onLoginSuccess();
    }
}
