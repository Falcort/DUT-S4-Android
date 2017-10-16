package com.example.thibault.projetiutandroid;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements Serializable
{
    ArrayList<String> listString;
    ArrayList<HashMap<String, String>> ArrayMap = new ArrayList<>();
    HashMap<String, String> Hashmap;

    ListView listView;

    SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        }

        listView = (ListView) findViewById(R.id.listViewContact);
        adapter = new SimpleAdapter(this.getBaseContext(), ArrayMap, R.layout.list_view_contact_layout, new String[] {"name", "uri", "phone", "email", "sexe"}, new int[] {R.id.titre, R.id.img});
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
                intent.putExtra("uri", ArrayMap.get(position).get("uri"));
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                final int index = i;
                final CharSequence[] items = {"Supprimer contact", "Envoyé un SMS", "Annulé"};
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Suppresion contact");
                builder.setItems(items, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int item)
                    {
                        if(item == 0)
                        {
                            listString.remove(index);
                            ArrayMap.remove(index);
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
