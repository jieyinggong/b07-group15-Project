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
import com.example.b07project.Student_Register_MVP.StudentRegisterModel;
import com.example.b07project.Student_Register_MVP.StudentRegisterPresenter;
import com.google.firebase.database.FirebaseDatabase;

@RunWith(MockitoJUnitRunner.class)
public class StudentRegisterPresenterTest {

    @Mock
    private StudentRegisterModel.StudentRegisterViewInterface mockView;
    @Mock
    private FirebaseDatabase mockFirebaseDatabase;
    private StudentRegisterModel mockModel;

    private StudentRegisterPresenter presenter;

    @Before
    public void setUp() {
        //MockitoAnnotations.initMocks(this);
        //mockModel = new AdminRegisterModel(mockFirebaseDatabase); // Use mocked FirebaseDatabase
        mockModel = Mockito.mock(StudentRegisterModel.class);
        presenter = new StudentRegisterPresenter(mockView, mockModel);
    }
    @Test
    public void registerStudent_withUniqueUsernameAndSuccessfulRegistration_shouldShowRegistrationSuccess() {
        assertNotNull("Mock model is null", mockModel); // Check that mockModel is not null

        doAnswer(invocation -> {
            StudentRegisterModel.OnUsernameCheckListener callback = invocation.getArgument(1);
            callback.onUsernameAvailable();
            return null;
        }).when(mockModel).checkUsernameUnique(anyString(), any(StudentRegisterModel.OnUsernameCheckListener.class));

        doAnswer(invocation -> {
            StudentRegisterModel.OnRegistrationCompleteListener callback = invocation.getArgument(3);
            callback.onSuccess();
            return null;
        }).when(mockModel).registerNewStudent(anyString(), anyString(), anyString(), any(StudentRegisterModel.OnRegistrationCompleteListener.class));

        presenter.registerStudent("username", "fullName", "password");
        verify(mockView).showRegistrationSuccess();
    }

    @Test
    public void registerStudent_withEmptyUsername_shouldShowRegistrationFailure() {
        presenter.registerStudent("", "fullName", "password");
        verify(mockView).showRegistrationFailure();
    }

    @Test
    public void registerStudent_withEmptyFullName_shouldShowRegistrationFailure() {
        presenter.registerStudent("username", "", "password");
        verify(mockView).showRegistrationFailure();
    }

    @Test
    public void registerStudent_withEmptyPassword_shouldShowRegistrationFailure() {
        presenter.registerStudent("username", "fullName", "");
        verify(mockView).showRegistrationFailure();
    }



    @Test
    public void registerStudent_withNonUniqueUsername_shouldShowUsernameTaken() {
        doAnswer(invocation -> {
            StudentRegisterModel.OnUsernameCheckListener callback = invocation.getArgument(1);
            callback.onUsernameTaken();
            return null;
        }).when(mockModel).checkUsernameUnique(anyString(), any(StudentRegisterModel.OnUsernameCheckListener.class));

        presenter.registerStudent("username", "fullName", "password");
        verify(mockView).showUsernameTaken();
    }

    @Test
    public void registerStudent_withUniqueUsernameAndFailedRegistration_shouldShowRegistrationFailure() {
        doAnswer(invocation -> {
            StudentRegisterModel.OnUsernameCheckListener callback = invocation.getArgument(1);
            callback.onUsernameAvailable();
            return null;
        }).when(mockModel).checkUsernameUnique(anyString(), any(StudentRegisterModel.OnUsernameCheckListener.class));

        doAnswer(invocation -> {
            StudentRegisterModel.OnRegistrationCompleteListener callback = invocation.getArgument(3);
            callback.onFailure();
            return null;
        }).when(mockModel).registerNewStudent(anyString(), anyString(), anyString(), any(StudentRegisterModel.OnRegistrationCompleteListener.class));

        presenter.registerStudent("username", "fullName", "password");
        verify(mockView).showRegistrationFailure();
    }
}

