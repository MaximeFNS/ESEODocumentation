package fr.eseo.dis.afonsodebarre.eseodocumentation;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import java.util.concurrent.ExecutionException;

import static fr.eseo.dis.afonsodebarre.eseodocumentation.MainActivity.LOGIN;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.MainActivity.TOKEN;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.ProjectDetailsActivity.IMAGE;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.TousLesProjetsActivity.IDPROJET;
public class PosterActivity extends AppCompatActivity {

    private String login, token, idProjet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster);
        login = getIntent().getStringExtra(LOGIN);
        token = getIntent().getStringExtra(TOKEN);
        idProjet = getIntent().getStringExtra(IDPROJET);
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

        byte[] decodedString = Base64.decode(resultat, Base64.DEFAULT);
        final Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        ImageView poster = (ImageView) findViewById(R.id.posterFull);
        poster.setImageBitmap(decodedByte);
    }
}
