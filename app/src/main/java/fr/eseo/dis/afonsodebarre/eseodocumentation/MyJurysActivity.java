package fr.eseo.dis.afonsodebarre.eseodocumentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import fr.eseo.dis.afonsodebarre.eseodocumentation.adapters.JuriesRecyclerViewAdapter;
import fr.eseo.dis.afonsodebarre.eseodocumentation.adapters.ProjectsRecyclerViewAdapter;

import static fr.eseo.dis.afonsodebarre.eseodocumentation.MainActivity.LOGIN;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.MainActivity.PASSWORD;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.MainActivity.TOKEN;
public class MyJurysActivity extends AppCompatActivity {

    private String login, password, token;
    private static final String TAG = "TAG";
    private JuriesRecyclerViewAdapter juriesRecyclerViewAdapter;
    private ArrayList<String> JURIES_ID = new ArrayList<>();
    private ArrayList<String> JURIES_DATES = new ArrayList<>();
    private ArrayList<String> JURIES_MEMBERS = new ArrayList<>();
    private ArrayList<String> JURIES_PROJECTS = new ArrayList<>();
    private ArrayList<String> PROJECTS_IDS = new ArrayList<>();
    private ArrayList<String> PROJECTS_TITLES = new ArrayList<>();
    private static final int CONNECTION = 0;
    public static final String IDJURYPROJECTS = "IDJURYPROJECTS";
    public static final String JURIESTITLES = "JURIESTITLES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_jurys);
        login = getIntent().getStringExtra(LOGIN);
        password = getIntent().getStringExtra(PASSWORD);
        token = getIntent().getStringExtra(TOKEN);
        Log.d(TAG, "onCreate: "+login+password+token);

        WebServiceConnectivity wsc = new WebServiceConnectivity(this);
        wsc.execute("https://192.168.4.240/pfe/webservice.php?q=MYJUR&user="+login+"&token="+token);

        try {
            String resultat = wsc.get();
            JSONObject jObject = new JSONObject(resultat);
            String resultString = jObject.getString("result");
            Log.d("ResultString ",resultString);
            if (resultString.equals("OK")) {
                Log.d("NbJurys",Integer.toString(jObject.getJSONArray("juries").length()));
                int sizeJury = jObject.getJSONArray("juries").length();
                JSONArray arrayjuries = jObject.getJSONArray("juries");
                for (int i = 0; i < sizeJury; i++){


                    JSONObject juryObject = arrayjuries.getJSONObject(i);

                    String juryIdJury = juryObject.getString("idJury");
                    String juryDate = juryObject.getString("date");
                    String juryInfo = juryObject.getString("info");

                    JSONObject jObject2 = juryObject.getJSONObject("info");


                    Log.d("ID Jury "+ i,juryIdJury);
                    Log.d("Date Jury "+ i,juryDate);
                    Log.d("Jury Info "+ i,juryInfo);

                    JURIES_ID.add(juryIdJury);
                    JURIES_DATES.add(juryDate);

                    JSONArray arraymembers = jObject2.getJSONArray("members");
                    JSONArray arrayprojects = jObject2.getJSONArray("projects");
                    String profsRetour = "";
                    String titleprojects = "\n";

                    for (int j = 0; j<arraymembers.length();j++){
                        JSONObject memberObject = arraymembers.optJSONObject(j);
                        profsRetour = profsRetour + memberObject.getString("forename") +" "+memberObject.getString("surname") + "   ";
                    }
                    JURIES_MEMBERS.add(profsRetour);

                    for (int j = 0; j<arrayprojects.length();j++){
                        JSONObject projectObject = arrayprojects.optJSONObject(j);
                        titleprojects = titleprojects + "- " + projectObject.getString("title") + "\n";
                    }

                    JURIES_PROJECTS.add(titleprojects);

                    for (int j = 0; j<arrayprojects.length();j++){
                        JSONObject projectObject = arrayprojects.optJSONObject(j);
                        PROJECTS_IDS.add(projectObject.getString("projectId"));
                        PROJECTS_TITLES.add(projectObject.getString("title"));
                    }


                }

            } else {

            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();

        }

        RecyclerView juriesRecycler = (RecyclerView)findViewById(R.id.jurieslist);
        juriesRecycler.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        juriesRecycler.setLayoutManager(llm);
        juriesRecyclerViewAdapter = new JuriesRecyclerViewAdapter(this);
        juriesRecycler.setAdapter(juriesRecyclerViewAdapter);
        setMJJuries();
    }

    private void setMJJuries(){
        Log.d(TAG, "setMJJuries: " + PROJECTS_IDS.size());
        Log.d(TAG, "setMJJuries: " + PROJECTS_IDS.toString());
        juriesRecyclerViewAdapter.setJuries(JURIES_ID,JURIES_DATES, JURIES_MEMBERS, JURIES_PROJECTS, PROJECTS_IDS, PROJECTS_TITLES);

            }

    public void viewProjects(ArrayList<String> idProjects){
        Intent intent = new Intent(MyJurysActivity.this, MyJuryProjects.class);
        intent.putExtra(LOGIN, login);
        intent.putExtra(TOKEN,token);
        intent.putExtra(IDJURYPROJECTS,idProjects);
        startActivityForResult(intent,CONNECTION);
    }

    public void gradeProjects(ArrayList<String> idProjects, ArrayList<String> titles){
        Intent intent = new Intent(MyJurysActivity.this, MyGradesActivity.class);
        intent.putExtra(LOGIN, login);
        intent.putExtra(TOKEN,token);
        intent.putExtra(IDJURYPROJECTS,idProjects);
        intent.putExtra(JURIESTITLES,titles);
        startActivityForResult(intent,CONNECTION);
    }

}
