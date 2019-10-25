package fr.eseo.dis.afonsodebarre.eseodocumentation.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Annotation")
public class Annotation {
    @PrimaryKey(autoGenerate = true)
    public long annotationId;

    @NonNull
    @ColumnInfo(name = "userName")
    public String userName;

    public String annotation;


    @NonNull
    public String projectId;

    public String getUserName(){
        return this.userName;
    }

    public String getAnnotation(){
        return this.annotation;
    }

    public void setUserName(String username){
        this.userName=username;
    }

    public void setAnnotation(String annotation){
        this.annotation=annotation;
    }

    public String getProjectId(){ return this.projectId;}

    public void setProjectId(String projectId) { this.projectId=projectId;}

}
