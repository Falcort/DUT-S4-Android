package com.example.thibault.projetiutandroid;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements Serializable
{
    ArrayList<String> listString;
    ArrayList<HashMap<String, String>> ArrayMap = new ArrayList<>();
    HashMap<String, String> Hashmap;
    String fileName = Environment.getExternalStorageDirectory() + File.separator + "IUT-Android" + File.separator + "saveContactIUTAndroid.txt";
    ListView listView;

    ArrayList<Contact> ContactList = new ArrayList<>();
    SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = getApplicationContext();
        try
        {
            File file = new File(fileName);
            if(!file.exists())
            {
                file.createNewFile();
            }
            FileInputStream FileInputSteam = new FileInputStream(file);
            ObjectInputStream ObjectInputStream = new ObjectInputStream(FileInputSteam);
            ContactList = (ArrayList<Contact>) ObjectInputStream.readObject();
            ObjectInputStream.close();
            FileInputSteam.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        Log.d("STATE", "Size : " + ContactList.size());
        for (Contact contact : ContactList)
        {
            Hashmap = new HashMap<>();
            Hashmap.put("name", contact.getName());
            Hashmap.put("email", contact.getEmail());
            Hashmap.put("phone", contact.getPhone());
            Hashmap.put("sexe", contact.getSexe());
            Hashmap.put("img", contact.getImg());
            Log.d("STATE", "IMAGE DANS LE FOR : " + contact.getImg());
            ArrayMap.add(Hashmap);
        }



        /*

        Commentaire pour Serialization

        if(getIntent().getSerializableExtra("listContact") != null)
        {
            listString = getIntent().getStringArrayListExtra("listContact");
            for (String contact : listString)
            {
                Log.d("STATE", contact);
                String split[] = contact.split(";");
                Hashmap = new HashMap<>();
                Hashmap.put("name", split[0]);
                Hashmap.put("email", split[1]);
                Hashmap.put("phone", split[2]);
                Hashmap.put("sexe", split[3]);
                if(split.length > 4)
                {
                    Hashmap.put("uri", split[4]);
                }
                ArrayMap.add(Hashmap);
            }
        }
        else
        {
            listString = new ArrayList<>();
        }*/

        listView = (ListView) findViewById(R.id.listViewContact);
        adapter = new SimpleAdapter(this.getBaseContext(), ArrayMap, R.layout.list_view_contact_layout, new String[] {"name", "img", "phone", "email", "sexe"}, new int[] {R.id.titre, R.id.img});
        listView.setAdapter(adapter);

        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(MainActivity.this, MoreInfo.class);
                intent.putExtra("name", ArrayMap.get(position).get("name"));
                intent.putExtra("phone", ArrayMap.get(position).get("phone"));
                intent.putExtra("email", ArrayMap.get(position).get("email"));
                intent.putExtra("sexe", ArrayMap.get(position).get("sexe"));
                intent.putExtra("uri", ArrayMap.get(position).get("img"));
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                final int index = i;
                final CharSequence[] items = {"Supprimer contact", "Envoyer un SMS", "Annuler"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Suppression contact");
                builder.setItems(items, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int item)
                    {
                        if(item == 0)
                        {
                            try
                            {
                                Log.d("STATE", "AJOUTER");
                                File file = new File(fileName);
                                ArrayMap.remove(index);
                                ContactList.remove(index);
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
                            adapter.notifyDataSetChanged();

                        }
                        else if(item == 1)
                        {
                            Intent intent = new Intent(MainActivity.this, SendSMS.class);
                            intent.putExtra("phone", ArrayMap.get(index).get("phone"));
                            startActivity(intent);
                        }
                        else
                        {
                            dialogInterface.dismiss();
                        }
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public void floatingClick(View view)
    {
        Intent intent = new Intent(MainActivity.this, AddContact.class);
        intent.putExtra("listContact", listString);
        startActivity(intent);
    }
}
