package fr.eseo.dis.afonsodebarre.eseodocumentation.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.afonsodebarre.eseodocumentation.MyJuryProjects;
import fr.eseo.dis.afonsodebarre.eseodocumentation.R;
import fr.eseo.dis.afonsodebarre.eseodocumentation.TousLesProjetsActivity;

/**
 * Recycler View of the page which allows to see projects
 */
public class ProjectsRecyclerViewAdapter  extends RecyclerView.Adapter<ProjectsRecyclerViewAdapter.ProjectsRecyclerViewHolder> {

    private TousLesProjetsActivity projetsActivity;
    private MyJuryProjects myJuryProjects;

    private final List<Integer> expandedPositions;

    /**
     * Elements displayed on the cards
     */
    private List<String> projects_title;
    private List<String> projects_id;
    private List<String> projects_confid;
    private List<String> projects_description;
    private List<String> projects_poster_emplacement;
    private List<String> projects_students;
    private List<String> projects_supervisor;
    /**
     * Boolean to determined if the user wants to see all projects or only projects of his jury
     */
    private boolean inAllProjects = false;
    private boolean inMyJuryProjects = false;

    /**
     * Constructor of the class if the user wants to see all projects
     * @param projetsActivity Activity used
     */
    public ProjectsRecyclerViewAdapter(TousLesProjetsActivity projetsActivity) {
        this.projetsActivity = projetsActivity;

        projects_title= new ArrayList<>();
        projects_id = new ArrayList<>();
        projects_confid = new ArrayList<>();
        projects_description = new ArrayList<>();
        projects_poster_emplacement = new ArrayList<>();
        projects_students = new ArrayList<>();
        projects_supervisor = new ArrayList<>();
        inAllProjects = true;
        expandedPositions = new ArrayList<>();
    }

    /**
     * Constructor of the class if the user wants to see projects of his jury
     * @param myJuryProjects activity used
     */
   public ProjectsRecyclerViewAdapter(MyJuryProjects myJuryProjects) {
        this.myJuryProjects = myJuryProjects;

        projects_title= new ArrayList<>();
        projects_id = new ArrayList<>();
        projects_confid = new ArrayList<>();
        projects_description = new ArrayList<>();
        projects_poster_emplacement = new ArrayList<>();
        projects_students = new ArrayList<>();
        projects_supervisor = new ArrayList<>();
        inMyJuryProjects = true;
        expandedPositions = new ArrayList<>();
    }

    /**
     *
     * @param projects_title Title of the project
     * @param projects_description Description of the project
     * @param projects_confid Confidentiality of the project
     * @param projects_id Id of the project
     * @param projects_poster_emplacement Place of the project
     * @param projects_students Students of the project
     * @param projects_supervisor Supervisor of the project
     */
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

    /**
     *
     * @param parent ViewGroup
     * @param viewType int
     * @return ProjectsRecyclerViewHolder
     */
    @NonNull
    @Override
    public ProjectsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View projectView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_projects_item,parent,false);
        return new ProjectsRecyclerViewHolder(projectView);
    }

    /**
     *
     * @param holder ProjectsRecyclerViewHolder
     * @param position int
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final ProjectsRecyclerViewHolder holder, final int position) {

        holder.projectResume.setText(projects_description.get(position));
        holder.projectConfidentiality.setText("confid : "+projects_confid.get(position));
        holder.projectTitle.setText(projects_title.get(position));

        /*
          Change the visibility of some elements if the user use a long click on the card
         */
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
                    expandedPositions.remove(Integer.valueOf(position));
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
        /*
          User can see the details of the selected project on the clicked card
         */
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            final String id = projects_id.get(position);
            final String title = projects_title.get(position);
            final String description = projects_description.get(position);
            final String confid = projects_confid.get(position);
            final String students = projects_students.get(position);
            final String supervisor = projects_supervisor.get(position);
            final String emplacementPoster = projects_poster_emplacement.get(position);
            @Override
            public void onClick(View view) {

                if(inAllProjects){
                    projetsActivity.viewDetails(id,emplacementPoster,title,description,confid,supervisor,students);
                }
                if(inMyJuryProjects){
                    myJuryProjects.viewDetails(id,emplacementPoster,title,description,confid,supervisor,students);
                }

            }
        });
    }

    /**
     *
     * @return int
     */
    @Override
    public int getItemCount() {
        return projects_id.size();
    }

    /**
     * Class of the ProjectsRecyclerViewHolder
     */
    class ProjectsRecyclerViewHolder extends RecyclerView.ViewHolder {

        private final TextView projectTitle;
        private final TextView projectConfidentiality;
        private final TextView projectResume;
        private final TextView projectDescriptionLabel;

        private ProjectsRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            /**
             * Add elements of the layout
             */
            projectTitle = itemView.findViewById(R.id.project_titre);
            projectConfidentiality = itemView.findViewById(R.id.project_confidentiality);
            projectResume = itemView.findViewById(R.id.project_resume);
            projectDescriptionLabel = itemView.findViewById(R.id.project_description);
        }
    }


}
