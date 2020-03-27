package cat.udl.tidic.amd.dam_rol_game;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;

import java.util.Timer;
import java.util.TimerTask;

public class DiceDialog extends DialogFragment {

    private View rootView;
    private DiceViewModel diceViewModel;
    private MainActivity activity;

    private ImageView dice_picture;
    private Handler handler;
    private Timer timer=new Timer();
    private boolean rolling=false;
    private RotateAnimation rotate;


    static DiceDialog newInstance(MainActivity activity) {
        DiceDialog dialog = new DiceDialog();
        dialog.activity = activity;
        return dialog;
    }



    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        initView();
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                .setView(rootView)
                .setCancelable(true)
                .setMessage(R.string.start_dice)
                .setPositiveButton(R.string.close, null)
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setOnShowListener(dialog -> {
            onDialogShow(alertDialog);
        });
        return alertDialog;

    }


    private void initView(){

        diceViewModel = new DiceViewModel();

        rootView = LayoutInflater.from(getContext())
                .inflate(R.layout.dice, null, false);

        // Init the dice animation
        configAnimation();

        //link handler to callback
        handler=new Handler(callback);

        // Observe the live data, once the dice is rolled to change the view
        diceViewModel.imageRes.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer value) {
                dice_picture.setImageResource(value);
            }
        });

    }

    private void configAnimation(){
        dice_picture = (ImageView) rootView.findViewById(R.id.imageView);
        dice_picture.setOnClickListener(new HandleClick());
        rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(3000);
        rotate.setInterpolator(new LinearInterpolator());
    }



    private class HandleClick implements View.OnClickListener {
        public void onClick(View arg0) {
            if (!rolling) {
                rolling = true;
                dice_picture.setImageResource(R.drawable.dice3d);
                dice_picture.startAnimation(rotate);
                timer.schedule(new Roll(), 3000);
            }
        }
    }


    class Roll extends TimerTask {
        public void run() {
            handler.sendEmptyMessage(0);
        }
    }


    private Handler.Callback callback = new Handler.Callback() {
        public boolean handleMessage(@NonNull Message msg) {
            diceViewModel.roll();
            rolling=false;
            rotate.cancel();
            return true;
        }
    };

    private void onDialogShow(AlertDialog dialog) {
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setOnClickListener(v -> {
            onDoneClicked();
        });
    }

    private void onDoneClicked() {

        int diceVal = diceViewModel.getDiceValue();

        if (diceVal > 0 &&  diceVal <= 6 ) {
            activity.setEvent(diceVal);
            dismiss();
        }
    }

}
