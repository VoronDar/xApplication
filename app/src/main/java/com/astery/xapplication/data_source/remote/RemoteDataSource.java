package com.astery.xapplication.data_source.remote;

import android.util.Log;

import androidx.annotation.NonNull;

import com.astery.xapplication.data_source.remote.listeners.RemoteListGettable;
import com.astery.xapplication.data_source.remote.listeners.RemoteOneGettable;
import com.astery.xapplication.data_source.remote.utils.FbUsable;
import com.astery.xapplication.pojo.Advise;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RemoteDataSource {

    private final String LOG = "remote_data_source";

    private final String ADVISE_TABLE = "advise";

    private final FirebaseFirestore database;

    public RemoteDataSource(FirebaseFirestore database) {
        this.database = database;
    }




    public void getAdvisesForParent(String parentId, RemoteListGettable<Advise> listener){
        getData(database.collection(ADVISE_TABLE).whereEqualTo("parentId", parentId).get(),
                listener, ADVISE_TABLE);
    }







    private void getAllData(RemoteListGettable<FbUsable> loadable, String collectionName){
        getData(database.collection(collectionName).get(), loadable, collectionName);
    }

    private void getDataById(RemoteOneGettable<FbUsable> loadable, String collectionName, String id){
        getData(database.collection(collectionName).document(id).get(), loadable, collectionName);
    }

    private <T> void getData(Task<QuerySnapshot> fbTask, RemoteListGettable<T> loadable, String collectionName){
        fbTask.addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                QuerySnapshot snapshot = task.getResult();
                                if (snapshot == null){
                                    Log.i(LOG, "from " + collectionName + " got nothing");
                                    return;
                                }
                                List<T> list = new ArrayList<>();
                                for (QueryDocumentSnapshot document : snapshot) {
                                    list.add(document.toObject(loadable.getObjectClass()));
                                    ((FbUsable)list.get(list.size()-1)).setId(document.getId());
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

    private <T> void getData(Task<DocumentSnapshot> fbTask, RemoteOneGettable<T> loadable, String collectionName){
        fbTask.addOnCompleteListener(task -> {
            if (task.getResult() != null) {
                @NonNull T object= Objects.requireNonNull(
                        task.getResult().toObject(loadable.getObjectClass()));
                ((FbUsable)object).setId(task.getResult().getId());
                loadable.getResult(object);
            }
        }).addOnFailureListener(e ->
                Log.i(LOG, "while getting element from " + collectionName + " got an error " + e.getMessage()));
        }
    }
