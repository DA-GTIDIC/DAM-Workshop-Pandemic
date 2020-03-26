package cat.udl.tidic.amd.dam_rol_game;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;


public class GameBeginDialog extends DialogFragment {

    private View rootView;
    private MainActivity activity;
    private TextView daysEditText;
    private TextView survivalEditText;



    public static GameBeginDialog newInstance(MainActivity activity) {
        GameBeginDialog dialog = new GameBeginDialog();
        dialog.activity = activity;
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initViews();
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setView(rootView)
                .setTitle(R.string.game_dialog_title)
                .setCancelable(false)
                .setPositiveButton(R.string.start, null)
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.setOnShowListener(dialog -> {
            onDialogShow(alertDialog);
        });
        return alertDialog;
    }

    private void initViews() {
        rootView = LayoutInflater.from(getContext())
                .inflate(R.layout.game_begin_dialog, null, false);

        daysEditText = (EditText) rootView.findViewById(R.id.days_survive_input);
        survivalEditText = (EditText) rootView.findViewById(R.id.infection_rate_input);

    }

    private void onDialogShow(AlertDialog dialog) {
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(v -> {
            onDoneClicked();
        });
    }

    private void onDoneClicked() {
        int days = 7;
        int survival_rate= 50;
        String cdays = daysEditText.getText().toString();
        String csurvival_rate = survivalEditText.getText().toString();

        if (!"".equals(cdays)){
            days=Integer.parseInt(cdays);
        }

        if (!"".equals(csurvival_rate)){
            survival_rate=Integer.parseInt(csurvival_rate);
        }

       activity.startGame(days, survival_rate);
        dismiss();
    }


}
