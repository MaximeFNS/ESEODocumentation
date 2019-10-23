package fr.eseo.dis.afonsodebarre.eseodocumentation.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.afonsodebarre.eseodocumentation.MyGradesActivity;
import fr.eseo.dis.afonsodebarre.eseodocumentation.R;

public class GradesRecyclerViewAdapter extends RecyclerView.Adapter<GradesRecyclerViewAdapter.GradesRecyclerViewHolder> {

    private final MyGradesActivity gradesActivity;

    private ArrayList<String> projectstitles;
    private ArrayList<String> projectsstudents;
    private ArrayList<String> projectsmygrades;
    private ArrayList<String> projectsaverages;

    private List<Integer> expandedPositions;

    public GradesRecyclerViewAdapter(MyGradesActivity gradesActivity) {
        this.gradesActivity = gradesActivity;

        projectstitles = new ArrayList<>();
        projectsstudents = new ArrayList<>();
        projectsmygrades = new ArrayList<>();
        projectsaverages = new ArrayList<>();

        expandedPositions = new ArrayList<>();
    }

    public void setGrades(ArrayList<String> titles, ArrayList<String> students, ArrayList<String> mygrades, ArrayList<String> averages) {

        this.projectstitles = titles;
        this.projectsstudents = students;
        this.projectsmygrades = mygrades;
        this.projectsaverages = averages;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GradesRecyclerViewAdapter.GradesRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View gradesView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_grades_item,parent,false);
        return new GradesRecyclerViewAdapter.GradesRecyclerViewHolder(gradesView);
    }

    @Override
    public int getItemCount() {
        return projectstitles.size();
    }

    @Override
    public void onBindViewHolder(@NonNull final GradesRecyclerViewHolder holder, final int position) {

        holder.projecttogradetitle.setText(projectstitles.get(position));
        holder.students.setText(projectsstudents.get(position));
        holder.grades.setText(projectsmygrades.get(position));
        holder.averages.setText(projectsaverages.get(position));

    }

    class GradesRecyclerViewHolder extends RecyclerView.ViewHolder {

        private final TextView projecttogradetitle;
        private final TextView students;
        private final TextView grades;
        private final TextView averages;

        public GradesRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            projecttogradetitle = itemView.findViewById(R.id.projecttograde_title);
            students = itemView.findViewById(R.id.students);
            grades = itemView.findViewById(R.id.mygrades);
            averages = itemView.findViewById(R.id.averages);

        }
    }
}
