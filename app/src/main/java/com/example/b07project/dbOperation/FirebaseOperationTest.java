package com.example.b07project.dbOperation;


import com.google.firebase.database.FirebaseDatabase;


public class FirebaseOperationTest {
    public static class User {
        public String id;
        public String name;

        public User(){
            id = "123";
            name = "abc";
        }

        public User(String id, String name){
            this.id=id;
            this.name =name;
        }
        // 其他字段和构造函数
    }

    public static void main(String[] args) {

        FirebaseDatabase database = FirebaseDatabase.getInstance("https://test-a5df6-default-rtdb.firebaseio.com/");

        // 创建和测试 CreateItem
        testCreateItem(database);

        // 创建和测试 EditItem
        testEditItem(database);

        // 创建和测试 ReadItem
        testReadItem(database);

        // 创建和测试 ChangesFetcher
        testChangesFetcher(database);
    }

    private static void testCreateItem(FirebaseDatabase database) {
        CreateItem<User> createItem = new CreateItem<>(database);
        User user = new User("1", "Alice");
        createItem.create("users", user, new PrintCallback());
    }

    private static void testEditItem(FirebaseDatabase database) {
        EditItem<User> editItem = new EditItem<>(new PrintCallback());
        User user = new User("1", "Alice Updated");
        editItem.update("1", user);
        editItem.delete("1", user);
    }

    private static void testReadItem(FirebaseDatabase database) {
        ReadItem<User> readItem = new ReadItem<>();
        readItem.read("1", User.class, new ResultCallback<User>() {
            @Override
            public void onSuccess(User result) {
                System.out.println("Read success: " + result.name);
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println("Read failure: " + e.getMessage());
            }
        });
    }

    private static void testChangesFetcher(FirebaseDatabase database) {
        ChangesFetcher<User> changesFetcher = new ChangesFetcher<>();
        changesFetcher.FetchChanges(User.class, new ResultCallback<User>() {
            @Override
            public void onSuccess(User result) {
                System.out.println("Change detected: " + result.name);
            }

            @Override
            public void onFailure(Exception e) {
                System.out.println("Change fetch failed: " + e.getMessage());
            }
        });
    }
}

// 示例回调实现

