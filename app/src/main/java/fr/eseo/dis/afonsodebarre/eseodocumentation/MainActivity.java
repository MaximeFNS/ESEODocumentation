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
import android.widget.TextView;
import org.json.JSONObject;
import org.json.*;
import java.io.BufferedReader;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    public static final String LOGIN = "LOGIN";
    public static final String PASSWORD = "PASSWORD";
    private static final int CONNECTION = 0;
    private static final String TAG = "TAG";
    private Context context = this;
    private BufferedReader bf;
    private EditText login;
    private EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button connect = (Button)findViewById(R.id.bn_connect);
        connect.setEnabled(false);
        login = (EditText)findViewById(R.id.user_login);
        password = (EditText)findViewById(R.id.user_password);
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
                                        WebServiceConnectivity wsc = new WebServiceConnectivity(context);
                                        wsc.execute("https://192.168.4.240/pfe/webservice.php?q=LOGON&user="+login.getText()+"&pass="+password.getText());

                                        try {
                                            String resultat = wsc.get();
                                            JSONObject jObject = new JSONObject(resultat);
                                            String aJsonString = jObject.getString("result");

                                            if(aJsonString.equals("OK")){
                                                Intent intent = new Intent(MainActivity.this, MenuJury.class);
                                                intent.putExtra(LOGIN,login.getText().toString());
                                                intent.putExtra(PASSWORD,password.getText().toString());
                                                startActivityForResult(intent,CONNECTION);

                                            }

                                        } catch (ExecutionException e) {
                                            e.printStackTrace();
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        //Intent intent = new Intent(MainActivity.this, MenuCommunication.class);
                                        //startActivity(intent);

                                    }
                                });

                                /*if (login.getText().toString().equals("jpo") && password.getText().toString().equals("jpo")){

                                    connect.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent(MainActivity.this, MenuCommunication.class);
                                            intent.putExtra(LOGIN,login.getText().toString());
                                            intent.putExtra(PASSWORD,password.getText().toString());
                                            startActivityForResult(intent,CONNECTION);
                                            //Intent intent = new Intent(MainActivity.this, MenuCommunication.class);
                                            //startActivity(intent);

                                        }
                                    });

                                } else if (login.getText().toString().equals("jury") && password.getText().toString().equals("jury")){

                                    connect.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            Intent intent = new Intent(MainActivity.this, MenuJury.class);
                                            intent.putExtra(LOGIN,login.getText().toString());
                                            intent.putExtra(PASSWORD,password.getText().toString());
                                            startActivityForResult(intent,CONNECTION);
                                            //Intent intent = new Intent(MainActivity.this, MenuCommunication.class);
                                            //startActivity(intent);

                                        }
                                    });

                                }*/

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
