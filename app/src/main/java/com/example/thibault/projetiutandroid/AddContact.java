package com.example.thibault.projetiutandroid;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AddContact extends AppCompatActivity
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
        }

        if (phone.length() <= 0)
        {
            phone.setError("Requis");
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
        }

        if(valide == 0)
        {
            CharSequence text = "Nom : " + name.getText() + "\nEmail : " + email.getText() + "\nPhone : " + phone.getText() + "\nSexe : " + sexe;
            Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
