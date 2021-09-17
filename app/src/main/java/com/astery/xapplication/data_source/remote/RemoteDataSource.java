package com.astery.xapplication.data_source.remote;

import android.util.Log;

import androidx.annotation.NonNull;

import com.astery.xapplication.data_source.remote.listeners.RemoteListGettable;
import com.astery.xapplication.data_source.remote.listeners.RemoteOneGettable;
import com.astery.xapplication.data_source.remote.utils.FbUsable;
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




    public <T> void getValuesWithParent(String parentId, RemoteListGettable<T> listener){
        String tableName = FbConverter.getTableName(listener.getObjectClass().getSimpleName());
        getData(database.collection(tableName).whereEqualTo("parentId", parentId).get(),
                listener, tableName);
    }

    public <T> void getValues(RemoteListGettable<T> listener) {
        getAllData(listener, FbConverter.getTableName(listener.getObjectClass().getSimpleName()));
    }

    public <T> void getAllData(RemoteListGettable<T> loadable, String collectionName) {
        getData(database.collection(collectionName).get(), loadable, collectionName);
    }

    public <T> void getDataById(RemoteOneGettable<T> loadable, String collectionName, String id) {
        getData(database.collection(collectionName).document(id).get(), loadable, collectionName);
    }




    private <T> void getData(Task<QuerySnapshot> fbTask, RemoteListGettable<T> loadable, String collectionName) {
        fbTask.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot snapshot = task.getResult();
                if (snapshot == null) {
                    Log.i(LOG, "from " + collectionName + " got nothing");
                    return;
                }
                List<T> list = new ArrayList<>();
                for (QueryDocumentSnapshot document : snapshot) {
                    list.add(document.toObject(loadable.getObjectClass()));
                    ((FbUsable) list.get(list.size() - 1)).setId(document.getId());
                }
                loadable.getResult(list);

            } else {
                Log.i(LOG, "from " + collectionName + " get unSuccessful task");
            }
        })
                .addOnFailureListener(e -> {
                    loadable.getError(e.getMessage());
                    Log.i(LOG, "while getting from " + collectionName + " got an error " + e.getMessage());
                });
    }

    private <T> void getData(Task<DocumentSnapshot> fbTask, RemoteOneGettable<T> loadable, String collectionName) {
        fbTask.addOnCompleteListener(task -> {
            if (task.getResult() != null) {
                @NonNull T object = Objects.requireNonNull(
                        task.getResult().toObject(loadable.getObjectClass()));
                ((FbUsable) object).setId(task.getResult().getId());
                loadable.getResult(object);
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