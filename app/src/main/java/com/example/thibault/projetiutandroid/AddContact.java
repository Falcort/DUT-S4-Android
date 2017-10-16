package com.example.thibault.projetiutandroid;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class AddContact extends AppCompatActivity implements Serializable
{

    private static final int GALLERY = 1;
    private static final int CAMERA = 2;
    private final String fileName = Environment.getExternalStorageDirectory() + File.separator + "IUT-Android" + File.separator + "saveContactIUTAndroid.txt";

    private String imageURI = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
    }

    public void onSubmit(View view) throws IOException {
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
            ArrayList<Contact> ContactList = new ArrayList<>();
            try
            {
                Log.d("STATE", "LIRE");
                File file = new File(fileName);
                FileInputStream FileInputSteam = new FileInputStream(file);
                ObjectInputStream ObjectInputStream = new ObjectInputStream(FileInputSteam);
                ContactList = (ArrayList<Contact>) ObjectInputStream.readObject();
                ObjectInputStream.close();
                FileInputSteam.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            try
            {
                Log.d("STATE", "AJOUTER");
                File file = new File(fileName);
                Contact contact = new Contact(name.getText().toString(), email.getText().toString(), phone.getText().toString(), sexe, imageURI);
                Log.d("STATE", "IMAGE URI DANS ADD CONTACT : " + imageURI);
                ContactList.add(contact);
                FileOutputStream fos = new FileOutputStream(file, false);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                os.writeObject(ContactList);
                os.close();
                fos.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            Intent intent = new Intent(AddContact.this, MainActivity.class);
            startActivity(intent);
            finish();


            /*
            Commentaire pour Serialization

            String newContact = name.getText() + ";" + email.getText() + ";" + phone.getText() + ";" + sexe + ";"  + imageURI;
            ArrayList<String> contact = getIntent().getStringArrayListExtra("listContact");

            contact.add(newContact);

            Intent intent = new Intent(AddContact.this, MainActivity.class);
            intent.putExtra("listContact", contact);
            startActivity(intent);
            finish();*/
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
                    takePhotoFromCamera();
                }
                else if(i == 1)
                {
                    choosePhotoFromGallary();
                }
                else
                {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    private void choosePhotoFromGallary()
    {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera()
    {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED)
        {
            return;
        }
        if (requestCode == GALLERY)
        {
            if (data != null)
            {
                Uri contentURI = data.getData();
                try
                {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    imageURI = saveImage(bitmap);
                    Log.d("STATE", "IMAGE URI : " + imageURI);

                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }

        }
        else if (requestCode == CAMERA)
        {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            imageURI =  saveImage(thumbnail);
            Log.d("STATE", "IMAGE URI : " + imageURI);
            Toast.makeText(AddContact.this, "Image Saved!", Toast.LENGTH_SHORT).show();
        }
    }

    public String saveImage(Bitmap myBitmap)
    {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + "/IUT-Android/");
        if (!wallpaperDirectory.exists())
        {
            wallpaperDirectory.mkdirs();
        }
        try
        {
            File f = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this, new String[]{f.getPath()}, new String[]{"image/jpeg"}, null);
            fo.close();
            return f.getAbsolutePath();
        }
        catch (IOException e1)
        {
            e1.printStackTrace();
        }
        return "";
    }

}
