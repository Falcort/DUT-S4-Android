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

        title.setText(seisme.getTitle());
        update.setText(seisme.getUpdate());
        link.setText(seisme.getLink());
        summary.setText(Html.fromHtml(seisme.getSumary())); /*Ne fonctionne plus et ne rend pas les dd dt */
        point.setText(seisme.getPoint());
        elev.setText(seisme.getElev());
        age.setText(seisme.getAge());
        magnitude.setText(seisme.getMagnitude());
        contributor.setText(seisme.getContributor());
        author.setText(seisme.getAuthor());
        id.setText(seisme.getId());

        link.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
