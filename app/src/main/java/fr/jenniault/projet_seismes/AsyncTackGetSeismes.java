package fr.jenniault.projet_seismes;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncTackGetSeismes extends AsyncTask<Object, Void, String>
{
    public AsyncResponse delegate = null;
    TextView tv;
    String xml;

    @Override
    protected void onPostExecute(String s)
    {
        delegate.processFinish(xml);
    }

    @Override
    protected String doInBackground(Object... objects)
    {
        xml = "";
        String urlString = (String) objects[0];
        String line;
        try
        {
            URL url = new URL(urlString);
            tv = (TextView) objects[1];
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream() ) );
                while ((line = in.readLine()) != null)
                {
                    xml += line;
                }
                in.close();
            }
        }
        catch(Exception ex)
        {
            return ex.getStackTrace().toString();
        }
        return xml;
    }
}
