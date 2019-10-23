package fr.eseo.dis.afonsodebarre.eseodocumentation.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.afonsodebarre.eseodocumentation.MyGradesActivity;
import fr.eseo.dis.afonsodebarre.eseodocumentation.R;

public class GradesRecyclerViewAdapter extends RecyclerView.Adapter<GradesRecyclerViewAdapter.GradesRecyclerViewHolder> {

    private final MyGradesActivity gradesActivity;
    private static final String TAG = "GradesRecyclerViewAda";
    private ArrayList<String> projectstitles;
    private ArrayList<String> projectsstudents;
    private ArrayList<String> projectsmygrades;
    private ArrayList<String> projectsaverages;
    private ArrayList<ArrayList<String>> listNames;
    ArrayList<String> listNamesProject = new ArrayList<>();


    private List<Integer> expandedPositions;

    public GradesRecyclerViewAdapter(MyGradesActivity gradesActivity) {
        this.gradesActivity = gradesActivity;

        projectstitles = new ArrayList<>();
        projectsstudents = new ArrayList<>();
        projectsmygrades = new ArrayList<>();
        projectsaverages = new ArrayList<>();
        listNames = new ArrayList<>();
        expandedPositions = new ArrayList<>();
    }

    public void setGrades(ArrayList<String> titles, ArrayList<String> students,
                          ArrayList<String> mygrades, ArrayList<String> averages, ArrayList<ArrayList<String>> names) {

        this.projectstitles = titles;
        this.projectsstudents = students;
        this.projectsmygrades = mygrades;
        this.projectsaverages = averages;
        listNames = names;
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
        holder.positionGrade = position;
        Log.d(TAG, "PositionGr B : " + position);

        if(expandedPositions.contains(position)){
            holder.np1.setVisibility(View.VISIBLE);
            holder.np2.setVisibility(View.VISIBLE);
        }
        else{
            holder.np1.setVisibility(View.GONE);
            holder.np2.setVisibility(View.GONE);
        }
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {
                if(holder.np1.getVisibility()==View.VISIBLE){
                    expandedPositions.remove(new Integer(position));
                    holder.np1.setVisibility(View.GONE);
                    holder.np2.setVisibility(View.GONE);
                }
                else{
                    expandedPositions.add(position);
                    holder.positionGrade = position;
                    listNamesProject = listNames.get(position);
                    Log.d(TAG, "onLongClick: " + listNamesProject.toString());
                    holder.np1.setVisibility(View.VISIBLE);
                    holder.np2.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });

    }

    class GradesRecyclerViewHolder extends RecyclerView.ViewHolder {

        private final TextView projecttogradetitle;
        private final TextView students;
        private final TextView grades;
        private final TextView averages;
        private final NumberPicker np1;
        private final NumberPicker np2;
        private int positionGrade;



        public GradesRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);

            projecttogradetitle = itemView.findViewById(R.id.projecttograde_title);
            students = itemView.findViewById(R.id.students);
            grades = itemView.findViewById(R.id.mygrades);
            averages = itemView.findViewById(R.id.averages);

            np1 = (NumberPicker) itemView.findViewById(R.id.numberpickernote);
            np1.setMinValue(0);
            np1.setMaxValue(20);
            np1.setWrapSelectorWheel(true);

            np2 = (NumberPicker) itemView.findViewById(R.id.numberpickeretu);
            np2.setMinValue(0);
            Log.d(TAG, "PositionGr A : " + positionGrade);
             listNames.get(positionGrade);
            np2.setMaxValue(listNamesProject.size());

            //np2.setDisplayedValues( new String[] { "", "France", "United Kingdom" } );
            np1.setWrapSelectorWheel(true);

            np1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @SuppressLint("LongLogTag")
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal){

                }
            });

            np2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @SuppressLint("LongLogTag")
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal){

                }
            });

        }
    }
}
