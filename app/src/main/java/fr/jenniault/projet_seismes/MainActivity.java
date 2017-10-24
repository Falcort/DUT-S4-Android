package fr.jenniault.projet_seismes;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements AsyncResponse
{
    URL url;
    ArrayList<Seisme> listSeismes = new ArrayList<>();
    AsyncTackGetSeismes asyncTask;
    ArrayList<HashMap<String, String>> ArrayMap = new ArrayList<>();
    HashMap<String, String> hashMap;
    ListView listview;
    ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try
        {
            if(isOnline())
            {
                asyncTask = new AsyncTackGetSeismes();
                asyncTask.delegate = this;
                asyncTask.execute("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/4.5_week.atom").get();
            }
            else
            {
                Toast toast = Toast.makeText(getBaseContext(), "Pas de co", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        listview = (ListView) findViewById(R.id.listview);
        spinner = (ProgressBar) findViewById(R.id.spinner);
        listview.setVisibility(View.INVISIBLE);
        spinner.setVisibility(View.VISIBLE);
    }

    @Override
    public void processFinish(String output)
    {
        try
        {
            parseXML(output);
        }
        catch (Exception ex)
        {
            ex.getStackTrace();
        }
    }

    private void parseXML(String xml) throws XmlPullParserException
    {
        final String ENTRY = "entry";
        final String ID = "id";
        final String TITLE = "title";
        final String UPDATED = "updated";
        final String LINK = "link";
        final String SUMMARY = "summary";
        final String POINT = "point";
        final String ELEV = "elev";
        final String FEED = "feed";
        final String CATEGORY = "category";
        final String GEORSS = "georss";

        String label;

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser parser = factory.newPullParser();

        try
        {
            parser.setInput(new StringReader(xml));
            int eventType = parser.getEventType();
            boolean done = false;
            Seisme seisme = null;

            while (eventType != XmlPullParser.END_DOCUMENT && !done)
            {
                String name = null;
                switch (eventType)
                {
                    case XmlPullParser.START_DOCUMENT:
                    {
                        break;
                    }
                    case XmlPullParser.START_TAG:
                    {
                        name = parser.getName();
                        if (name.equalsIgnoreCase(ENTRY))
                        {
                            seisme = new Seisme();
                        }
                        else if (seisme != null)
                        {
                            if (name.equalsIgnoreCase(ID))
                            {
                                seisme.setId(parser.nextText());
                            }
                            else if (name.equalsIgnoreCase(TITLE))
                            {
                                seisme.setTitle(parser.nextText());
                            }
                            else if (name.equalsIgnoreCase(UPDATED))
                            {
                                seisme.setUpdate(parser.nextText());
                            }
                            else if (name.equalsIgnoreCase(LINK))
                            {
                                seisme.setLink(parser.getAttributeValue(2));
                            }
                            else if (name.equalsIgnoreCase(SUMMARY))
                            {
                                seisme.setSumary(parser.nextText());
                            }
                            else if (name.contains(POINT))
                            {
                                seisme.setPoint(parser.nextText());
                            }
                            else if (name.contains(ELEV))
                            {
                                seisme.setElev(parser.nextText());
                            }
                            else if (name.equalsIgnoreCase(CATEGORY))
                            {
                                label = parser.getAttributeValue(0);
                                if (label.equals("Magnitude"))
                                {
                                    seisme.setMagnitude(seisme.getTitle().substring(2, 5));
                                }
                                else if (label.equals("Age"))
                                {
                                    seisme.setAge(parser.getAttributeValue(1));
                                }
                                else if (label.equals("Contributor"))
                                {
                                    seisme.setContributor(parser.getAttributeValue(1));
                                }
                                else if (label.equals("Author"))
                                {
                                    seisme.setAuthor(parser.getAttributeValue(1));
                                }
                            }
                        }
                        break;
                    }
                    case XmlPullParser.END_TAG:
                    {
                        name = parser.getName();
                        if (name.equalsIgnoreCase(ENTRY) && seisme != null)
                        {
                            listSeismes.add(seisme);
                        }
                        else if (name.equalsIgnoreCase(FEED))
                        {
                            done = true;
                        }
                        break;
                    }
                }
                eventType = parser.next();
            }
        }
        catch(Exception ex)
        {
            ex.getStackTrace();
        }
        updateListView();
    }

    private void lireSeismes()
    {
        for (Seisme seisme : listSeismes)
        {
            Log.d("STATE", seisme.getTitle());
        }
    }

    private void updateListView()
    {
        String magnitude;
        for(Seisme seisme : listSeismes)
        {
            hashMap = new HashMap<>();
            hashMap.put("id", seisme.getId());
            hashMap.put("title", seisme.getTitle());
            hashMap.put("update", seisme.getUpdate());
            hashMap.put("link", seisme.getLink());
            hashMap.put("summary", seisme.getSumary());
            hashMap.put("point", seisme.getPoint());
            hashMap.put("elev", seisme.getElev());
            hashMap.put("age", seisme.getAge());
            hashMap.put("magnitude", seisme.getMagnitude());
            hashMap.put("contributor", seisme.getContributor());
            hashMap.put("author", seisme.getAuthor());
            if(Double.parseDouble(seisme.getMagnitude()) < 5)
            {
                hashMap.put("color", String.valueOf(R.mipmap.ic_green));
            }
            else if(Double.parseDouble(seisme.getMagnitude()) >= 5 && Double.parseDouble(seisme.getMagnitude()) <= 6.9)
            {
                hashMap.put("color", String.valueOf(R.mipmap.ic_orange));
            }
            else
            {
                hashMap.put("color", String.valueOf(R.mipmap.ic_red));
            }
            ArrayMap.add(hashMap);

        }
        SimpleAdapter adapter = new SimpleAdapter(this.getBaseContext(), ArrayMap, R.layout.listview_layout, new String[] {"title", "update", "color"}, new int[] {R.id.title, R.id.updated, R.id.img});
        listview.setAdapter(adapter);
        listview.setClickable(true);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Context context = getApplicationContext();
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra("seisme", listSeismes.get(i));
                startActivity(intent);
            }
        });

        listview.setVisibility(View.VISIBLE);
        spinner.setVisibility(View.INVISIBLE);
    }

    public void refresh(View view)
    {
        if(isOnline())
        {
            asyncTask = new AsyncTackGetSeismes();
            listview.setVisibility(View.INVISIBLE);
            spinner.setVisibility(View.VISIBLE);
            try
            {
                asyncTask.delegate = this;
                asyncTask.execute("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/4.5_week.atom").get();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        else
        {
            Toast toast = Toast.makeText(getBaseContext(), "Pas de co", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void openMap(View view)
    {
        Intent intent = new Intent(MainActivity.this, MapsActivityAll.class);
        intent.putExtra("map", listSeismes);
        startActivity(intent);
    }

    public boolean isOnline()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}