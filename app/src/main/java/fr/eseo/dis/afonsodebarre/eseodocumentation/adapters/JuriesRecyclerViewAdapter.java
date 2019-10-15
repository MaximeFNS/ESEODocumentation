package fr.eseo.dis.afonsodebarre.eseodocumentation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fr.eseo.dis.afonsodebarre.eseodocumentation.MyJurysActivity;
import fr.eseo.dis.afonsodebarre.eseodocumentation.R;
import fr.eseo.dis.afonsodebarre.eseodocumentation.TousLesProjetsActivity;

public class JuriesRecyclerViewAdapter extends RecyclerView.Adapter<JuriesRecyclerViewAdapter.JuriesRecyclerViewHolder> {

    private final MyJurysActivity juriesActivity;

    private List<String> juriesIds;
    private List<String> juriesDates;
    private List<String> juriesMembers;

    private List<Integer> expandedPositions;

    public JuriesRecyclerViewAdapter(MyJurysActivity juriesActivity) {
        this.juriesActivity = juriesActivity;

        juriesIds = new ArrayList<>();
        juriesDates = new ArrayList<>();
        juriesMembers = new ArrayList<>();

        expandedPositions = new ArrayList<>();
    }

    public void setJuries(ArrayList<String> juriesIds, ArrayList<String> juriesDates) {
        this.juriesIds = juriesIds;
        this.juriesDates = juriesDates;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public JuriesRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View juriesView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_juries_item,parent,false);
        return new JuriesRecyclerViewHolder(juriesView);
    }

    @Override
    public void onBindViewHolder(@NonNull final JuriesRecyclerViewHolder holder, final int position) {
        holder.juryID.setText(juriesIds.get(0));
        holder.juryDate.setText(juriesDates.get(0));


        if(expandedPositions.contains(position)){
            holder.juryProjects.setVisibility(View.VISIBLE);
            holder.juryMembers.setVisibility(View.VISIBLE);
        }
        else{
            holder.juryProjects.setVisibility(View.GONE);
            holder.juryMembers.setVisibility(View.GONE);
        }
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(holder.juryProjects.getVisibility()==View.VISIBLE){
                    expandedPositions.remove(new Integer(position));
                    holder.juryProjects.setVisibility(View.GONE);
                    holder.juryMembers.setVisibility(View.GONE);
                }
                else{
                    expandedPositions.add(position);
                    holder.juryProjects.setVisibility(View.VISIBLE);
                    holder.juryMembers.setVisibility(View.VISIBLE);
                }
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return juriesIds.size();
    }


    class JuriesRecyclerViewHolder extends RecyclerView.ViewHolder {

        private final TextView juryTitle;
        private final TextView juryID;
        private final TextView juryDate;
        private final TextView juryMembers;
        private final TextView juryProjects;

        public JuriesRecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            juryTitle = itemView.findViewById(R.id.jury_title);
            juryID = itemView.findViewById(R.id.jury_id);
            juryDate = itemView.findViewById(R.id.jury_date);
            juryMembers = itemView.findViewById(R.id.juries_members);
            juryProjects = itemView.findViewById(R.id.juries_projects);
        }
    }


}
