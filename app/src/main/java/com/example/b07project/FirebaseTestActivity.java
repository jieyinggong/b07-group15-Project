package com.example.b07project;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import android.widget.Button;
import android.widget.Toast;
import android.util.Log;
import java.util.List;
import java.util.ArrayList;
import java.lang.Class;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.b07project.dbOperation.*;
 class User{
    public String id;
    public String name;
    public User(){}
     public User(String id, String name){
        this.id=id;
        this.name=name;
     }

     public String getName() {
        return name;
     }
     public String getId(){return id;}
 }
public class FirebaseTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_test);

        Button testButton = findViewById(R.id.testButton);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testFirebaseOperations();
            }
        });
    }

    private void testFirebaseOperations() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();

       // testCreateItem(database);
        User newUser = new User("123", "Test User");
        User newUser3 = new User("234", "Test User3");
        User newUser2 = new User("134", "Test User2");

       // testEditItem(database);
        //testReadItem(database);
        testChangesFetcher(database);
    }

//    private void testCreateItem(FirebaseDatabase database) {
//        CreateItem<User> createItem = new CreateItem<>(database);
//
//        User newUser = new User("123", "Test User");
//
//        createItem.create("User", newUser, new DefaultCallback() {
//            @Override
//            public void onSuccess() {
//                showToast("CreateItem: Success");
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//                showToast("CreateItem: Failed - " + e.getMessage());
//            }
//        });
//
//
//        User newUser3 = new User("234", "Test User3");
//        User newUser2 = new User("134", "Test User2");
//    }


//    private void testEditItem(FirebaseDatabase database) {
//        EditItem<User> editItem = new EditItem<>(new DefaultCallback() {
//            @Override
//            public void onSuccess() {
//                showToast("EditItem: Success");
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//                showToast("EditItem: Failed - " + e.getMessage());
//            }
//        });
//
//        // 测试更新操作
//        String userIdToUpdate = "-NjGA2WDbCEW36gUerYa"; // 假设的用户ID
//        User updatedUser = new User(userIdToUpdate, "Updated Name");
//        editItem.update(userIdToUpdate, updatedUser);
//
//        // 测试删除操作
//        String userIdToDelete = "-NjGAg5xb8AQTTlckp7i"; // 另一个假设的用户ID
//         //通常删除操作不需要传递完整的对象，只需ID即可
//        editItem.delete(userIdToDelete, updatedUser); // 传递null或者相应的对象
//    }

//    private void testReadItem(FirebaseDatabase database) {
//        ReadItem<User> readItem = new ReadItem<>();
//
//        // 假设您要读取ID为"123"的用户
//        String userId = "-NjGA2WDbCEW36gUerYa";
//
//        readItem.read(userId, User.class, new ResultCallback<User>() {
//            @Override
//            public void onSuccess(User result) {
//                // 处理读取成功的情况
//                showToast("ReadItem: Success - User: " + result.getName());
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//                // 处理读取失败的情况
//                showToast("ReadItem: Failed - " + e.getMessage());
//            }
//        });
//
//        // 如果您还想列出所有用户，可以使用listAll方法
//        readItem.listAll(User.class, new ResultCallback<List<User>>() {
//            @Override
//            public void onSuccess(List<User> result) {
//                // 处理读取成功的情况
//                showToast("ListAll: Success - Total Users: " + result.size());
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//                // 处理读取失败的情况
//                showToast("ListAll: Failed - " + e.getMessage());
//            }
//        });
//    }

    private void testChangesFetcher(FirebaseDatabase database) {
        ChangesFetcher<User> changesFetcher = new ChangesFetcher<>();

        changesFetcher.FetchChanges(User.class, new ResultCallback<User>() {
            @Override
            public void onSuccess(User result) {
                // 当检测到变化时，此处代码将被执行
                // 比如，当有新用户被添加或现有用户被更新时
                showToast("ChangesFetcher: Detected a change in User: " + result.getId());
            }

            @Override
            public void onFailure(Exception e) {
                // 如果监听过程中发生错误，此处代码将被执行
                showToast("ChangesFetcher: Failed to fetch changes - " + e.getMessage());
            }
        });

        EditItem<User> editItem = new EditItem<>(new DefaultCallback() {
                        @Override
            public void onSuccess() {
                showToast("EditItem: Success");
            }

            @Override
            public void onFailure(Exception e) {
                showToast("EditItem: Failed - " + e.getMessage());
            }
        });
        String userIdToUpdate = "-NjHD35aNFtSwVSIbpqP"; // 假设的用户ID
        User updatedUser = new User(userIdToUpdate, "650594");
        editItem.update(userIdToUpdate, updatedUser);

//        CreateItem<User> createItem = new CreateItem<>(database);
//
//        User newUser = new User("123", "2");
//
//        createItem.create("User", newUser, new DefaultCallback() {
//            @Override
//            public void onSuccess() {
//                showToast("CreateItem: Success");
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//                showToast("CreateItem: Failed - " + e.getMessage());
//            }
//    });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
