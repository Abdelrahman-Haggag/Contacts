package com.example.contacts;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    ArrayList<User>users,contactListFull;//used in search
    Context context;

    public ContactAdapter(ArrayList<User> users,Context context) {
        this.users = users;
        this.context = context;
        this.contactListFull=new ArrayList<>(this.users);//used to restore all contacts
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.user_name_contact);

        }
    }
    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {//inflate means reading the layout xml and translate them into code
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item_design,parent,false);

        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {
        User user=users.get(position);
        holder.name.setText(user.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context,ContactActivity.class);
                i.putExtra("name",user.getName());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void filter(String query) {
        if (query.isEmpty()) {
            users.clear();
            users.addAll(contactListFull); // Restore the original list
        } else {
            ArrayList<User> filteredList = new ArrayList<>();
            for (User contact : contactListFull) {
                if (contact.getName().toLowerCase().contains(query.toLowerCase())) {
                    filteredList.add(contact);
                }
            }
            users.clear();
            users.addAll(filteredList);
        }
        notifyDataSetChanged();
    }


}
