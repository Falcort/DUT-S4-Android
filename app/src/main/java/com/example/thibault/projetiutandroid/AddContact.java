package com.example.thibault.projetiutandroid;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class AddContact extends AppCompatActivity implements Serializable
{
    ImageSwitcher imageview;

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

    public void onAddPhoto(View view)
    {
        final CharSequence[] items = {"Prendre une photo", "Chosiir depuis la gallerie", "Annulée"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddContact.this);
        builder.setTitle("Ajouter une photo");
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                if(i == 0)
                {
                    Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePicture, 0);
                }
                else if(i == 1)
                {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);
                }
                else
                {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent)
    {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode)
        {
            case 0:
            {
                if(resultCode == RESULT_OK)
                {
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageview.setImageURI(selectedImage);
                }
                break;
            }
            case 1:
                {
                if(resultCode == RESULT_OK)
                {
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageview.setImageURI(selectedImage);
                }
                break;
            }
        }
    }
}
