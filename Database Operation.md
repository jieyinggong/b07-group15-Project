# Database Operation

# 统一的操作

`package com.example.b07project.dbOperation_Information;`

## Callback

在数据库操作成功或者失败后，自定义想要的操作比如Toast，Log，跳转啊都可以

onSuccess 就是数据库操作成功后，想要的实现的操作

onFailure 就是数据库失败后，想要实现的操作

DefaultCallback 和 ResultCallback 之间的不同就在onSuccess 的parameter 上

注意ResultCallback 用了generic T

有的dbOperation中的method 用的是ResultCallback会传回result 可以对result 进行想要实现的操作

```java
public interface DefaultCallback {
    void onSuccess();
    void onFailure(Exception e);
}

public interface ResultCallback<T> {
    void onSuccess(T result);
    void onFailure(Exception e);
}
```

### Instance

```java
DefaultCallback callback = new DefaultCallback(){
         @Override
             public void onSuccess() {
                   showToast("Submit Success!");
                }
          @Override
              public void onFailure(Exception e) {
                    showToast(e.getMessage());
                }
};
```

## Create New Item

```java
public interface CreateOperation
public class CreateItem implements CreateOperation
 
public void create(String path, Information item, DefaultCallback callback)
//path 是指数据库想要存到的路径 path下面
//比说是“Complaint”
```

- Code （点击展开）
    
    ```java
    public interface CreateOperation {
        void create(String path, Information item, DefaultCallback callback);
    }
    
    public class CreateItem implements CreateOperation{
    
        public CreateItem(){}
    
        @Override
        public void create(String path, Information item, DefaultCallback callback) {
            FirebaseDatabase db = FirebaseDatabase.getInstance();
            DatabaseReference ref = db.getReference();
            DatabaseReference newRef = ref.child(path).push();
            item.infoID = newRef.getKey();
            newRef.setValue(item)
                    .addOnSuccessListener(v -> {
                        if (callback != null) callback.onSuccess();
                    })
                    .addOnFailureListener(e -> {
                        if (callback != null) callback.onFailure(e);
                    });
       
    ```
    

### Instance

```java
CreateOperation newItem = new CreateItem();
Information complaint = new Complaint(title, content);
      newItem.create("Complaint", complaint, new DefaultCallback() {
                @Override
                public void onSuccess() {
                    showToast("Submit Success!");
                    finish();
                }
                @Override
                public void onFailure(Exception e) {
                    showToast(e.getMessage());
                }
            });
```

- 这里 callback 用的是匿名内部类的方式，在不同的情况下可以定义不同的想要的继续的操作尤其是成功后的操作，很方便

## Read item （针对 Complaint, Announcement ）

主要的话就是listAll，对于listview 来说会很方便

```java
public interface ReadOperation
public class ReadItem implements ReadOperation{

public void listAll(String path, ResultCallback<List<Information>> callback)
// path 指的是数据库的path

```

- Code
    
    ```java
    public interface ReadOperation {
            void read(String id,String path, ResultCallback<Information> callback);
            void listAll(String path, ResultCallback<List<Information>> callback);
    
    }
    
    public class ReadItem implements ReadOperation{
        DatabaseReference ref;
        public  ReadItem(){
            ref = FirebaseDatabase.getInstance().getReference();
        }
        public  ReadItem(DefaultCallback callback){
            ref = FirebaseDatabase.getInstance().getReference();
        }
    
        @Override
        public void listAll(String path, ResultCallback<List<Information>> callback) {
            DatabaseReference itemsRef = ref.child(path);
            itemsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        List<Information> resultList = new ArrayList<>();
                        for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                            Information item = (Information) itemSnapshot.getValue(Information.class);
                            resultList.add(item);
                        }
                        if (callback != null) {
                            callback.onSuccess(resultList);
                        }
                    } else {
                        if (callback != null) {
                            callback.onFailure(new Exception("No data found in " + path));
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    if (callback != null) {
                        callback.onFailure(databaseError.toException());
                    }
                }
            });
        }
    
    }
    
    }
    ```
    

### Instance

```java
String path = "Complaint";
List<Information> dataList = new ArrayList<>();
ReadOperation read = new ReadItem();
read.listAll(path, new ResultCallback<List<Information>>() {
            @Override
            public void onSuccess(List<Information> result) {
// 成功后把数据库这个path 下面已有的所有数据都加到datalist 这个List里面
                dataList.clear();
                if (result != null){
                    for (Information info : result) {
                        if (info != null && info.infoID != null) {
                            dataList.add(0,info);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Exception e) {
                Log.i("TAG","Error:"  + e.getMessage());
                }
        });
```

## Read (all) item（针对Event 和 Feedback）

```java
package com.example.b07project.dbOperation_Special;
public interface ReadSpecialOperation
public class ReadSpecialItem implements ReadSpecialOperation

public void listAllSpecial(String path, Class<?> claz, ResultCallback<List<Information>> callback) 
// 相比上面一个多了一个Class<？> claz 的parameter 这个是Java里面特殊的一个class 就代表Class
// 比如说 String.class 指的就是 String 这个类
//然后Information.class 指的就是Information 这个类
有了这一个Class 的参数我们才能把从数据库抓取的数据转化成我们想要的特定的类，比如说Event和Feedback
相比较基类Information来说，他们更多的field，为了不丢失这些数据，会用claz转换成我们想要的
Event 和 Feedback 的 object
（Event.class） 和 （Feedback. class ）
但是要特别注意的是，这里虽然可以转换成Event 但是出来的result的Declare type依旧是Information
(所以说ResultCallback的List<Information> 这个list 依旧是information的list)
但是runtime type 会是 claz对应的那个类型，实际使用result的时候要 用instanceof 检查
然后进行data casting
还有calz 对应的类一定要是Information的子类
```

- Code
    
    ```java
    public interface ReadSpecialOperation {
            void readSpecial(String id,Class<?> Claz, String path, ResultCallback<Information> callback);
            void listAllSpecial(String path ,Class<?> Claz , ResultCallback<List<Information>> callback);
    
    }
    
    public class ReadSpecialItem implements ReadSpecialOperation {
        DatabaseReference ref;
        public ReadSpecialItem(){
            ref = FirebaseDatabase.getInstance().getReference();
        }
        public ReadSpecialItem(DefaultCallback callback){
            ref = FirebaseDatabase.getInstance().getReference();
        }
    
    @Override
        public void listAllSpecial(String path, Class<?> claz, ResultCallback<List<Information>> callback) {
            DatabaseReference itemsRef = ref.child(path);
            itemsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        List<Information> resultList = new ArrayList<>();
                        for (DataSnapshot itemSnapshot : dataSnapshot.getChildren()) {
                            if (Information.class.isAssignableFrom(claz)) {
                                Information item = (Information) dataSnapshot.getValue(claz);
                                resultList.add(item);
                        }
                        if (callback != null) {
                            callback.onSuccess(resultList);
                        }}
                    } else {
                        if (callback != null) {
                            callback.onFailure(new Exception("No data found in " + path));
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    if (callback != null) {
                        callback.onFailure(databaseError.toException());
                    }
                }
            });
        }
    }
    ```
    

### Instance

```java
String path = "Complaint";
List<Information> dataList = new ArrayList<>();
ReadSpecialOperation read = new ReadSpecialItem();
read.listAllSpecial(path, Complaint.class, new ResultCallback<List<Information>>() {
            @Override
            public void onSuccess(List<Information> result) {
// 成功后把数据库这个path 下面已有的所有数据都加到datalist 这个List里面
                dataList.clear();
                if (result != null){
                    for (Information info : result) {
											if (info instanceof Complaint){
                        if (info != null && info.infoID != null) {
														Complaint complaint = (Complaint) info;
                            dataList.add(0,complaint);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Exception e) {
                Log.i("TAG","Error:"  + e.getMessage());
                }
        }); 
```

- 这一个还没有完全的测试过（（

## Changes Fetcher - Add new item 针对Complain，Notification）

监听数据库是否有新的数据增加

```java
public interface FetchfromChangesOperation 
public class ChangesFetcher implements FetchfromChangesOperation

 public void fetchNewitem(String path, ResultCallback<Information> callback) 
//这个方法基本上会放在activity的onResume中，一旦这个进入这个activity的时候就会放一个监听器
//然后在activity处于活动状态中的时候，会持续监听

public void removeListener()

//在activity的onPause 即退出这个活动的时候，监听器会被移除
```

- CODE
    
    ```java
    public interface FetchfromChangesOperation {
        void fetchNewitem(String path, ResultCallback<Information> callback);
        public void fetchUpdates(String path, ResultCallback<Information> callback);
        public void removeListener();
    }
    
    public class ChangesFetcher implements FetchfromChangesOperation {
        private ChildEventListener childEventListenerForNewItem;
        private ValueEventListener childEventListenerForUpdates;
        private DatabaseReference itemsRef;
    
        @Override
        public void fetchNewitem(String path, ResultCallback<Information> callback) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            DatabaseReference itemsRef = ref.child(path);
            childEventListenerForNewItem = new ChildEventListener(){
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String previousChildName) {
                    // 处理新添加的节点
                    Information newItem = dataSnapshot.getValue(Information.class);
                    callback.onSuccess(newItem);
                }
    
                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String previousChildName) {
                }
    
                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                }
                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String previousChildName) {
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    if (callback != null) {
                        callback.onFailure(databaseError.toException());
                    }
    
                }
            };
            itemsRef.addChildEventListener(childEventListenerForNewItem);
        }
    
    @Override
        public void removeListener() {
            if (itemsRef != null) {
    
                if (childEventListenerForNewItem != null) {
                    itemsRef.removeEventListener(childEventListenerForNewItem);
                }
                if (childEventListenerForUpdates != null) {
                    itemsRef.removeEventListener(childEventListenerForUpdates);
                }
            }
        }
    }
    ```
    

### Instance

```java
// 这个fetcher 是作为一个field值放在这个activity的class里最上面的
//因为fetchNewitem，removeListene 这两个method想要增加移除对应的同一个listener就要用同一个instance
FetchfromChangesOperation fetcher = new ChangesFetcher();

protected void onResume() {
        super.onResume();
//        Complaint info2 = new Complaint("subject2","content111");
//        dataList.add(0, info2);
//        adapter.notifyDataSetChanged();
        fetcher.fetchNewitem(path, new ResultCallback<Information>() {
            @Override
            public void onSuccess(Information newItem) {
                    dataList.add(0, newItem);
                    adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Exception e) {
                Toast.makeText(ComplaintAdminActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void onPause(){
        super.onPause();
        fetcher.removeListener();
    }
}
```

## Changes Special Fetcher （针对 Event）

```java
package com.example.b07project.dbOperation_Special;

public interface FetchSpecialChangesOperation
public class ChangesSpecialFetch implements FetchSpecialChangesOperation

public void fetchNewSpecialitem(String path, Class<?> claz,ResultCallback<Information> callback)
public void removeListener() 
```

- Code
    
    ```java
    public interface FetchSpecialChangesOperation {
        public void fetchNewSpecialitem(String path, Class<?> claz,ResultCallback<Information> callback);
        public void fetchUpdates(String path, Class<?> claz,ResultCallback<Information> callback);
        public void removeListener();
    }
    
    public class ChangesSpecialFetch implements FetchSpecialChangesOperation {
        private ChildEventListener childEventListenerForNewItem;
        private ValueEventListener childEventListenerForUpdates;
        private DatabaseReference itemsRef;
    
        @Override
        public void fetchNewSpecialitem(String path, Class<?> claz,ResultCallback<Information> callback) {
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
            DatabaseReference itemsRef = ref.child(path);
            childEventListenerForNewItem = new ChildEventListener(){
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, String previousChildName) {
                    if (Information.class.isAssignableFrom(claz)) {
                        // 处理新添加的节点
                        Information newItem = (Information) dataSnapshot.getValue(claz);
                        callback.onSuccess(newItem);
                    }
                }
    
                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, String previousChildName) {
                }
    
                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                }
                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, String previousChildName) {
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    if (callback != null) {
                        callback.onFailure(databaseError.toException());
                    }
    
                }
            };
            itemsRef.addChildEventListener(childEventListenerForNewItem);
        }
    
    @Override
        public void removeListener() {
            if (itemsRef != null) {
    
                if (childEventListenerForNewItem != null) {
                    itemsRef.removeEventListener(childEventListenerForNewItem);
                }
                if (childEventListenerForUpdates != null) {
                    itemsRef.removeEventListener(childEventListenerForUpdates);
                }
            }
        }
    }
    ```
    

- 还有一些其他的method比如说edit，delete， read还有 fetchUpdate 暂时还不用，想看的话可以直接在github上面看，基本上大同小异。