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
    public List<String> PROJECTS_SUPERVISOR= new ArrayList<>();
    public List<String> PROJECTS_POSTER= new ArrayList<>();
    public List<String> PROJECTS_STUDENTS = new ArrayList<>();
    private static final int CONNECTION = 0;
    public static final String IDPROJET = "IDPROJET";
    public static final String EMP = "EMP";
    public static final String DESCRPIP = "DESCRIP";
    public static final String CONFID = "CONFID";
    public static final String SUPERVISOR = "SUPERVISOR";
    public static final String STUDENTS = "STUDENTS";
    public static final String TITLE = "TITLE";
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
        projectsRecyclerViewAdapter.setProjects(PROJECTS_TITLE,PROJECTS_DESCRIPTION,PROJECTS_CONFID,PROJECTS_ID,PROJECTS_POSTER,PROJECTS_STUDENTS,PROJECTS_SUPERVISOR);
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
                    JSONObject projectObject = arrayProjects.getJSONObject(i);
                    String projectTitleString = projectObject.getString("title");
                    String projectIdString = projectObject.getString("projectId");
                    String projectConfidString = projectObject.getString("confid");
                    String projectDescriptionString = projectObject.getString("descrip");
                    String projectPosterString = projectObject.getString("poster");
                    if (projectPosterString==null){
                        projectPosterString="";
                    }
                    Log.d("TITRE PROJET", projectTitleString);
                    PROJECTS_TITLE.add(projectTitleString);
                    PROJECTS_ID.add(projectIdString);
                    PROJECTS_CONFID.add(projectConfidString);
                    PROJECTS_DESCRIPTION.add(projectDescriptionString);
                    PROJECTS_POSTER.add(projectPosterString);
                    JSONArray arrayStudents = projectObject.getJSONArray("students");
                    String studentString="";
                    for (int j=0; j< arrayStudents.length();j++){
                        JSONObject student = arrayStudents.getJSONObject(j);
                        studentString += student.getString("forename") + " " + student.getString("surname")+"\n";

                    }
                    PROJECTS_STUDENTS.add(studentString);

                    JSONObject supervisor = projectObject.getJSONObject("supervisor");
                    String supString = supervisor.getString("forename")+ " " + supervisor.getString("surname");
                    PROJECTS_SUPERVISOR.add(supString);




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

    public void viewDetails(String idProjet, String posterEmplacement, String title, String description, String confid, String supervisor, String students){
        Intent intent = new Intent(TousLesProjetsActivity.this, ProjectDetailsActivity.class);
        intent.putExtra(IDPROJET,idProjet);
        intent.putExtra(EMP,posterEmplacement);
        intent.putExtra(TITLE,title);
        intent.putExtra(DESCRPIP,description);
        intent.putExtra(CONFID,confid);
        intent.putExtra(SUPERVISOR,supervisor);
        intent.putExtra(STUDENTS,students);
        intent.putExtra(LOGIN, login);
        intent.putExtra(TOKEN,token);
        startActivityForResult(intent,CONNECTION);
    }

}
