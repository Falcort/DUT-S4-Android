package com.example.thibault.projetiutandroid;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class AddContact extends AppCompatActivity implements Serializable
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
    }

    public void onSubmit(View view)
    {
        int valide = 0;

        Context context = getApplicationContext();
        EditText name = (EditText) findViewById(R.id.name);
        EditText email = (EditText) findViewById(R.id.email);
        EditText phone = (EditText) findViewById(R.id.phone);
        String sexe = "";

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.RadioGroup);
        int radioID = radioGroup.getCheckedRadioButtonId();
        View radioView = radioGroup.findViewById(radioID);
        int indexRadio = radioGroup.indexOfChild(radioView);

        if(name.length() <= 0)
        {
            name.setError("Requis");
            valide++;
        }

        if(email.length() <= 0)
        {
            email.setError("Requis");
            valide++;
        }

        if (phone.length() <= 0)
        {
            phone.setError("Requis");
            valide++;
        }

        if(indexRadio == 1)
        {
            sexe = "Homme";
        }
        else if(indexRadio == 0)
        {
            sexe = "Femme";
        }
        else
        {
            Toast toast = Toast.makeText(context, "Sex requis", Toast.LENGTH_SHORT);
            toast.show();
            valide++;
        }

        if(valide == 0)
        {
            String newContact = name.getText() + ";" + email.getText() + ";" + phone.getText() + ";" + sexe;
            Log.d("STATE", "\n\n\n" + "ICI" + "\n\n\n");
            ArrayList<String> contact = getIntent().getStringArrayListExtra("listContact");
            Log.d("STATE", "\n\n\n" + "ICI" + "\n\n\n");
            Log.d("STATE", "\n\n\n" + contact.size() + "\n\n\n");
            for(String test : contact)
            {
                Log.d("STATE", "\n\n\n" + test + "\n\n\n");
            }

            contact.add(newContact);

            Intent intent = new Intent(AddContact.this, MainActivity.class);
            intent.putExtra("listContact", contact);
            startActivity(intent);
            finish();
        }
    }
}
