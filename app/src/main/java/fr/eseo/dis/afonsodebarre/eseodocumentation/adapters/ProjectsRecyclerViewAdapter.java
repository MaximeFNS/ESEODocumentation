package fr.eseo.dis.afonsodebarre.eseodocumentation.adapters;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.afonsodebarre.eseodocumentation.R;
import fr.eseo.dis.afonsodebarre.eseodocumentation.TousLesProjetsActivity;

public class ProjectsRecyclerViewAdapter  extends RecyclerView.Adapter<ProjectsRecyclerViewAdapter.ProjectsRecyclerViewHolder> {

    private final TousLesProjetsActivity projetsActivity;

    private List<Integer> projectinfos;

    //TODO: This field will be deleted
    private float radius;

    public ProjectsRecyclerViewAdapter(TousLesProjetsActivity projetsActivity) {
        this.projetsActivity = projetsActivity;
        //TODO: The following lines will be repalaced
        projectinfos = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            projectinfos.add(i);
        }
        //TODO: End of the code to be replaced
    }

    @NonNull
    @Override
    public ProjectsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View filmView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_projects_item,parent,false);
        //TODO: The following lines will be deleted
        ((CardView)filmView).setRadius(radius);
        radius += 10;
        //TODO: End of code to be deleted
        return new ProjectsRecyclerViewHolder(filmView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProjectsRecyclerViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return projectinfos.size();
    }

    class ProjectsRecyclerViewHolder extends RecyclerView.ViewHolder {

        public ProjectsRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
