package fr.eseo.dis.afonsodebarre.eseodocumentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import fr.eseo.dis.afonsodebarre.eseodocumentation.adapters.ProjectsRecyclerViewAdapter;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.MainActivity.LOGIN;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.MainActivity.PASSWORD;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.MainActivity.TOKEN;




public class TousLesProjetsActivity extends AppCompatActivity {

    public List<String> PROJECTS_TITLE = new ArrayList<>();
    public List<String> PROJECTS_ID = new ArrayList<>();
    public List<String> PROJECTS_CONFID = new ArrayList<>();
    public List<String> PROJECTS_DESCRIPTION = new ArrayList<>();
    private static final int CONNECTION = 0;
    private String login, password, token;
    private static final String TAG = "TAG";
    private ProjectsRecyclerViewAdapter projectsRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tous_les_projets);
        login = getIntent().getStringExtra(LOGIN);
        password = getIntent().getStringExtra(PASSWORD);
        token = getIntent().getStringExtra(TOKEN);
        Log.d(TAG, "onCreate: "+login+password+token);
        RecyclerView projectsRecycler = (RecyclerView)findViewById(R.id.projectslist);
        projectsRecycler.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        projectsRecycler.setLayoutManager(llm);
        projectsRecyclerViewAdapter = new ProjectsRecyclerViewAdapter(this);
        projectsRecycler.setAdapter(projectsRecyclerViewAdapter);
        importProjects();
        projectsRecyclerViewAdapter.setProjects(PROJECTS_TITLE,PROJECTS_DESCRIPTION,PROJECTS_CONFID,PROJECTS_ID);
    }

    private void importProjects() {



        WebServiceConnectivity wsc = new WebServiceConnectivity(this);
        Log.d("Lancement ", "ok");
        wsc.execute("https://192.168.4.240/pfe/webservice.php?q=LIPRJ&user=" + login + "&token=" + token);
        Log.d("Execution ","ok");


        try {
            String resultat = wsc.get();
            JSONObject jObject = new JSONObject(resultat);
            String resultString = jObject.getString("result");
            Log.d("ResultString ",resultString);
            if (resultString.equals("OK")) {
                Log.d("ResultString ","ok");
                Log.d("NombreDeProjets",Integer.toString(jObject.getJSONArray("projects").length()));

                for (int i = 0; i < jObject.getJSONArray("projects").length(); i++){
                    JSONArray arrayProjects = jObject.getJSONArray("projects");
                    JSONObject projetcObject = arrayProjects.getJSONObject(0);
                    String projectTitleString = projetcObject.getString("title");
                    String projectIdString = projetcObject.getString("projectId");
                    String projectConfidString = projetcObject.getString("confid");
                    String projectDescriptionString = projetcObject.getString("descrip");

                    PROJECTS_TITLE.add(projectTitleString);
                    PROJECTS_ID.add(projectIdString);
                    PROJECTS_CONFID.add(projectConfidString);
                    PROJECTS_DESCRIPTION.add(projectDescriptionString);
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
    }


}
