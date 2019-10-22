package fr.eseo.dis.afonsodebarre.eseodocumentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import fr.eseo.dis.afonsodebarre.eseodocumentation.adapters.GradesRecyclerViewAdapter;

import static fr.eseo.dis.afonsodebarre.eseodocumentation.MainActivity.LOGIN;

import static fr.eseo.dis.afonsodebarre.eseodocumentation.MainActivity.TOKEN;
import  static fr.eseo.dis.afonsodebarre.eseodocumentation.MyJurysActivity.JURIESTITLES;
import  static fr.eseo.dis.afonsodebarre.eseodocumentation.MyJurysActivity.IDJURYPROJECTS;
public class MyGradesActivity extends AppCompatActivity {

    private static final int CONNECTION = 0;
    private static final String TAG = "MyGradesActivity";
    private String login, token;
    private ArrayList<String> titles, ids;
    private ArrayList<String> GRADES_STUDENTS = new ArrayList<>();
    private ArrayList<String> GRADES_GIVEN = new ArrayList<>();
    private ArrayList<String> GRADES_AVERAGE = new ArrayList<>();
    private GradesRecyclerViewAdapter gradesRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_grades);
        login = getIntent().getStringExtra(LOGIN);
        token = getIntent().getStringExtra(TOKEN);
        titles = getIntent().getStringArrayListExtra(JURIESTITLES);
        ids = getIntent().getStringArrayListExtra(IDJURYPROJECTS);
        for(int k=0; k<ids.size();k++){

            WebServiceConnectivity wsc = new WebServiceConnectivity(this);
            wsc.execute("https://192.168.4.240/pfe/webservice.php?q=NOTES&user="+login+"&proj="+ids.get(k)+"&token="+token);

            String resultat = null;
            try {
                resultat = wsc.get();
                JSONObject jObject = new JSONObject(resultat);
                String resultString = jObject.getString("result");
                if (resultString.equals("OK")) {
                    int sizeGrades = jObject.getJSONArray("notes").length();
                    JSONArray arrayGrades = jObject.getJSONArray("notes");
                    ArrayList<String> eleves = new ArrayList<>();
                    ArrayList<String> myg = new ArrayList<>();
                    ArrayList<String> avg = new ArrayList<>();
                    String eleve = "";
                    String allmyg = "";
                    String allavg = "";
                    for (int i = 0; i < sizeGrades; i++){

                            JSONObject gradeObject = arrayGrades.getJSONObject(i);
                            eleves.add(gradeObject.getString("forename") + " " + gradeObject.getString("surname") + "\n");
                            myg.add(gradeObject.getString("mynote")+ "\n");
                            avg.add(gradeObject.getString("avgNote")+ "\n");


                    }
                    for(int ielev = 0; ielev<eleves.size();ielev++){
                        eleve = eleve + eleves.get(ielev);
                        allmyg = allmyg + myg.get(ielev);
                        allavg = allavg + avg.get(ielev);
                    }
                    GRADES_STUDENTS.add(eleve);
                    GRADES_GIVEN.add(allmyg);
                    GRADES_AVERAGE.add(allavg);

                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        RecyclerView gradesRecycler = (RecyclerView)findViewById(R.id.gradeslist);
        gradesRecycler.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        gradesRecycler.setLayoutManager(llm);
        gradesRecyclerViewAdapter = new GradesRecyclerViewAdapter(this);
        gradesRecycler.setAdapter(gradesRecyclerViewAdapter);
        gradesRecyclerViewAdapter.setGrades(titles, GRADES_STUDENTS, GRADES_GIVEN, GRADES_AVERAGE);
    }
}
