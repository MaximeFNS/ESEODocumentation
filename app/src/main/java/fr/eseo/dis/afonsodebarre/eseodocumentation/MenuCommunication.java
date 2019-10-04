package fr.eseo.dis.afonsodebarre.eseodocumentation;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;


import androidx.appcompat.app.AppCompatActivity;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.MainActivity.LOGIN;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.MainActivity.PASSWORD;

public class MenuCommunication extends AppCompatActivity {

    private static final String TAG = "TAG";
    private static final int CONNECTION = 0;
    private String login, password;
    ImageButton projetsButton;
    ImageButton juryButton;

    public static Intent getStartIntent(final Context ctx) {
        return new Intent(ctx, MenuCommunication.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_comm_main);
        login = getIntent().getStringExtra(LOGIN);
        password = getIntent().getStringExtra(PASSWORD);
        Log.d(TAG, "log: "+ login +password);

        projetsButton = findViewById(R.id.tousProjetsImage);
        projetsButton.setOnClickListener(onProjetsButtonClicked);

        juryButton = findViewById(R.id.pseudosjurysImage);
        juryButton.setOnClickListener(onPseudosJurysButtonClicked);

    }

    private final View.OnClickListener onProjetsButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            Intent intent = new Intent(MenuCommunication.this, TousLesProjetsActivity.class);
            startActivityForResult(intent,CONNECTION);

        }
    };

    private final View.OnClickListener onPseudosJurysButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            Intent intent = new Intent(MenuCommunication.this, PseudosJurysActivity.class);
            startActivityForResult(intent,CONNECTION);

        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainActivity","onStart()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity","onStop()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity","onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivity","onPause()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity","onDestroy()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("MainActivity","onRestart()");
    }
}
