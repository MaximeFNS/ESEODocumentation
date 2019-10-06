package fr.eseo.dis.afonsodebarre.eseodocumentation.adapters;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.afonsodebarre.eseodocumentation.R;
import fr.eseo.dis.afonsodebarre.eseodocumentation.TousLesProjetsActivity;

public class ProjectsRecyclerViewAdapter  extends RecyclerView.Adapter<ProjectsRecyclerViewAdapter.ProjectsRecyclerViewHolder> {

    private final TousLesProjetsActivity projetsActivity;

    private List<Integer> projectinfos;


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

        View projectView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_projects_item,parent,false);
        return new ProjectsRecyclerViewHolder(projectView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProjectsRecyclerViewHolder holder, int position) {
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(holder.filmSynopsis.getVisibility()==View.VISIBLE){

                    holder.filmSynopsis.setVisibility(View.GONE);
                    holder.filmSynopsisLabel.setVisibility(View.GONE);
                }
                else{

                    holder.filmSynopsis.setVisibility(View.VISIBLE);
                    holder.filmSynopsisLabel.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return projectinfos.size();
    }


    class ProjectsRecyclerViewHolder extends RecyclerView.ViewHolder {

        private final TextView filmTitre;
        private final TextView filmGenre;
        private final TextView filmAnnee;
        private final TextView filmSynopsis;
        private final TextView filmSynopsisLabel;

        public ProjectsRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            filmTitre = itemView.findViewById(R.id.filmography_titre);
            filmGenre = itemView.findViewById(R.id.filmography_genre);
            filmAnnee = itemView.findViewById(R.id.filmography_annee);
            filmSynopsis = itemView.findViewById(R.id.filmography_resume);
            filmSynopsisLabel = itemView.findViewById(R.id.filmography_synopsis);
        }
    }


}
