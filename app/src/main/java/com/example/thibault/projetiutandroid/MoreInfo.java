package com.example.thibault.projetiutandroid;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class MoreInfo extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);

        TextView name = (TextView) findViewById(R.id.name);
        TextView phone = (TextView) findViewById(R.id.phone);
        TextView email = (TextView) findViewById(R.id.email);
        TextView sexe = (TextView) findViewById(R.id.sexe);
        ImageView img = (ImageView) findViewById(R.id.img);

        name.setText(getIntent().getStringExtra("name"));
        phone.setText(getIntent().getStringExtra("phone"));
        email.setText(getIntent().getStringExtra("email"));
        sexe.setText(getIntent().getStringExtra("sexe"));
        if(getIntent().getStringExtra("uri") != null)
        {
            img.setImageURI(Uri.parse(getIntent().getStringExtra("uri")));
        }
    }
}
