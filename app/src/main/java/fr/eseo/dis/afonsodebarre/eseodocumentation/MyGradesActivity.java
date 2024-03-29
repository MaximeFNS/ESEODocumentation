package fr.eseo.dis.afonsodebarre.eseodocumentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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

/**
 * MyGradesActivity is the activity used to manage the grades of the students
 */
public class MyGradesActivity extends AppCompatActivity {

    private String login, token;

    /**
     * Variables which contains all the grades data
     */
    private final ArrayList<String> GRADES_STUDENTS = new ArrayList<>();
    private final ArrayList<String> GRADES_GIVEN = new ArrayList<>();
    private final ArrayList<String> GRADES_AVERAGE = new ArrayList<>();
    private final ArrayList<Integer> idProjet = new ArrayList<>();

    /**
     * Variables used to determine later what project have been selected
     */
    private ArrayList<String> TEMPORARYNAMES = new ArrayList<>();
    private final ArrayList<ArrayList<String>> LISTOFLISTNAMES = new ArrayList<>();

    private ArrayList<String> TEMPORARYIDS = new ArrayList<>();
    private final ArrayList<ArrayList<String>> LISTOFLISTIDS = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_grades);
        login = getIntent().getStringExtra(LOGIN);
        token = getIntent().getStringExtra(TOKEN);
        ArrayList<String> titles = getIntent().getStringArrayListExtra(JURIESTITLES);
        ArrayList<String> ids = getIntent().getStringArrayListExtra(IDJURYPROJECTS);
        /*
        For each jury within the user is a member
         */
        for(int k = 0; k< ids.size(); k++){

            /*
            Use of a web service get the grades
             */
            WebServiceConnectivity wsc = new WebServiceConnectivity(this);
            wsc.execute("https://192.168.4.240/pfe/webservice.php?q=NOTES&user="+login+"&proj="+ ids.get(k)+"&token="+token);

            idProjet.add(Integer.valueOf(ids.get(k)));

            String result;
            try {
                result = wsc.get();
                JSONObject jObject = new JSONObject(result);
                String resultString = jObject.getString("result");
                /*
                If the result is ok
                 */
                if (resultString.equals("OK")) {
                    int sizeGrades = jObject.getJSONArray("notes").length();
                    JSONArray arrayGrades = jObject.getJSONArray("notes");

                    ArrayList<String> students = new ArrayList<>();
                    ArrayList<String> myg = new ArrayList<>();
                    ArrayList<String> avg = new ArrayList<>();

                    StringBuilder eleve = new StringBuilder();
                    StringBuilder allmyg = new StringBuilder();
                    StringBuilder allavg = new StringBuilder();
                   /*
                   For each students of a project, we get data of their grades
                    */
                    for (int i = 0; i < sizeGrades; i++){

                            JSONObject gradeObject = arrayGrades.getJSONObject(i);
                            students.add(gradeObject.getString("forename") + " " + gradeObject.getString("surname") + "\n");
                            myg.add(gradeObject.getString("mynote")+ "\n");
                            avg.add(gradeObject.getString("avgNote")+ "\n");
                            TEMPORARYNAMES.add(gradeObject.getString("forename") + " " + gradeObject.getString("surname"));
                            TEMPORARYIDS.add(gradeObject.getString("userId"));

                    }

                    /*
                    LISTOFLISTNAMES contains list of names by project
                    LISTOFLISTIDS contains list of ids by project
                     */
                    LISTOFLISTNAMES.add(TEMPORARYNAMES);
                    LISTOFLISTIDS.add(TEMPORARYIDS);
                    TEMPORARYNAMES = new ArrayList<>();
                    TEMPORARYIDS = new ArrayList<>();

                    for(int index = 0; index<students.size();index++){
                        eleve.append(students.get(index));
                        allmyg.append(myg.get(index));
                        allavg.append(avg.get(index));
                    }
                    GRADES_STUDENTS.add(eleve.toString());
                    GRADES_GIVEN.add(allmyg.toString());
                    GRADES_AVERAGE.add(allavg.toString());

                }
            } catch (ExecutionException | InterruptedException | JSONException e) {
                e.printStackTrace();
            }

        }

        /*
        Initialization of the recycler view and we send the data to it
         */
        RecyclerView gradesRecycler = findViewById(R.id.gradesList);
        gradesRecycler.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        gradesRecycler.setLayoutManager(llm);
        GradesRecyclerViewAdapter gradesRecyclerViewAdapter = new GradesRecyclerViewAdapter(this);
        gradesRecycler.setAdapter(gradesRecyclerViewAdapter);
        gradesRecyclerViewAdapter.setGrades(titles, GRADES_STUDENTS, GRADES_GIVEN, GRADES_AVERAGE, LISTOFLISTNAMES, LISTOFLISTIDS, idProjet);
    }

    /**
     *
     * @param idStudent id of the student
     * @param note grade of the student
     * @param idProject Id of the project
     */
    public void sendNote(int idStudent, int note, int idProject){
        WebServiceConnectivity wsc = new WebServiceConnectivity(this);
        wsc.execute("https://192.168.4.240/pfe/webservice.php?q=NEWNT&user="+login+"&proj="+idProject+
                "&student="+idStudent+"&note="+note+"&token="+token);

        finish();
        startActivity(getIntent());
    }
}
