package fr.eseo.dis.afonsodebarre.eseodocumentation;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static fr.eseo.dis.afonsodebarre.eseodocumentation.MainActivity.LOGIN;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.MainActivity.PASSWORD;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.MainActivity.TOKEN;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.TousLesProjetsActivity.CONFID;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.TousLesProjetsActivity.DESCRPIP;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.TousLesProjetsActivity.EMP;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.TousLesProjetsActivity.IDPROJET;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.TousLesProjetsActivity.STUDENTS;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.TousLesProjetsActivity.SUPERVISOR;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.TousLesProjetsActivity.TITLE;

public class ProjectDetailsActivity extends AppCompatActivity {



    private String login, token, idProjet, title, confid, description, emplacementPoster, supervisor,students;
    private Bitmap imageString;
    public static final String IMAGE = "IMAGE";
    private static final String TAG = "TAG";
    private static final int CONNECTION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);
        login = getIntent().getStringExtra(LOGIN);
        token = getIntent().getStringExtra(TOKEN);
        idProjet = getIntent().getStringExtra(IDPROJET);
        confid = getIntent().getStringExtra(CONFID);
        title = getIntent().getStringExtra(TITLE);
        description = getIntent().getStringExtra(DESCRPIP);
        emplacementPoster = getIntent().getStringExtra(EMP);
        students = getIntent().getStringExtra(STUDENTS);
        supervisor = getIntent().getStringExtra(SUPERVISOR);
        Log.d(TAG, "Details: " + idProjet);

        WebServiceConnectivity wsc = new WebServiceConnectivity(this);
        wsc.execute("https://192.168.4.240/pfe/webservice.php?q=POSTR&user="+login+"&proj="+idProjet+"&style=FLB64&token="+token);
        String resultat = "";
        try {
            resultat = wsc.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Poster: " + login + token);
        byte[] decodedString = Base64.decode(resultat, Base64.DEFAULT);
        final Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        ImageView poster = (ImageView) findViewById(R.id.poster);
        imageString = decodedByte;
        poster.setImageBitmap(decodedByte);
        poster.setOnClickListener(onPosterClicked);

        Button retour = (Button) findViewById(R.id.bn_details);
        retour.setOnClickListener(onReturnButtonClicked);

        TextView detailsTitle = (TextView)findViewById(R.id.details_titre) ;
        TextView detailsEmplacament=(TextView)findViewById(R.id.details_emplacement) ;
        TextView detailsSupervisor=(TextView)findViewById(R.id.details_supervisor) ;
        TextView detailsDescLabel=(TextView)findViewById(R.id.details_desc_label) ;
        TextView detailsDescription=(TextView)findViewById(R.id.details_description) ;
        TextView detailsConfid=(TextView)findViewById(R.id.details_confid) ;
        TextView detailsStudentsLabel=(TextView)findViewById(R.id.details_students) ;
        TextView detailsStudents=(TextView)findViewById(R.id.rv_details_students) ;
        detailsTitle.setText(title);
        detailsConfid.setText("Confidentialité : "+confid);
        detailsDescLabel.setText("Description : ");
        detailsDescription.setText(description);
        detailsEmplacament.setText("Emplacement du poster: "+emplacementPoster);
        detailsStudentsLabel.setText("Etudiants :");
        detailsSupervisor.setText("Porteur : "+supervisor);
        detailsStudents.setText(students);



    }

    private final View.OnClickListener onPosterClicked = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            Intent intent = new Intent(ProjectDetailsActivity.this, PosterActivity.class);
            intent.putExtra(LOGIN, login);
            intent.putExtra(TOKEN, token);
            intent.putExtra(IDPROJET,idProjet);
            startActivityForResult(intent,CONNECTION);

        }
    };

    private final View.OnClickListener onReturnButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            Intent intent = new Intent(ProjectDetailsActivity.this, TousLesProjetsActivity.class);
            intent.putExtra(LOGIN, login);
            intent.putExtra(TOKEN, token);
            startActivityForResult(intent,CONNECTION);

        }
    };


}
