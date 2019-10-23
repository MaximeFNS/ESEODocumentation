package fr.eseo.dis.afonsodebarre.eseodocumentation.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.afonsodebarre.eseodocumentation.MyJuryProjects;
import fr.eseo.dis.afonsodebarre.eseodocumentation.R;
import fr.eseo.dis.afonsodebarre.eseodocumentation.TousLesProjetsActivity;

import static android.content.ContentValues.TAG;

public class ProjectsRecyclerViewAdapter  extends RecyclerView.Adapter<ProjectsRecyclerViewAdapter.ProjectsRecyclerViewHolder> {

    private TousLesProjetsActivity projetsActivity;
    private MyJuryProjects myJuryProjects;

    private List<Integer> expandedPositions;

    private List<String> projects_title;
    private List<String> projects_id;
    private List<String> projects_confid;
    private List<String> projects_description;
    private List<String> projects_poster_emplacement;
    private List<String> projects_students;
    private List<String> projects_supervisor;
    private boolean inallProjects = false;
    private boolean inmyjuryprojects = false;


    public ProjectsRecyclerViewAdapter(TousLesProjetsActivity projetsActivity) {
        this.projetsActivity = projetsActivity;

        projects_title= new ArrayList<>();
        projects_id = new ArrayList<>();
        projects_confid = new ArrayList<>();
        projects_description = new ArrayList<>();
        projects_poster_emplacement = new ArrayList<>();
        projects_students = new ArrayList<>();
        projects_supervisor = new ArrayList<>();
        inallProjects = true;
        expandedPositions = new ArrayList<>();
    }




   public ProjectsRecyclerViewAdapter(MyJuryProjects myJuryProjects) {
        this.myJuryProjects = myJuryProjects;

        projects_title= new ArrayList<>();
        projects_id = new ArrayList<>();
        projects_confid = new ArrayList<>();
        projects_description = new ArrayList<>();
        projects_poster_emplacement = new ArrayList<>();
        projects_students = new ArrayList<>();
        projects_supervisor = new ArrayList<>();
        inmyjuryprojects = true;
        expandedPositions = new ArrayList<>();
    }

    public void setProjects(List<String> projects_title, List<String> projects_description, List<String> projects_confid, List<String> projects_id, List<String> projects_poster_emplacement, List<String> projects_students, List<String> projects_supervisor) {
        this.projects_title= projects_title;
        this.projects_description= projects_description;
        this.projects_confid= projects_confid;
        this.projects_id= projects_id;
        this.projects_poster_emplacement=projects_poster_emplacement;
        this.projects_supervisor = projects_supervisor;
        this.projects_students = projects_students;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public ProjectsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View projectView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_projects_item,parent,false);
        return new ProjectsRecyclerViewHolder(projectView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProjectsRecyclerViewHolder holder, final int position) {

        holder.projectResume.setText(projects_description.get(position));
        holder.projectConfidentiality.setText("confid : "+projects_confid.get(position));
        holder.projectTitle.setText(projects_title.get(position));

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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            String id = projects_id.get(position);
            String title = projects_title.get(position);
            String description = projects_description.get(position);
            String confid = projects_confid.get(position);
            String students = projects_students.get(position);
            String supervisor = projects_supervisor.get(position);
            String emplacementPoster = projects_poster_emplacement.get(position);
            @Override
            public void onClick(View view) {
                Log.d(TAG, "IDProjet : " + id);
                if(inallProjects){
                    projetsActivity.viewDetails(id,emplacementPoster,title,description,confid,supervisor,students);
                }
                if(inmyjuryprojects){
                    myJuryProjects.viewDetails(id,emplacementPoster,title,description,confid,supervisor,students);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return projects_id.size();
    }


    class ProjectsRecyclerViewHolder extends RecyclerView.ViewHolder {

        private final TextView projectTitle;
        private final TextView projectConfidentiality;
        private final TextView projectResume;
        private final TextView projectDescriptionLabel;

        public ProjectsRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            projectTitle = itemView.findViewById(R.id.project_titre);

            projectConfidentiality = itemView.findViewById(R.id.project_confidentiality);
            projectResume = itemView.findViewById(R.id.project_resume);
            projectDescriptionLabel = itemView.findViewById(R.id.project_description);
        }
    }


}
