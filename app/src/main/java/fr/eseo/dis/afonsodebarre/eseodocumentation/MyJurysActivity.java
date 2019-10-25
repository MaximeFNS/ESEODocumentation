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

/**
 * MyJurysActivity is the activity which manage the jury of the user
 */
public class MyJurysActivity extends AppCompatActivity {

    private String login,  token;
    private JuriesRecyclerViewAdapter juriesRecyclerViewAdapter;
    private static final int CONNECTION = 0;

    /**
     * Elements displayed
     */
    private final ArrayList<String> JURIES_ID = new ArrayList<>();
    private final ArrayList<String> JURIES_DATES = new ArrayList<>();
    private final ArrayList<String> JURIES_MEMBERS = new ArrayList<>();
    private final ArrayList<String> JURIES_PROJECTS = new ArrayList<>();

    /**
     * Variables used to determine later what project have been selected
     */
    private ArrayList<String> TEMPORARY = new ArrayList<>();
    private ArrayList<String> TEMPORARYTITLES = new ArrayList<>();
    private final ArrayList<ArrayList<String>> LISTOFLISTIDS = new ArrayList<>();
    private final ArrayList<ArrayList<String>> LISTOFLISTTITLES = new ArrayList<>();

    public static final String IDJURYPROJECTS = "IDJURYPROJECTS";
    public static final String JURIESTITLES = "JURIESTITLES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_jurys);
        login = getIntent().getStringExtra(LOGIN);
        token = getIntent().getStringExtra(TOKEN);

        /*
        Use of a web service which list the juries of the user
         */
        WebServiceConnectivity wsc = new WebServiceConnectivity(this);
        wsc.execute("https://192.168.4.240/pfe/webservice.php?q=MYJUR&user="+login+"&token="+token);

        try {
            String result = wsc.get();
            JSONObject jObject = new JSONObject(result);
            String resultString = jObject.getString("result");

            /*
            If result of the web service is data get values of the juries
             */
            if (resultString.equals("OK")) {

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

                    /*
                    TEMPORARYTITLES contains list of titles by project
                    LISTOFLISTIDS contains list of ids by project
                     */
                    LISTOFLISTIDS.add(TEMPORARY);
                    LISTOFLISTTITLES.add(TEMPORARYTITLES);
                    TEMPORARY = new ArrayList<>();
                    TEMPORARYTITLES = new ArrayList<>();

                }

            }

        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
        }

        /*
        Initialization of the recycler view and we send the data to it
         */
        RecyclerView juriesRecycler = findViewById(R.id.juriesList);
        juriesRecycler.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        juriesRecycler.setLayoutManager(llm);
        juriesRecyclerViewAdapter = new JuriesRecyclerViewAdapter(this);
        juriesRecycler.setAdapter(juriesRecyclerViewAdapter);
        setMJJuries();
    }

    /**
     * void setMKJuries send the data to the recycler view
     */
    private void setMJJuries(){

        juriesRecyclerViewAdapter.setJuries(JURIES_ID,JURIES_DATES, JURIES_MEMBERS, JURIES_PROJECTS, LISTOFLISTTITLES,LISTOFLISTIDS);

            }

    /**
     * void viewProjects() direct the user to MyJuryProjects.class
      * @param idProjects Ids of the project assigned to the selected jury
     */
    public void viewProjects(ArrayList<String> idProjects){
        Intent intent = new Intent(MyJurysActivity.this, MyJuryProjects.class);
        intent.putExtra(LOGIN, login);
        intent.putExtra(TOKEN,token);
        intent.putExtra(IDJURYPROJECTS,idProjects);
        startActivityForResult(intent,CONNECTION);
    }

    /**
     * void gradeProjects() direct the user to MyGradesActivity.class
     * @param idProjects Ids of the project assigned to the selected jury
     * @param titles Titles of the project assigned to the selected jury
     */
    public void gradeProjects(ArrayList<String> idProjects, ArrayList<String> titles){
        Intent intent = new Intent(MyJurysActivity.this, MyGradesActivity.class);
        intent.putExtra(LOGIN, login);
        intent.putExtra(TOKEN,token);
        intent.putExtra(IDJURYPROJECTS,idProjects);
        intent.putExtra(JURIESTITLES,titles);
        startActivityForResult(intent,CONNECTION);
    }

}
