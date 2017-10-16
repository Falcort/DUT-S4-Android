package com.example.thibault.projetiutandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SendSMS extends AppCompatActivity
{
    String phone = "";
    EditText text;
    EditText phoneView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);
        text = (EditText) findViewById(R.id.text);
        phoneView = (EditText) findViewById(R.id.phone);
        phone = getIntent().getStringExtra("phone");
        phoneView.setText(phone);
    }

    public void onSend(View view)
    {
        if(text.length() == 0)
        {
            text.setError("Requis");
        }
        else if(phone.length() == 0)
        {
            text.setError("Requis");
        }
        else
        {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phone, null, text.getText().toString(), null, null);
            Toast.makeText(getApplicationContext(), "SMS Envoyé.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(SendSMS.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
