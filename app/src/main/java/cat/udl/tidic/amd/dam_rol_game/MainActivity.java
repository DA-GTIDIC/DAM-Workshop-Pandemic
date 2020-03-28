package cat.udl.tidic.amd.dam_rol_game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.progresviews.ProgressWheel;

import java.util.ArrayList;

import cat.udl.tidic.amd.dam_rol_game.models.Events;
import params.com.stepprogressview.StepProgressView;

public class MainActivity extends AppCompatActivity {


    private static final String GAME_BEGIN_DIALOG_TAG = "game_dialog_tag";
    private static final String GAME_END_DIALOG_TAG = "game_end_dialog_tag";

    private GameViewModel gameViewModel;
    private TextView daysTextView;
    private TextView eventTextView;
    private ImageView cardImageView;
    private StepProgressView gameProgress;
    private ProgressWheel gameInfectionProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //infectionTextView = findViewById(R.id.textView);
        daysTextView = findViewById(R.id.textView2);
        eventTextView = findViewById(R.id.textView3);
        cardImageView = findViewById(R.id.card);
        gameProgress = findViewById(R.id.stepProgressView);
        gameInfectionProgress = findViewById(R.id.infection_progress);


        Button roll = findViewById(R.id.roll);

        roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiceDialog dialog = DiceDialog.newInstance(MainActivity.this);
                dialog.show(getSupportFragmentManager(), GAME_END_DIALOG_TAG);
            }
        });

        promptForConfiguration();
    }


    public void setEvent(int diceVal){
        gameViewModel.play(diceVal);
    }

    public void startGame(int days, int survival_rate){
        gameViewModel = new GameViewModel(days, survival_rate);
        eventTextView.setText(getText(R.string.BEGINS));
        cardImageView.setImageResource(R.drawable.virus);
        gameProgress.setCurrentProgress(0);

        ArrayList<Integer> markers = new ArrayList<>();
        markers.add(gameViewModel.getDays()/2);
        gameProgress.setMarkers(markers);
        gameProgress.setTotalProgress(gameViewModel.getDays());

        gameInfectionProgress.setDefText("Infection");
        gameInfectionProgress.setStepCountText(survival_rate + "%");
        gameInfectionProgress.setPercentage(survival_rate);

        gameViewModel.getEvent().observe(this, new Observer<Events>() {
            @Override
            public void onChanged(Events e) {

                int msgRes = Events.getMessageResource(e);
                int imgRes = Events.getCardResource(e);
                eventTextView.setText(getText(msgRes));
                cardImageView.setImageResource(imgRes);
            }
        });

        gameViewModel.getInfection().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer i) {
                gameInfectionProgress.setStepCountText(i + "%");
                gameInfectionProgress.setPercentage(i);
            }
        });

        gameViewModel.getDay().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer i) {
                String msg = "Days: (" + i + "/" + gameViewModel.getDays() + ")";
                daysTextView.setText(msg);
                gameProgress.setCurrentProgress(i);
            }
        });

        gameViewModel.getSurvival().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean survive) {
                GameEndDialog dialog = GameEndDialog.newInstance(MainActivity.this, survive);
                dialog.show(getSupportFragmentManager(), "Gam");
            }
        });
    }

    public void promptForConfiguration(){
        GameBeginDialog dialog = GameBeginDialog.newInstance(this);
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), GAME_BEGIN_DIALOG_TAG);
    }


}
