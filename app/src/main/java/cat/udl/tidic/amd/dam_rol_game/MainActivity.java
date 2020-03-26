package cat.udl.tidic.amd.dam_rol_game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button roll = findViewById(R.id.roll);

        roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DiceDialog dialog = DiceDialog.newInstance(MainActivity.this);
                dialog.show(getSupportFragmentManager(), "DICE");
            }
        });
    }

}
