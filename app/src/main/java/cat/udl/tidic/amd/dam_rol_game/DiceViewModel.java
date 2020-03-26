package cat.udl.tidic.amd.dam_rol_game;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Random;

import cat.udl.tidic.amd.dam_rol_game.R;

public class DiceViewModel extends ViewModel {

    public MutableLiveData<Integer> imageRes;
    private Random rnd;


    public DiceViewModel(){
        imageRes = new MutableLiveData<Integer>();
        rnd = new Random();
    }

    void roll(){

        int cValue = rnd.nextInt(6) + 1;
        imageRes.setValue(getDiceResource(cValue));

    }

    private int getDiceResource(int dice){
        switch(dice) {
            case 1:
                return R.drawable.one;
            case 2:
                return R.drawable.two;
            case 3:
                return R.drawable.three;
            case 4:
                return R.drawable.four;
            case 5:
                return R.drawable.five;
            case 6:
                return R.drawable.six;
            default:
                return -1;
        }
    }



}
