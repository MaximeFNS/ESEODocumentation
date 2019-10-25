package fr.eseo.dis.afonsodebarre.eseodocumentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import org.json.*;

import java.util.concurrent.ExecutionException;

/**
 * Main Activity is the login activity of the project
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Elements which will be send to other activities
     */
    public static final String LOGIN = "LOGIN";
    public static final String TOKEN = "TOKEN";

    private static final int CONNECTION = 0;
    private final Context context = this;

    /**
     * Variable of the login and the password
     */
    private EditText login;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button connect = findViewById(R.id.bn_connect);
        connect.setEnabled(false);
        login = findViewById(R.id.user_login);
        password = findViewById(R.id.user_password);
        /*
        The goals is to be able to click on the button connection only if login and password contain text
         */
        login.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if(login.getText()!=null && !login.getText().toString().equals("")){
                    password.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void afterTextChanged(Editable editable) {
                            if(password.getText()!=null && !password.getText().toString().equals("")){
                                connect.setEnabled(true);


                                connect.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        loginUser();

                                    }
                                });

                            }
                            else{
                                connect.setEnabled(false);
                            }
                        }
                    });

                }
                else{
                    connect.setEnabled(false);
                }
            }
        });

    }

    /**
     * void loginUser() try to connect to the app with the login & the password given
     */
    private void loginUser() {

        /*
         * Execution of the web service to login
         */
        WebServiceConnectivity wsc = new WebServiceConnectivity(context);
        wsc.execute("https://192.168.4.240/pfe/webservice.php?q=LOGON&user="+login.getText()+"&pass="+password.getText());

        try {
            /*
            Get the Json result of the web service
             */
            String result = wsc.get();
            JSONObject jObject = new JSONObject(result);
            String resultString = jObject.getString("result");
            String tokenString = jObject.getString("token");
            WebServiceConnectivity wsc2 = new WebServiceConnectivity(context);
            /*
            If the login & the password are correct we use an other web service to verify if the user is a member of communication.
            The user is directed to the next activity according to the result.
             */
            if(resultString.equals("OK")){
                wsc2.execute("https://192.168.4.240/pfe/webservice.php?q=PORTE&user="+login.getText()+"&token="+tokenString);
                result = wsc2.get();
                jObject = new JSONObject(result);
                resultString = jObject.getString("result");
                if(resultString.equals("OK")) {
                    Intent intent = new Intent(MainActivity.this, MenuCommunication.class);
                    intent.putExtra(LOGIN, login.getText().toString());
                    intent.putExtra(TOKEN, tokenString);
                    startActivityForResult(intent, CONNECTION);
                }else{
                    Intent intent = new Intent(MainActivity.this, MenuJury.class);
                    intent.putExtra(LOGIN, login.getText().toString());
                    intent.putExtra(TOKEN, tokenString);
                    startActivityForResult(intent, CONNECTION);
                }
            }

        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
        }
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