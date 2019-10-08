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
    private List<Integer> expandedPositions = new ArrayList<>();



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
    public void onBindViewHolder(@NonNull final ProjectsRecyclerViewHolder holder, final int position) {
        if(expandedPositions.contains(position)){
            holder.projectResume.setVisibility(View.VISIBLE);
            holder.projectDescriptionLabel.setVisibility(View.VISIBLE);
        }
        else{
            holder.projectResume.setVisibility(View.GONE);
            holder.projectDescriptionLabel.setVisibility(View.GONE);
        }
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(holder.projectResume.getVisibility()==View.VISIBLE){
                    expandedPositions.remove(new Integer(position));
                    holder.projectResume.setVisibility(View.GONE);
                    holder.projectDescriptionLabel.setVisibility(View.GONE);
                }
                else{
                    expandedPositions.add(position);
                    holder.projectResume.setVisibility(View.VISIBLE);
                    holder.projectDescriptionLabel.setVisibility(View.VISIBLE);
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

        private final TextView projectTitle;
        private final TextView projectID;
        private final TextView projectConfidentiality;
        private final TextView projectResume;
        private final TextView projectDescriptionLabel;

        public ProjectsRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            projectTitle = itemView.findViewById(R.id.project_titre);
            projectID = itemView.findViewById(R.id.project_id);
            projectConfidentiality = itemView.findViewById(R.id.project_confidentiality);
            projectResume = itemView.findViewById(R.id.project_resume);
            projectDescriptionLabel = itemView.findViewById(R.id.project_description);
        }
    }


}
