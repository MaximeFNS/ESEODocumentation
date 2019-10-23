package fr.eseo.dis.afonsodebarre.eseodocumentation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.MainActivity.LOGIN;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.MainActivity.PASSWORD;
import static fr.eseo.dis.afonsodebarre.eseodocumentation.MainActivity.TOKEN;
public class MenuJury extends AppCompatActivity {

  private static final int CONNECTION = 0;
  ImageButton reviewButton;
  ImageButton juryButton;
  ImageButton projectButton;
  ImageButton gradeButton;

  private String login, password, token;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_menu_jury);
    login = getIntent().getStringExtra(LOGIN);
    password = getIntent().getStringExtra(PASSWORD);
    token = getIntent().getStringExtra(TOKEN);

    projectButton = findViewById(R.id.tousProjetsImage);
    projectButton.setOnClickListener(onProjetsButtonClicked);

    juryButton = findViewById(R.id.pseudosjurysImage);
    juryButton.setOnClickListener(onPseudosJurysButtonClicked);

    /*gradeButton = findViewById(R.id.gradeImage);
    gradeButton.setOnClickListener(onGradeButtonClicked);

    reviewButton = findViewById(R.id.reviewImage);
    reviewButton.setOnClickListener(onReviewButtonClicked);*/
  }

  private final View.OnClickListener onProjetsButtonClicked = new View.OnClickListener() {
    @Override
    public void onClick(final View view) {
      Intent intent = new Intent(MenuJury.this, TousLesProjetsActivity.class);
      intent.putExtra(LOGIN, login);
      intent.putExtra(PASSWORD, password);
      intent.putExtra(TOKEN, token);
      startActivityForResult(intent,CONNECTION);

    }
  };

  private final View.OnClickListener onPseudosJurysButtonClicked = new View.OnClickListener() {
    @Override
    public void onClick(final View view) {
      Intent intent = new Intent(MenuJury.this, MyJurysActivity.class);
      intent.putExtra(LOGIN, login);
      intent.putExtra(PASSWORD, password);
      intent.putExtra(TOKEN, token);
      startActivityForResult(intent,CONNECTION);

    }
  };

  private final View.OnClickListener onGradeButtonClicked = new View.OnClickListener() {
    @Override
    public void onClick(final View view) {
      Intent intent = new Intent(MenuJury.this, MyGradesActivity.class);
      startActivityForResult(intent,CONNECTION);

    }
  };

  private final View.OnClickListener onReviewButtonClicked = new View.OnClickListener() {
    @Override
    public void onClick(final View view) {
      Intent intent = new Intent(MenuJury.this, MyReviewsActivity.class);
      intent.putExtra(LOGIN, login);
      intent.putExtra(PASSWORD, password);
      intent.putExtra(TOKEN, token);
      startActivityForResult(intent,CONNECTION);

    }
  };

  @Override
  protected void onStart() {
    super.onStart();
    Log.d("MainActivity","onStart()");
  }

  @Override
  protected void onStop() {
    super.onStop();
    Log.d("MainActivity","onStop()");
  }

  @Override
  protected void onResume() {
    super.onResume();
    Log.d("MainActivity","onResume()");
  }

  @Override
  protected void onPause() {
    super.onPause();
    Log.d("MainActivity","onPause()");
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    Log.d("MainActivity","onDestroy()");
  }

  @Override
  protected void onRestart() {
    super.onRestart();
    Log.d("MainActivity","onRestart()");
  }
}