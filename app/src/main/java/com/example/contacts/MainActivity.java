package com.example.contacts;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ImageView add_btn;
    ContactDataBase db;
    ContactAdapter adapter;
    RecyclerView recycler;
    SearchView search_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        add_btn=findViewById(R.id.add);
        recycler=findViewById(R.id.recyclerView);
        search_icon=findViewById(R.id.search);

        db=new ContactDataBase(this,ContactDataBase.TABLE_USER,null,ContactDataBase.DATABASE_VERSION);

        showAllContacts();
        db.retrieveAllData().size();

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,AddActivity.class);
                startActivity(i);
            }
        });
        search_icon.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.filter(newText);
                recycler.setAdapter(adapter);

                return true;
            }
        });



    }

    public void showAllContacts(){
        ArrayList<User> users=new ArrayList<>();
        users=db.retrieveAllData();
        adapter=new ContactAdapter(users,MainActivity.this);
        recycler.setAdapter(adapter);

    }
}