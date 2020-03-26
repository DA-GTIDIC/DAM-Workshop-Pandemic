package cat.udl.tidic.amd.dam_rol_game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cat.udl.tidic.amd.dam_rol_game.models.Events;

public class MainActivity extends AppCompatActivity {


    private static final String GAME_BEGIN_DIALOG_TAG = "game_dialog_tag";
    private static final String GAME_END_DIALOG_TAG = "game_end_dialog_tag";

    private GameViewModel gameViewModel;
    private TextView infectionTextView;
    private TextView daysTextView;
    private TextView eventTextView;
    private ImageView cardImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        infectionTextView = findViewById(R.id.textView);
        daysTextView = findViewById(R.id.textView2);
        eventTextView = findViewById(R.id.textView3);
        cardImageView = findViewById(R.id.card);


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
        eventTextView.setText(getText(R.string.EMPIEZA));
        cardImageView.setImageResource(R.drawable.virus);

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
                String msg = "Current Infection (%): " + i;
                infectionTextView.setText(msg);
            }
        });

        gameViewModel.getDay().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer i) {
                String msg = "Days: (" + i + "/" + gameViewModel.getDays() + ")";
                daysTextView.setText(msg);
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
