package cat.udl.tidic.amd.dam_rol_game.models;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

public class Game {


    private MutableLiveData<Integer> infection;
    private MutableLiveData<Boolean>  survival;
    private MutableLiveData<Events> roundEvent;
    private MutableLiveData<Integer> cDay;

    private int days;



    public Game(MutableLiveData<Events> cEvent, MutableLiveData<Integer> cInfection,
                MutableLiveData<Integer> cDay, MutableLiveData<Boolean> survive, int days,
                int infectionRate){
        this.days = days;
        infection = cInfection;
        infection.setValue(infectionRate);
        survival = survive;
        this.cDay = cDay;
        this.cDay.setValue(0);
        roundEvent = cEvent;
    }

    public int getDays(){
        return days;
    }
    
    
    
    public void round(int diceValue){

        int day = 0;
        if ( cDay.getValue() != null){
            day = cDay.getValue() + 1;
            cDay.setValue(day);
        }

        Events cEvent = parseDiceToEvent(diceValue);
        roundEvent.setValue(cEvent);
        int cValue = Events.getEventDamage(cEvent != null ? cEvent : Events.INCONSIENCIA);
        int cSurvival = 0;

        if (infection.getValue() != null){
            cSurvival = infection.getValue() + cValue;
            if (cSurvival < 0){
                cSurvival = 0;
            }
            infection.setValue(cSurvival);
        }

        hasEnd(cSurvival,day);

    }

    private void hasEnd(int cValue, int cDay){

        if (cDay == days && cValue < 100){
            survival.setValue(true);
        }

        if (cValue >= 100){
            survival.setValue(false);
        }

    }
    
    private Events parseDiceToEvent(int diceValue){
        
        switch (diceValue){
            case 1:
                return Events.REPARTO;
            case 2:
                return Events.VACUNAS;
            case 3:
                return Events.ENCASA;
            case 4:
                return Events.FUERACASA;
            case 5:
                return Events.CEPAMUTADA;
            case 6:
                return Events.INCONSIENCIA;
            default:
                return null;
        }
        
    }




}
