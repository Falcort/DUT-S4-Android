package com.example.thibault.projetiutandroid;

import android.content.Context;
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
import android.widget.Toast;

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
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(getIntent().getSerializableExtra("listContact") != null)
        {
            listString = getIntent().getStringArrayListExtra("listContact");
            Log.d("STATE", "\nTaille de la listString MLain : \n" + listString.size());
            for (String contact : listString)
            {
                Log.d("STATE", "\nAjout\n");
                String split[] = contact.split(";");
                Hashmap = new HashMap<>();
                Hashmap.put("name", split[0]);
                Hashmap.put("email", split[1]);
                Hashmap.put("phone", split[2]);
                Hashmap.put("sexe", split[3]);
                ArrayMap.add(Hashmap);
            }
        }
        else
        {
            listString = new ArrayList<>();
        }

        listView = (ListView) findViewById(R.id.listViewContact);
        adapter = new SimpleAdapter(this.getBaseContext(), ArrayMap, R.layout.list_view_contact_layout, new String[] {"name", "phone", "email", "sexe"}, new int[] {R.id.titre});
        listView.setAdapter(adapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Context context = getApplicationContext();
                CharSequence text = ArrayMap.get(position).get("phone") + "\n" + ArrayMap.get(position).get("email") + "\n" + ArrayMap.get(position).get("sexe");
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                ArrayMap.remove(i);
                adapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    public void floatingClick(View view)
    {
        Intent intent = new Intent(MainActivity.this, AddContact.class);
        intent.putExtra("listContact", listString);
        startActivity(intent);
        finish();
    }
}
