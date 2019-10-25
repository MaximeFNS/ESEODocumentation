package fr.eseo.dis.afonsodebarre.eseodocumentation.database;

import android.content.ContentValues;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.OnConflictStrategy;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import fr.eseo.dis.afonsodebarre.eseodocumentation.daos.AnnotationsDao;
import fr.eseo.dis.afonsodebarre.eseodocumentation.entities.Annotation;

@Database(entities = {Annotation.class}, version = 1, exportSchema = false)
public abstract class ESEODocumentationDatabase extends RoomDatabase {
    private static ESEODocumentationDatabase INSTANCE;



    public abstract AnnotationsDao annotationsDao();
     public static ESEODocumentationDatabase getInstance(Context context){
         if (INSTANCE == null) {
             synchronized (ESEODocumentationDatabase.class) {
                 if (INSTANCE == null) {
                     INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                             ESEODocumentationDatabase.class, "MyDatabase.db")
                             .addCallback(prepopulateDatabase())
                             .build();
                 }
             }
         }
         return INSTANCE;
     }

    private static Callback prepopulateDatabase(){
        return new Callback() {

            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);

                ContentValues contentValues = new ContentValues();

                contentValues.put("alberpat", "Ce poster est beau mais non conforme");


                db.insert("Annotation", OnConflictStrategy.IGNORE, contentValues);
            }
        };
    }


}
