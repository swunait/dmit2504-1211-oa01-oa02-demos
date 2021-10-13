package ca.nait.dmit.dmit2054.radiobuttondemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    private TextView mGameDifficultyTextView;
    private TextView mGameUsernameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        mGameDifficultyTextView = findViewById(R.id.activity_game_difficulty_textview);
        mGameUsernameTextView = findViewById(R.id.activity_game_username_textview);

        if (getIntent() != null) {
            Bundle extras = getIntent().getExtras();
            mGameDifficultyTextView.setText(extras.getString("GAME_DIFFICULTY"));
            mGameUsernameTextView.setText(extras.getString("GAME_USERNAME"));
        }
    }

}