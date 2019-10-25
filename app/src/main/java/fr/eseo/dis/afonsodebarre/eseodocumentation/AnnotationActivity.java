package fr.eseo.dis.afonsodebarre.eseodocumentation;

import androidx.appcompat.app.AppCompatActivity;
import fr.eseo.dis.afonsodebarre.eseodocumentation.database.ESEODocumentationDatabase;
import fr.eseo.dis.afonsodebarre.eseodocumentation.entities.Annotation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static fr.eseo.dis.afonsodebarre.eseodocumentation.MainActivity.LOGIN;
public class AnnotationActivity extends AppCompatActivity {

    private String login, projectId;
    private ESEODocumentationDatabase database;
    private EditText message;
    private Annotation anno;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation);
        login = getIntent().getStringExtra(LOGIN);
        projectId = getIntent().getStringExtra("IDPROJECT");
        database = database.getInstance(this);
        message = findViewById(R.id.annotation);


        AnnotationAsyncTask aat = new AnnotationAsyncTask(anno,login,projectId,database,false,"");
        aat.execute();

        //message.setText(anno.getAnnotation());
        message.setText("This is an annotation");







        Button submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(onSubmitButtonClicked);




    }

    private final View.OnClickListener onSubmitButtonClicked = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            EditText updatedMessage = findViewById(R.id.annotation);
            AnnotationAsyncTask aat2 = new AnnotationAsyncTask(anno,login,projectId,database,true,updatedMessage.getText().toString());
            aat2.execute();




        }
    };


}
