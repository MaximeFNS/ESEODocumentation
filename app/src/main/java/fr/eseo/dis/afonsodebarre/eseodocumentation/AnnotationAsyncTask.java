package fr.eseo.dis.afonsodebarre.eseodocumentation;

import android.app.Activity;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;

import fr.eseo.dis.afonsodebarre.eseodocumentation.database.ESEODocumentationDatabase;
import fr.eseo.dis.afonsodebarre.eseodocumentation.entities.Annotation;

public class AnnotationAsyncTask extends AsyncTask<String, String, String> {


    private String projectId;
    private String login;
    private ESEODocumentationDatabase database;
    private Annotation anno;
    private boolean submitted;
    private String updatedMessage;

    public AnnotationAsyncTask(Annotation anno, String login, String projectId, ESEODocumentationDatabase database, boolean submitted, String updatedMessage){
        this.anno = anno;
        this.login = login;
        this.projectId=projectId;
        this.database=database;
        this.submitted=submitted;
        this.updatedMessage=updatedMessage;
    }


    @Override
    protected String doInBackground(String... strings) {

        if (!submitted) {




            if (database.annotationsDao().getAnnotations(login, projectId) == null) {
                anno = new Annotation();
                anno.setUserName(login);
                anno.setProjectId(projectId);
                anno.setAnnotation("No annotation");
                database.annotationsDao().insertAnnotation(anno);
            }
        }else{
            anno.setAnnotation(updatedMessage);
            database.annotationsDao().updateAnnotation(anno);

        }

        return "OK";

    }
}
