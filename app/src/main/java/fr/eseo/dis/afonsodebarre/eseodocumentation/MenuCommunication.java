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
import static fr.eseo.dis.afonsodebarre.eseodocumentation.MainActivity.TOKEN;

public class MenuCommunication extends AppCompatActivity {

    private static final int CONNECTION = 0;
    private String login, password, token;
    ImageButton projectButton;

    public static Intent getStartIntent(final Context ctx) {
        return new Intent(ctx, MenuCommunication.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_comm_main);
        login = getIntent().getStringExtra(LOGIN);
        password = getIntent().getStringExtra(PASSWORD);
        token = getIntent().getStringExtra(TOKEN);

        projectButton = findViewById(R.id.tousProjetsImage);
        projectButton.setOnClickListener(onProjetsButtonClicked);

    }

    private final View.OnClickListener onProjetsButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            Intent intent = new Intent(MenuCommunication.this, TousLesProjetsActivity.class);
            intent.putExtra(LOGIN, login);
            intent.putExtra(PASSWORD, password);
            intent.putExtra(TOKEN, token);
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
