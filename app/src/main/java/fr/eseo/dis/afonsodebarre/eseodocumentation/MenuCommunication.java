package fr.eseo.dis.afonsodebarre.eseodocumentation;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;


import androidx.appcompat.app.AppCompatActivity;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.MainActivity.LOGIN;
public class MenuCommunication extends AppCompatActivity {

    private String login;

    public static Intent getStartIntent(final Context ctx) {
        return new Intent(ctx, MenuCommunication.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_comm_main);
        login = getIntent().getStringExtra(LOGIN);

    }

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
