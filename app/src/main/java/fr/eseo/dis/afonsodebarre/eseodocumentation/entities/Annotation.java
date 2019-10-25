package fr.eseo.dis.afonsodebarre.eseodocumentation.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Annotation")
public class Annotation {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "userName")
    public String userName;

    public String annotation;

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
}
