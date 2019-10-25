package fr.eseo.dis.afonsodebarre.eseodocumentation;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageButton;


import androidx.appcompat.app.AppCompatActivity;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.MainActivity.LOGIN;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.MainActivity.TOKEN;

/**
 * MenuCommunication is the menu of the members of communication
 */
public class MenuCommunication extends AppCompatActivity {

    private static final int CONNECTION = 0;
    private String login, token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_comm_main);
        /*
        Get the token & the login of the user
         */
        login = getIntent().getStringExtra(LOGIN);
        token = getIntent().getStringExtra(TOKEN);

        /*
        Display the imageButton of projects
         */
        ImageButton projectButton = findViewById(R.id.allProjectsImage);
        projectButton.setOnClickListener(onProjetsButtonClicked);

    }

    /**
     * If the image Button for projects is selected, the user is directed to TousLesProjetsActivity
     */
    private final View.OnClickListener onProjetsButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            Intent intent = new Intent(MenuCommunication.this, TousLesProjetsActivity.class);
            intent.putExtra(LOGIN, login);
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
