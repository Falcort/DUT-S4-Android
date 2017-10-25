package fr.jenniault.projet_seismes;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class infoDetail extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_detail);


        TextView title = (TextView) findViewById(R.id.title);
        TextView update = (TextView) findViewById(R.id.update);
        TextView link = (TextView) findViewById(R.id.link);
        TextView summary = (TextView) findViewById(R.id.summary);
        TextView point = (TextView) findViewById(R.id.point);
        TextView elev = (TextView) findViewById(R.id.elev);
        TextView age = (TextView) findViewById(R.id.age);
        TextView magnitude = (TextView) findViewById(R.id.magnitude);
        TextView contributor = (TextView) findViewById(R.id.contributor);
        TextView author = (TextView) findViewById(R.id.author);
        TextView id = (TextView) findViewById(R.id.id);

        Intent intent = getIntent();
        Seisme seisme = (Seisme) intent.getSerializableExtra("item");

        title.setText("Titre : " + seisme.getTitle());
        update.setText("Date : " + seisme.getUpdate());
        link.setText("Lien : " + seisme.getLink());
        summary.setText("Résumé du séisme : " + Html.fromHtml(seisme.getSumary())); /*Ne fonctionne plus et ne rend pas les dd dt */
        point.setText("Coordonnées : " + seisme.getPoint());
        elev.setText("Altitude : " + seisme.getElev());
        age.setText("Ancienneté : " + seisme.getAge());
        magnitude.setText("Magnitude : " + seisme.getMagnitude());
        contributor.setText("Contributeur : " + seisme.getContributor());
        author.setText("Auteur : " + seisme.getAuthor());
        id.setText("Identification : " + seisme.getId());

        link.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
