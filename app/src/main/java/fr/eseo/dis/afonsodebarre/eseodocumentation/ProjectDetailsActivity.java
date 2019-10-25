package fr.eseo.dis.afonsodebarre.eseodocumentation;


import android.annotation.SuppressLint;
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

import androidx.appcompat.app.AppCompatActivity;


import java.util.concurrent.ExecutionException;

import static fr.eseo.dis.afonsodebarre.eseodocumentation.MainActivity.LOGIN;

import static fr.eseo.dis.afonsodebarre.eseodocumentation.MainActivity.TOKEN;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.TousLesProjetsActivity.CONFID;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.TousLesProjetsActivity.DESCRPIP;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.TousLesProjetsActivity.EMP;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.TousLesProjetsActivity.IDPROJET;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.TousLesProjetsActivity.STUDENTS;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.TousLesProjetsActivity.SUPERVISOR;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.TousLesProjetsActivity.TITLE;

public class ProjectDetailsActivity extends AppCompatActivity {

    private String login;
    private String token;
    private String idProjet;

    private static final String TAG = "TAG";
    private static final int CONNECTION = 0;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);
        login = getIntent().getStringExtra(LOGIN);
        token = getIntent().getStringExtra(TOKEN);
        idProjet = getIntent().getStringExtra(IDPROJET);
        String confid = getIntent().getStringExtra(CONFID);
        String title = getIntent().getStringExtra(TITLE);
        String description = getIntent().getStringExtra(DESCRPIP);
        String emplacementPoster = getIntent().getStringExtra(EMP);
        String students = getIntent().getStringExtra(STUDENTS);
        String supervisor = getIntent().getStringExtra(SUPERVISOR);
        Log.d(TAG, "Details: " + idProjet);

        WebServiceConnectivity wsc = new WebServiceConnectivity(this);
        wsc.execute("https://192.168.4.240/pfe/webservice.php?q=POSTR&user="+login+"&proj="+idProjet+"&style=FLB64&token="+token);
        String result = "";
        try {
            result = wsc.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "Poster: " + login + token);
        byte[] decodedString = Base64.decode(result, Base64.DEFAULT);
        final Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        ImageView poster = findViewById(R.id.poster);

        poster.setImageBitmap(decodedByte);
        poster.setOnClickListener(onPosterClicked);

        Button returnToProjects = findViewById(R.id.bn_details);
        returnToProjects.setOnClickListener(onReturnButtonClicked);

        Button map = findViewById(R.id.map_button);
        map.setOnClickListener(onMapButtonClicked);

        TextView detailsTitle = findViewById(R.id.details_titre) ;
        TextView detailsEmplacament= findViewById(R.id.details_emplacement) ;
        TextView detailsSupervisor= findViewById(R.id.details_supervisor) ;
        TextView detailsDescLabel= findViewById(R.id.details_desc_label) ;
        TextView detailsDescription= findViewById(R.id.details_description) ;
        TextView detailsConfid= findViewById(R.id.details_confid) ;
        TextView detailsStudentsLabel= findViewById(R.id.details_students) ;
        TextView detailsStudents= findViewById(R.id.rv_details_students) ;
        detailsTitle.setText(title);
        detailsConfid.setText("Confidentiality : "+ confid);
        detailsDescLabel.setText("Description : ");
        detailsDescription.setText(description);
        detailsEmplacament.setText("Emplacement du poster: "+ emplacementPoster);
        detailsStudentsLabel.setText("Students :");
        detailsSupervisor.setText("Holder : "+ supervisor);
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

    private final View.OnClickListener onMapButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            Intent intent = new Intent(ProjectDetailsActivity.this, MapActivity.class);
            startActivityForResult(intent,CONNECTION);

        }
    };


}
