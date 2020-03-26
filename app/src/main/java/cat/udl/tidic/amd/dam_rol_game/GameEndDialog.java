package cat.udl.tidic.amd.dam_rol_game;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class GameEndDialog extends DialogFragment {

    private View rootView;
    private MainActivity activity;
    private boolean survive;


    public static GameEndDialog newInstance(MainActivity activity, Boolean survive) {
        GameEndDialog dialog = new GameEndDialog();
        dialog.activity = activity;
        dialog.survive = survive;
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initViews();
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setView(rootView)
                .setCancelable(false)
                .setPositiveButton(R.string.close, ((dialog, which) -> onNewGame()))
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        return alertDialog;
    }

    private void initViews() {
        rootView = LayoutInflater.from(getContext())
                .inflate(R.layout.game_end_dialog, null, false);

        if (survive){
            ((TextView) rootView.findViewById(R.id.game_status_msg)).setText("Victory");
            ((ImageView) rootView.findViewById(R.id.game_status_img))
                    .setImageResource(R.drawable.victory);
        }else{
            ((TextView) rootView.findViewById(R.id.game_status_msg)).setText("Defeat");
            ((ImageView) rootView.findViewById(R.id.game_status_img))
                    .setImageResource(R.drawable.defeat);
        }

    }


    private void onNewGame() {
        activity.promptForConfiguration();
        dismiss();
    }

}
