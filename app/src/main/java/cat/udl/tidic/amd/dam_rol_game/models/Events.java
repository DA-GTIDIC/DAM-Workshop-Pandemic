package cat.udl.tidic.amd.dam_rol_game.models;

import java.util.Random;

import cat.udl.tidic.amd.dam_rol_game.R;

public enum Events {
    REPARTO,
    VACUNAS,
    ENCASA,
    FUERACASA,
    CEPAMUTADA,
    INCONSIENCIA;

    public static Events getRandomEvent() {
        Random random = new Random();
        return values()[random.nextInt(values().length)];
    }

    public static int getMessageResource(Events e){

        switch(e){

            case REPARTO:
                return R.string.REPARTO;
            case ENCASA:
                return R.string.ENCASA;
            case VACUNAS:
                return R.string.VACUNAS;
            case FUERACASA:
                return R.string.FUERACASA;
            case CEPAMUTADA:
                return R.string.CEPAMUTADA;
            case INCONSIENCIA:
                return R.string.INCONSIENCIA;
            default:
                return -1;
        }

    }

    public static int getCardResource(Events e){

        switch(e){

            case REPARTO:
                return R.drawable.reparto;
            case ENCASA:
                return R.drawable.casa;
            case VACUNAS:
                return R.drawable.vacunas;
            case FUERACASA:
                return R.drawable.fuera;
            case CEPAMUTADA:
                return R.drawable.cepa;
            case INCONSIENCIA:
                return R.drawable.inconcienca;
            default:
                return -1;
        }

    }

    public static int getEventDamage(Events e){
        Random random = new Random();

        switch(e){

            case REPARTO:
                return -(random.nextInt((5 - 1) +1 ) + 1);
            case ENCASA:
                return -5;
            case VACUNAS:
                return -(random.nextInt((20 - 10) +1 ) + 10);
            case FUERACASA:
                return 15;
            case CEPAMUTADA:
                return 25;
            case INCONSIENCIA:
                return (random.nextInt((70 - 60) +1 ) + 60);
            default:
                return -1;
        }

    }


}
