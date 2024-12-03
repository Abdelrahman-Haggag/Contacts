package com.example.contacts;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Objects;

public class ContactActivity extends AppCompatActivity {
    Button update_btn;
    EditText userName,phoneNumber;
    ImageView back ,edit, delete , call,call_info, meg,meg_info;
    ContactAdapter adapter;
    ContactDataBase db;
    String oldPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_contact);
        init();
        getDataIntent();

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_btn.setVisibility(View.VISIBLE);
                oldPhone=phoneNumber.getText().toString();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ContactActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=userName.getText().toString();
                String phone=phoneNumber.getText().toString();
                if(valid(name,phone)){

                    boolean res= db.updateUser(new User(name,phone),oldPhone); //review this point how to update
                    if(res){
                        Toast.makeText(ContactActivity.this,"Updated Successfully",Toast.LENGTH_LONG).show();
                        adapter.notifyDataSetChanged();
                    }

                }
                update_btn.setVisibility(View.INVISIBLE);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //show message to user to ensure if need to delete or not
                AlertDialog.Builder alert = new AlertDialog.Builder(ContactActivity.this);
                alert.setTitle("Delete contact?");
                alert.setMessage("Are you sure to delete this contact");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //do your work here
                        dialog.dismiss();
                        boolean res=db.deleteData(phoneNumber.getText().toString());
                        if(res){
                            Toast.makeText(ContactActivity.this,"Deleted ",Toast.LENGTH_LONG).show();
                            Intent i=new Intent(ContactActivity.this,MainActivity.class);
                            adapter.notifyDataSetChanged();
                            startActivity(i);

                        }else{
                            Toast.makeText(ContactActivity.this,"something wrong please try later",Toast.LENGTH_LONG).show();
                        }
                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });

                alert.show();
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Intent.ACTION_DIAL);
                String temp ="tel:" + phoneNumber.getText().toString();
                in.setData(Uri.parse(temp));
                startActivity(in);
            }
        });
        call_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Intent.ACTION_DIAL);
                String temp ="tel:" + phoneNumber.getText().toString();
                in.setData(Uri.parse(temp));
                startActivity(in);
            }
        });

        meg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent smsIntent = new Intent(Intent.ACTION_SENDTO,Uri.parse("sms:"+phoneNumber.getText().toString()));
                smsIntent.putExtra("sms_body","Hello"+userName.getText().toString());
                startActivity(smsIntent);
            }
        });
        meg_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent smsIntent = new Intent(Intent.ACTION_SENDTO,Uri.parse("sms:"+phoneNumber.getText().toString()));
                smsIntent.putExtra("sms_body","Hello"+userName.getText().toString());
                startActivity(smsIntent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inte = new Intent(ContactActivity.this, MainActivity.class);
                startActivity(inte);
            }
        });

    }

    @SuppressLint("WrongViewCast")
    public void  init(){
        userName = findViewById(R.id.user_name_contact);
        phoneNumber = findViewById(R.id.phoneNumber_contact);
        update_btn=findViewById(R.id.button);
        call=findViewById(R.id.call_number);
        meg=findViewById(R.id.messsage_number);
        delete=findViewById(R.id.delete_contact);
        edit=findViewById(R.id.update_contact);
        call_info=findViewById(R.id.info_call_number);
        meg_info=findViewById(R.id.info_messsage_number);
        back =findViewById(R.id.back_to_main);
        db = new ContactDataBase(this,ContactDataBase.TABLE_USER,null,ContactDataBase.DATABASE_VERSION);
    }

    public Boolean valid(String name,String phone){
        boolean valid=true;

        if(name.isEmpty()){
            userName.setError("Please Fill it correctly");
            valid=false;
        }
        if(phone.isEmpty()||(!phone.matches("01[0-9]{9}"))){
            phoneNumber.setError("Please Fill it correctly");
            valid=false;
        }
        return valid;
    }

    public void getDataIntent(){
        String name =getIntent().getStringExtra("name");
        Log.e("name",name);
        ArrayList<User> users=db.retrieveAllData();
        for(int i=0; i<users.size();i++){
            if(Objects.equals(users.get(i).getName(), name)){
                userName.setText(users.get(i).getName());
                phoneNumber.setText(users.get(i).getPhone());
                break;
            }
        }
    }
}