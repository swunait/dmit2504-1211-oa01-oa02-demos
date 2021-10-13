package ca.nait.dmit.dmit2054.radiobuttondemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText mUsernameEditText;

    private RadioGroup mGameDifficultyRadioGroup;
    private String mSelectedGameDifficulty = "Normal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mUsernameEditText = findViewById(R.id.activity_main_username);
        mGameDifficultyRadioGroup = findViewById(R.id.activity_main_difficulty_radiogroup);
    }

    public void onStartGameClick(View view) {
        switch (mGameDifficultyRadioGroup.getCheckedRadioButtonId()) {
            case R.id.activity_main_easy_radiobutton:
                mSelectedGameDifficulty = "Easy";
                break;
            case R.id.activity_main_normal_radiobutton:
                mSelectedGameDifficulty = "Normal";
                break;
            case R.id.activity_main_hard_radiobutton:
                mSelectedGameDifficulty = "Hard";
                break;
        }
        String username = mUsernameEditText.getText().toString();

        if (mSelectedGameDifficulty.isEmpty() ) {
            Toast.makeText(this, "Game Difficulty is required", Toast.LENGTH_SHORT).show();
        } else if (username.isEmpty()) {
            Toast.makeText(this,"Username is required", Toast.LENGTH_SHORT).show();
        } else {
            Intent openGameActivityIntent = new Intent(this, GameActivity.class);

            Bundle extras = new Bundle();
            extras.putString("GAME_DIFFICULTY", mSelectedGameDifficulty);
            extras.putString("GAME_USERNAME", username);
            openGameActivityIntent.putExtras(extras);

            startActivity(openGameActivityIntent);
        }


    }
}