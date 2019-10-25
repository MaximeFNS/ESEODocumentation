package fr.eseo.dis.afonsodebarre.eseodocumentation.daos;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import fr.eseo.dis.afonsodebarre.eseodocumentation.entities.Annotation;

@Dao
public interface AnnotationsDao {
    @Query("SELECT * FROM Annotation WHERE userName = :userName")
    List<Annotation> getAnnotations(String userName);

    @Update
    String updateAnnotation(Annotation annotation);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    String insertAnnotation(Annotation annotation);
}
