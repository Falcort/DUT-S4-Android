package fr.jenniault.projet_seismes;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivityAll extends FragmentActivity implements OnMapReadyCallback
{

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_all);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        Intent intent = getIntent();
        final ArrayList<Seisme> listSeismes = (ArrayList<Seisme>) intent.getSerializableExtra("map");
        String[] split;
        for(Seisme seisme : listSeismes)
        {
            split = seisme.getPoint().split(" ");
            mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(split[0]), Double.parseDouble(split[1]))).title(seisme.getTitle()));
        }

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener()
        {
            @Override
            public void onInfoWindowClick(Marker marker)
            {
                Context context = getApplicationContext();
                String title =  marker.getTitle();
                int index = 0;
                for(Seisme seisme : listSeismes)
                {
                    if(seisme.getTitle().equals(title))
                    {
                        break;
                    }
                    index++;
                }

                Intent intent = new Intent(MapsActivityAll.this, infoDetail.class);
                intent.putExtra("item", listSeismes.get(index));
                startActivity(intent);
            }
        });
    }
}
