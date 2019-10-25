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
import java.util.List;
import java.util.concurrent.ExecutionException;

import fr.eseo.dis.afonsodebarre.eseodocumentation.adapters.ProjectsRecyclerViewAdapter;

import static fr.eseo.dis.afonsodebarre.eseodocumentation.MainActivity.LOGIN;

import static fr.eseo.dis.afonsodebarre.eseodocumentation.MainActivity.TOKEN;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.MyJurysActivity.IDJURYPROJECTS;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.TousLesProjetsActivity.CONFID;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.TousLesProjetsActivity.DESCRPIP;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.TousLesProjetsActivity.EMP;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.TousLesProjetsActivity.IDPROJET;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.TousLesProjetsActivity.STUDENTS;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.TousLesProjetsActivity.SUPERVISOR;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.TousLesProjetsActivity.TITLE;

public class MyJuryProjects extends AppCompatActivity {

    private String login, token;
    private ArrayList<String> idprojects;
    private final List<String> PROJECTS_TITLE = new ArrayList<>();
    private final List<String> PROJECTS_ID = new ArrayList<>();
    private final List<String> PROJECTS_CONFID = new ArrayList<>();
    private final List<String> PROJECTS_DESCRIPTION = new ArrayList<>();
    private final List<String> PROJECTS_SUPERVISOR= new ArrayList<>();
    private final List<String> PROJECTS_POSTER= new ArrayList<>();
    private final List<String> PROJECTS_STUDENTS = new ArrayList<>();
    private static final int CONNECTION = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_jury_projects);

        login = getIntent().getStringExtra(LOGIN);
        token = getIntent().getStringExtra(TOKEN);
        idprojects = getIntent().getStringArrayListExtra(IDJURYPROJECTS);

        RecyclerView projectsRecycler = findViewById(R.id.projectsList);
        projectsRecycler.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        projectsRecycler.setLayoutManager(llm);
        ProjectsRecyclerViewAdapter projectsRecyclerViewAdapter = new ProjectsRecyclerViewAdapter(this);
        projectsRecycler.setAdapter(projectsRecyclerViewAdapter);
        importProjects();
        projectsRecyclerViewAdapter.setProjects(PROJECTS_TITLE,PROJECTS_DESCRIPTION,PROJECTS_CONFID,PROJECTS_ID,PROJECTS_POSTER,PROJECTS_STUDENTS,PROJECTS_SUPERVISOR);
    }

    private void importProjects() {

        WebServiceConnectivity wsc = new WebServiceConnectivity(this);
        wsc.execute("https://192.168.4.240/pfe/webservice.php?q=LIPRJ&user=" + login + "&token=" + token);

        try {
            String result = wsc.get();
            JSONObject jObject = new JSONObject(result);
            String resultString = jObject.getString("result");

            if (resultString.equals("OK")) {

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
                    if(idprojects.contains(projectIdString)){
                        PROJECTS_TITLE.add(projectTitleString);
                        PROJECTS_ID.add(projectIdString);
                        PROJECTS_CONFID.add(projectConfidString);
                        PROJECTS_DESCRIPTION.add(projectDescriptionString);
                        PROJECTS_POSTER.add(projectPosterString);
                        JSONArray arrayStudents = projectObject.getJSONArray("students");
                        for (int j=0; j< arrayStudents.length();j++){
                            JSONObject student = arrayStudents.getJSONObject(j);
                            String studentString = student.getString("forename") + " " + student.getString("surname");
                            PROJECTS_STUDENTS.add(studentString);
                        }

                        JSONObject supervisor = projectObject.getJSONObject("supervisor");
                        String supString = supervisor.getString("forename")+ " " + supervisor.getString("surname");
                        PROJECTS_SUPERVISOR.add(supString);
                    }
                }

            }

        } catch (ExecutionException | InterruptedException | JSONException e) {
            e.printStackTrace();
        }
    }

    public void viewDetails(String idProjet, String posterEmplacement, String title, String description, String confid, String supervisor, String students){
        Intent intent = new Intent(MyJuryProjects.this, ProjectDetailsActivity.class);
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
