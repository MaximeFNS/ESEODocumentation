package fr.eseo.dis.afonsodebarre.eseodocumentation.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.afonsodebarre.eseodocumentation.MyGradesActivity;
import fr.eseo.dis.afonsodebarre.eseodocumentation.R;

public class GradesRecyclerViewAdapter extends RecyclerView.Adapter<GradesRecyclerViewAdapter.GradesRecyclerViewHolder> {

    private final MyGradesActivity gradesActivity;

    private ArrayList<String> projectsTitles;
    private ArrayList<String> projectsStudents;
    private ArrayList<String> projectsMyGrades;
    private ArrayList<String> projectsAverages;
    private ArrayList<ArrayList<String>> listNames;
    private ArrayList<String> listNamesProject = new ArrayList<>();
    private ArrayList<ArrayList<String>> listIds;
    private ArrayList<String> listIdsProject = new ArrayList<>();

    private ArrayList<Integer> idsProjets;
    private Integer idOfTheProject;
    private final List<Integer> expandedPositions;

    public GradesRecyclerViewAdapter(MyGradesActivity gradesActivity) {
        this.gradesActivity = gradesActivity;

        projectsTitles = new ArrayList<>();
        projectsStudents = new ArrayList<>();
        projectsMyGrades = new ArrayList<>();
        projectsAverages = new ArrayList<>();
        listNames = new ArrayList<>();
        listIds = new ArrayList<>();
        idsProjets = new ArrayList<>();
        expandedPositions = new ArrayList<>();
    }

    public void setGrades(ArrayList<String> titles, ArrayList<String> students,
                          ArrayList<String> myGrades, ArrayList<String> averages, ArrayList<ArrayList<String>> names,
                          ArrayList<ArrayList<String>> ids, ArrayList<Integer> idsProjets) {

        this.projectsTitles = titles;
        this.projectsStudents = students;
        this.projectsMyGrades = myGrades;
        this.projectsAverages = averages;
        listNames = names;
        listIds = ids;
        this.idsProjets = idsProjets;
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
        return projectsTitles.size();
    }

    @Override
    public void onBindViewHolder(@NonNull final GradesRecyclerViewHolder holder, final int position) {

        holder.projectToGradeTitle.setText(projectsTitles.get(position));
        holder.students.setText(projectsStudents.get(position));
        holder.grades.setText(projectsMyGrades.get(position));
        holder.averages.setText(projectsAverages.get(position));

        if(expandedPositions.contains(position)){
            holder.np1.setVisibility(View.VISIBLE);
            holder.np2.setVisibility(View.VISIBLE);
            holder.button.setVisibility(View.VISIBLE);
        }
        else{
            holder.np1.setVisibility(View.GONE);
            holder.np2.setVisibility(View.GONE);
            holder.button.setVisibility(View.GONE);
        }
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {
                if(holder.np1.getVisibility()==View.VISIBLE){
                    expandedPositions.remove(new Integer(position));
                    holder.np1.setVisibility(View.GONE);
                    holder.np2.setVisibility(View.GONE);
                    holder.button.setVisibility(View.GONE);
                }
                else{
                    expandedPositions.add(position);
                    listNamesProject = listNames.get(position);
                    listIdsProject = listIds.get(position);
                    idOfTheProject = idsProjets.get(position);

                    holder.Majnp2(listNamesProject.size());
                    holder.np1.setVisibility(View.VISIBLE);
                    holder.np2.setVisibility(View.VISIBLE);
                    holder.button.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });

    }

    class GradesRecyclerViewHolder extends RecyclerView.ViewHolder {

        private final TextView projectToGradeTitle;
        private final TextView students;
        private final TextView grades;
        private final TextView averages;
        private final NumberPicker np1;
        private final NumberPicker np2;
        private final Button button;

        private void Majnp2 (int size){
            np2.setMaxValue(size);
            String[] valuesNames = new String[listNamesProject.size()];
            for(int s = 0; s<listNamesProject.size();s++){
                valuesNames[s] = listNamesProject.get(s);
            }
            np2.setDisplayedValues(valuesNames);
        }

        private GradesRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            projectToGradeTitle = itemView.findViewById(R.id.projectToGrade_title);
            students = itemView.findViewById(R.id.students);
            grades = itemView.findViewById(R.id.myGrades);
            averages = itemView.findViewById(R.id.averages);
            button = itemView.findViewById(R.id.buttonAddGrade);

            np1 = itemView.findViewById(R.id.numberPickerNote);
            np1.setMinValue(0);
            np1.setMaxValue(20);
            np1.setWrapSelectorWheel(true);

            np2 = itemView.findViewById(R.id.numberPickerStudent);
            np2.setMinValue(1);
            np2.setMaxValue(1);
            np2.setWrapSelectorWheel(true);

            final int[] incetu = new int[2];
            incetu[1] = 1;
            np1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @SuppressLint("LongLogTag")
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                    incetu[0] = newVal;
                }
            });

            np2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @SuppressLint("LongLogTag")
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                    incetu[1] = newVal;
                }
            });

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    gradesActivity.sendNote(Integer.valueOf(listIdsProject.get(incetu[1]-1)), incetu[0], idOfTheProject);
                }
            });

        }
    }
}
