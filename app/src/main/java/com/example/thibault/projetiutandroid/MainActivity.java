package com.example.thibault.projetiutandroid;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Serializable
{
    ArrayList<Contact> listContact;
    ArrayList<String> listString;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getIntent().getSerializableExtra("listContact") != null)
        {
            listContact = new ArrayList<>();
            Context context = getApplicationContext();
            ArrayList<String> listString = getIntent().getStringArrayListExtra("listContact");

            for(String contact : listString)
            {
                String components[] = contact.split(";");
                Contact contactObj = new Contact(components[0], components[1], components[2], components[3]);
                listContact.add(contactObj);
            }

            Toast toast = Toast.makeText(context, listContact.get(0).getEmail() + "\n" + listContact.get(0).getName(), Toast.LENGTH_SHORT);
            toast.show();
        }
        else
        {
            listString = new ArrayList<>();
        }
    }

    public void floatingClick(View view)
    {
        Intent intent = new Intent(MainActivity.this, AddContact.class);
        intent.putExtra("listContact", listString);
        startActivity(intent);
    }
}
