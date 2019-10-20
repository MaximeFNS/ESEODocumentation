package fr.eseo.dis.afonsodebarre.eseodocumentation;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutionException;

import static fr.eseo.dis.afonsodebarre.eseodocumentation.MainActivity.LOGIN;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.MainActivity.TOKEN;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.TousLesProjetsActivity.IDPROJET;
public class ProjectDetailsActivity extends AppCompatActivity {

    private String login, token, idProjet;
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
}