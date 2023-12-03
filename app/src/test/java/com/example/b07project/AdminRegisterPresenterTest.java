package com.example.b07project;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertNotNull;
import com.example.b07project.Admin_Register_MVP.AdminRegisterModel;
import com.example.b07project.Admin_Register_MVP.AdminRegisterPresenter;
import com.google.firebase.database.FirebaseDatabase;

@RunWith(MockitoJUnitRunner.class)
public class AdminRegisterPresenterTest {

    @Mock
    private AdminRegisterModel.AdminRegisterInterface mockView;
    @Mock
    private FirebaseDatabase mockFirebaseDatabase;
    private AdminRegisterModel mockModel;

    private AdminRegisterPresenter presenter;

    @Before
    public void setUp() {
        //MockitoAnnotations.initMocks(this);
        //mockModel = new AdminRegisterModel(mockFirebaseDatabase); // Use mocked FirebaseDatabase
        mockModel = Mockito.mock(AdminRegisterModel.class);
        presenter = new AdminRegisterPresenter(mockView, mockModel);
    }
    @Test
    public void registerAdmin_withUniqueUsernameAndSuccessfulRegistration_shouldShowRegistrationSuccess() {
        assertNotNull("Mock model is null", mockModel); // Check that mockModel is not null

        doAnswer(invocation -> {
            AdminRegisterModel.OnUsernameCheckListener callback = invocation.getArgument(1);
            callback.onUsernameAvailable();
            return null;
        }).when(mockModel).checkUsernameUnique(anyString(), any(AdminRegisterModel.OnUsernameCheckListener.class));

        doAnswer(invocation -> {
            AdminRegisterModel.OnRegistrationCompleteListener callback = invocation.getArgument(3);
            callback.onSuccess();
            return null;
        }).when(mockModel).registerNewAdmin(anyString(), anyString(), anyString(), any(AdminRegisterModel.OnRegistrationCompleteListener.class));

        presenter.registerAdmin("username", "fullName", "password");
        verify(mockView).showRegistrationSuccess();
    }

    @Test
    public void registerAdmin_withEmptyUsername_shouldShowRegistrationFailure() {
        presenter.registerAdmin("", "fullName", "password");
        verify(mockView).showRegistrationFailure();
    }

    @Test
    public void registerAdmin_withEmptyFullName_shouldShowRegistrationFailure() {
        presenter.registerAdmin("username", "", "password");
        verify(mockView).showRegistrationFailure();
    }

    @Test
    public void registerAdmin_withEmptyPassword_shouldShowRegistrationFailure() {
        presenter.registerAdmin("username", "fullName", "");
        verify(mockView).showRegistrationFailure();
    }



    @Test
    public void registerAdmin_withNonUniqueUsername_shouldShowUsernameTaken() {
        doAnswer(invocation -> {
            AdminRegisterModel.OnUsernameCheckListener callback = invocation.getArgument(1);
            callback.onUsernameTaken();
            return null;
        }).when(mockModel).checkUsernameUnique(anyString(), any(AdminRegisterModel.OnUsernameCheckListener.class));

        presenter.registerAdmin("username", "fullName", "password");
        verify(mockView).showUsernameTaken();
    }

    @Test
    public void registerAdmin_withUniqueUsernameAndFailedRegistration_shouldShowRegistrationFailure() {
        doAnswer(invocation -> {
            AdminRegisterModel.OnUsernameCheckListener callback = invocation.getArgument(1);
            callback.onUsernameAvailable();
            return null;
        }).when(mockModel).checkUsernameUnique(anyString(), any(AdminRegisterModel.OnUsernameCheckListener.class));

        doAnswer(invocation -> {
            AdminRegisterModel.OnRegistrationCompleteListener callback = invocation.getArgument(3);
            callback.onFailure();
            return null;
        }).when(mockModel).registerNewAdmin(anyString(), anyString(), anyString(), any(AdminRegisterModel.OnRegistrationCompleteListener.class));

        presenter.registerAdmin("username", "fullName", "password");
        verify(mockView).showRegistrationFailure();
    }
}

