package com.example.homework07;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class ChatActivity extends AppCompatActivity {

    List<Chat> mchat = new ArrayList<Chat>();
    RecyclerView recyclerView;

    EditText textsend;
    MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        recyclerView = findViewById(R.id.chatrecyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        textsend = findViewById(R.id.textsend);

        messageAdapter = new MessageAdapter(mchat);
        recyclerView.setAdapter(messageAdapter);

        findViewById(R.id.send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Chat chat = new Chat("Akhil", textsend.getText().toString(), new Date());
                MainActivity.db.collection("chats").document(UUID.randomUUID().toString())
                        .set(chat)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(ChatActivity.this, "Message sent", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ChatActivity.this, "Message sent", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



        final CollectionReference docRef = MainActivity.db.collection("chats");
        docRef.orderBy("timestamp", Query.Direction.ASCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null) {
                    Log.w(MainActivity.TAG, "Listen failed.", e);
                    return;
                }

                String source = queryDocumentSnapshots != null && queryDocumentSnapshots.getMetadata().hasPendingWrites()
                        ? "Local" : "Server";

                mchat.clear();
                for(DocumentSnapshot documentSnapshot : queryDocumentSnapshots){

                    Log.d(MainActivity.TAG, source);
                    Chat chat = documentSnapshot.toObject(Chat.class);
                    mchat.add(chat);
                    messageAdapter.notifyDataSetChanged();
                }

                recyclerView.scrollToPosition(mchat.size()-1);
            }

        });

    }
}
