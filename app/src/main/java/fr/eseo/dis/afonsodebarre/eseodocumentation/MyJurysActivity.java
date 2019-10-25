package fr.eseo.dis.afonsodebarre.eseodocumentation;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import java.util.concurrent.ExecutionException;

import fr.eseo.dis.afonsodebarre.eseodocumentation.adapters.JuriesRecyclerViewAdapter;

import static fr.eseo.dis.afonsodebarre.eseodocumentation.MainActivity.LOGIN;

import static fr.eseo.dis.afonsodebarre.eseodocumentation.MainActivity.TOKEN;
public class MyJurysActivity extends AppCompatActivity {

    private String login,  token;
    private JuriesRecyclerViewAdapter juriesRecyclerViewAdapter;
    private final ArrayList<String> JURIES_ID = new ArrayList<>();
    private final ArrayList<String> JURIES_DATES = new ArrayList<>();
    private final ArrayList<String> JURIES_MEMBERS = new ArrayList<>();
    private final ArrayList<String> JURIES_PROJECTS = new ArrayList<>();
    private ArrayList<String> TEMPORARY = new ArrayList<>();
    private ArrayList<String> TEMPORARYTITLES = new ArrayList<>();
    private final ArrayList<ArrayList<String>> LISTOFLISTIDS = new ArrayList<>();
    private final ArrayList<ArrayList<String>> LISTOFLISTTITLES = new ArrayList<>();
    private static final int CONNECTION = 0;
    public static final String IDJURYPROJECTS = "IDJURYPROJECTS";
    public static final String JURIESTITLES = "JURIESTITLES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_jurys);
        login = getIntent().getStringExtra(LOGIN);
        token = getIntent().getStringExtra(TOKEN);

        WebServiceConnectivity wsc = new WebServiceConnectivity(this);
        wsc.execute("https://192.168.4.240/pfe/webservice.php?q=MYJUR&user="+login+"&token="+token);

        try {
            String result = wsc.get();
            JSONObject jObject = new JSONObject(result);
            String resultString = jObject.getString("result");
            Log.d("ResultString ",resultString);
            if (resultString.equals("OK")) {
                Log.d("NbJurys",Integer.toString(jObject.getJSONArray("juries").length()));
                int sizeJury = jObject.getJSONArray("juries").length();
                JSONArray arrayJuries = jObject.getJSONArray("juries");
                for (int i = 0; i < sizeJury; i++){

                    JSONObject juryObject = arrayJuries.getJSONObject(i);

                    String juryIdJury = juryObject.getString("idJury");
                    String juryDate = juryObject.getString("date");

                    JSONObject jObject2 = juryObject.getJSONObject("info");

                    JURIES_ID.add(juryIdJury);
                    JURIES_DATES.add(juryDate);

                    JSONArray arrayMembers = jObject2.getJSONArray("members");
                    JSONArray arrayProjects = jObject2.getJSONArray("projects");
                    StringBuilder profsReturn = new StringBuilder();
                    StringBuilder titleProjects = new StringBuilder("\n");

                    for (int j = 0; j<arrayMembers.length();j++){
                        JSONObject memberObject = arrayMembers.optJSONObject(j);
                        profsReturn.append(memberObject.getString("forename")).append(" ").append(memberObject.getString("surname")).append("   ");
                    }
                    JURIES_MEMBERS.add(profsReturn.toString());

                    for (int j = 0; j<arrayProjects.length();j++){
                        JSONObject projectObject = arrayProjects.optJSONObject(j);
                        titleProjects.append("- ").append(projectObject.getString("title")).append("\n");
                    }

                    JURIES_PROJECTS.add(titleProjects.toString());

                    for (int j = 0; j<arrayProjects.length();j++){
                        JSONObject projectObject = arrayProjects.optJSONObject(j);
                        TEMPORARY.add(projectObject.getString("projectId"));
                        TEMPORARYTITLES.add(projectObject.getString("title"));
                    }

                    LISTOFLISTIDS.add(TEMPORARY);
                    LISTOFLISTTITLES.add(TEMPORARYTITLES);
                    TEMPORARY = new ArrayList<>();
                    TEMPORARYTITLES = new ArrayList<>();

                }

            }

        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
        }
        RecyclerView juriesRecycler = findViewById(R.id.juriesList);
        juriesRecycler.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        juriesRecycler.setLayoutManager(llm);
        juriesRecyclerViewAdapter = new JuriesRecyclerViewAdapter(this);
        juriesRecycler.setAdapter(juriesRecyclerViewAdapter);
        setMJJuries();
    }

    private void setMJJuries(){

        juriesRecyclerViewAdapter.setJuries(JURIES_ID,JURIES_DATES, JURIES_MEMBERS, JURIES_PROJECTS, LISTOFLISTTITLES,LISTOFLISTIDS);

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
