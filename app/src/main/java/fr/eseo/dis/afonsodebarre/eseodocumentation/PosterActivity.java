package fr.eseo.dis.afonsodebarre.eseodocumentation;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;

import android.widget.ImageView;

import java.util.concurrent.ExecutionException;

import static fr.eseo.dis.afonsodebarre.eseodocumentation.MainActivity.LOGIN;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.MainActivity.TOKEN;

import static fr.eseo.dis.afonsodebarre.eseodocumentation.TousLesProjetsActivity.IDPROJET;

/**
 * PosterActivity display a selected poster in full version
 */
public class PosterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poster);
        String login = getIntent().getStringExtra(LOGIN);
        String token = getIntent().getStringExtra(TOKEN);
        String idProjet = getIntent().getStringExtra(IDPROJET);

        /*
        Use of web service to get the poster in base64 format.
         */
        WebServiceConnectivity wsc = new WebServiceConnectivity(this);
        wsc.execute("https://192.168.4.240/pfe/webservice.php?q=POSTR&user="+ login +"&proj="+ idProjet +"&style=FLB64&token="+ token);
        String result = "";
        try {
            result = wsc.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        /*
        Result in base 64 is converted to an image
         */
        byte[] decodedString = Base64.decode(result, Base64.DEFAULT);
        final Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        ImageView poster = findViewById(R.id.posterFull);
        poster.setImageBitmap(decodedByte);
    }
}
