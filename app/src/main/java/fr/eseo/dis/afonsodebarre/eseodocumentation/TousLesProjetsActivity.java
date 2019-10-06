package fr.eseo.dis.afonsodebarre.eseodocumentation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import fr.eseo.dis.afonsodebarre.eseodocumentation.adapters.ProjectsRecyclerViewAdapter;

public class TousLesProjetsActivity extends AppCompatActivity {

    private ProjectsRecyclerViewAdapter projectsRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tous_les_projets);
        RecyclerView projectsRecycler = (RecyclerView)findViewById(R.id.projectslist);
        projectsRecycler.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(RecyclerView.VERTICAL);
        projectsRecycler.setLayoutManager(llm);
        projectsRecyclerViewAdapter = new ProjectsRecyclerViewAdapter(this);
        projectsRecycler.setAdapter(projectsRecyclerViewAdapter);

    }
}
