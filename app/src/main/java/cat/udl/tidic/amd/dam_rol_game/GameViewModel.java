package cat.udl.tidic.amd.dam_rol_game;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import cat.udl.tidic.amd.dam_rol_game.models.Events;
import cat.udl.tidic.amd.dam_rol_game.models.Game;

public class GameViewModel extends ViewModel {

    private MutableLiveData<Events> cEvent;
    private MutableLiveData<Integer> cInfection;
    private MutableLiveData<Boolean> survive;
    private MutableLiveData<Integer> cDay;
    private Game game;


    public GameViewModel(int days, int survival_rate){
        cEvent = new MutableLiveData<>();
        cInfection = new MutableLiveData<>();
        survive = new MutableLiveData<>();
        cDay = new MutableLiveData<>();

        game = new Game(cEvent, cInfection,cDay,survive, days, survival_rate);

    }


    public  MutableLiveData<Events> getEvent(){
        return cEvent;
    }

    public  MutableLiveData<Integer>  getInfection(){
        return cInfection;
    }

    public  MutableLiveData<Boolean> getSurvival(){
        return survive;
    }

    public  MutableLiveData<Integer>  getDay(){
        return cDay;
    }

    public int getDays(){
        return game.getDays();
    }

    public void play(int diceValue){
        game.round(diceValue);
    }





}
