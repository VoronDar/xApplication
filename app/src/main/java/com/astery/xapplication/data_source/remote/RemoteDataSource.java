package com.astery.xapplication.data_source.remote;

import android.util.Log;

import androidx.annotation.NonNull;
import com.astery.xapplication.data_source.remote.utils.FbUsable;
import com.astery.xapplication.repository.listeners.GetItemListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RemoteDataSource {

    private final String LOG = "remote_data_source";

    private final FirebaseFirestore database;

    public RemoteDataSource(FirebaseFirestore database) {
        this.database = database;
    }




    public <T> void getValuesWithParent(String parentId, GetItemListener<List<T>> listener, Class<T> className){
        String tableName = FbConverter.getTableName(className.getSimpleName());
        getData(database.collection(tableName).whereEqualTo("parentId", parentId).get(),
                listener, tableName, className);
    }

    public <T> void getValues(GetItemListener<List<T>> listener, Class<T> className) {
        getAllData(listener, FbConverter.getTableName(className.getSimpleName()), className);
    }

    public <T> void getAllData(GetItemListener<List<T>> loadable, String collectionName, Class<T> className) {
        getData(database.collection(collectionName).get(), loadable, collectionName, className);
    }

    public <T> void getDataById(GetItemListener<T> loadable, String collectionName, String id, Class<T> className) {
        getOneData(database.collection(FbConverter.getTableName(collectionName)).document(id).get(),
                loadable, FbConverter.getTableName(collectionName), className);
    }




    private <T> void getData(Task<QuerySnapshot> fbTask, GetItemListener<List<T>> loadable, String collectionName, Class<T> className) {

        fbTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot snapshot = task.getResult();
                if (snapshot == null) {
                    Log.i(LOG, "from " + collectionName + " got nothing");
                    return;
                }
                List<T> list = new ArrayList<>();
                for (QueryDocumentSnapshot document : snapshot) {
                    list.add((T) document.toObject(className));
                    ((FbUsable)list.get(list.size() - 1)).setId(document.getId());
                }
                loadable.getItem(list);

            } else {
                Log.i(LOG, "from " + collectionName + " get unSuccessful task");
            }
        })
                .addOnFailureListener(e -> {
                    loadable.error();
                    Log.i(LOG, "while getting from " + collectionName + " got an error " + e.getMessage());
                });
    }

    private <T> void getOneData(Task<DocumentSnapshot> fbTask, GetItemListener<T> loadable, String collectionName, Class<T> className) {
        fbTask.addOnCompleteListener(task -> {
            if (task.getResult() != null) {

                @NonNull T object = Objects.requireNonNull(
                        task.getResult().toObject(className));
                ((FbUsable) object).setId(task.getResult().getId());
                loadable.getItem(object);
            }
        }).addOnFailureListener(e ->
                Log.i(LOG, "while getting element from " + collectionName + " got an error " + e.getMessage()));
    }




    enum FbConverter{
        ADVISE("advise", "Advise"),
        CATEGORY("category", "Category"),
        ITEM("item", "Item"),
        ARTICLE("article", "Article"),
        DESIRE("desire", "Desire"),
        QUESTION("question", "Question"),
        ANSWER("answer", "Answer"),
        EVENT_TEMPLATE("event", "EventTemplate"),
        WARNING_TEMPLATE("warning", "WarningTemplate");

        FbConverter(String name, String className) {
            this.name = name;
            this.className = className;
        }
        protected String name;
        protected String className;

        static String getTableName(String className) {
            for (FbConverter n : FbConverter.values()) {
                if (n.className.equals(className)) {
                    return n.name;
                }
            }
            throw new RuntimeException("TableNames got invalid className " + className);

        }
    }

}