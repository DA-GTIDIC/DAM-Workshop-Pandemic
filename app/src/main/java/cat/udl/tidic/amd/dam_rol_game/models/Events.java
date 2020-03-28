package cat.udl.tidic.amd.dam_rol_game.models;

import java.util.Random;

import cat.udl.tidic.amd.dam_rol_game.R;

public enum Events {
    MEDICINES,
    VACCINES,
    LOCKUP,
    PEOPLE_OUT,
    MUTATION,
    UNCONSCIOUSNESS;

    public static Events getRandomEvent() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }

    public static int getMessageResource(Events e){

        switch(e){

            case MEDICINES:
                return R.string.MEDICINES;
            case LOCKUP:
                return R.string.LOCKUP;
            case VACCINES:
                return R.string.VACCINES;
            case PEOPLE_OUT:
                return R.string.PEOPLE_OUT;
            case MUTATION:
                return R.string.MUTATION;
            case UNCONSCIOUSNESS:
                return R.string.UNCONSCIOUSNESS;
            default:
                return -1;
        }

    }

    public static int getCardResource(Events e){

        switch(e){

            case MEDICINES:
                return R.drawable.medicines;
            case LOCKUP:
                return R.drawable.lockup;
            case VACCINES:
                return R.drawable.vaccines;
            case PEOPLE_OUT:
                return R.drawable.people_out;
            case MUTATION:
                return R.drawable.mutation;
            case UNCONSCIOUSNESS:
                return R.drawable.unconsiousness;
            default:
                return -1;
        }

    }

    public static int getEventDamage(Events e){
        Random random = new Random();

        switch(e){

            case MEDICINES:
                return -(random.nextInt((5 - 1) +1 ) + 1);
            case LOCKUP:
                return -5;
            case VACCINES:
                return -(random.nextInt((20 - 10) +1 ) + 10);
            case PEOPLE_OUT:
                return 15;
            case MUTATION:
                return 25;
            case UNCONSCIOUSNESS:
                return (random.nextInt((70 - 60) +1 ) + 60);
            default:
                return -1;
        }

    }


}
