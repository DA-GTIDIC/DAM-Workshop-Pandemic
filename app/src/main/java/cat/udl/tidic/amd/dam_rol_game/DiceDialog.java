package cat.udl.tidic.amd.dam_rol_game;

import android.app.AlertDialog;
import android.app.Dialog;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
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

    ImageView dice_picture;     //reference to dice picture
    Handler handler;            //Post message to start roll
    Timer timer=new Timer();    //Used to implement feedback to user
    boolean rolling=false;      //Is die rolling?
    RotateAnimation rotate;


    public static DiceDialog newInstance(MainActivity activity) {
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
                .setMessage("Clickar al dau per tirar:")
                .setPositiveButton("close", null)
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        return alertDialog;

    }

    public void initView(){

        diceViewModel = new DiceViewModel();

        rootView = LayoutInflater.from(getContext())
                .inflate(R.layout.dice, null, false);


        //Get a reference to image widget
        dice_picture = (ImageView) rootView.findViewById(R.id.imageView);
        dice_picture.setOnClickListener(new HandleClick());
        rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(3000);
        rotate.setInterpolator(new LinearInterpolator());

        //link handler to callback
        handler=new Handler(callback);

        diceViewModel.imageRes.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer value) {
                dice_picture.setImageResource(value);
            }
        });

    }

    //User pressed dice, lets start
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


    Handler.Callback callback = new Handler.Callback() {
        public boolean handleMessage(Message msg) {
            diceViewModel.roll();
            rolling=false;
            rotate.cancel();
            return true;
        }
    };


    //Clean up
    public void onPause() {
        super.onPause();
    }

    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

}
