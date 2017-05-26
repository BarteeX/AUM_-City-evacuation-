package sample.structure.logic;

import java.util.Random;

/**
 * Created by Paweł on 19.05.2017.
 */
public class Genotype {

    public float stamina;   //0.5 = 50hp, 1 = 100 hp
    public float iLikeWindows; //0 = I hate windows, 1 = I love windows <3
    public float iLikeDoors; //---
    public float iLikeSafeZone; //---
    public float calm; //0 = Chill and relax, 1 = I'm panic as fuck
    public float iDontLikeSmoke; //1 = I dont' really like some, 0 = I'm a vaper!
    public float iDontLikeFire; //1 = Stay cool, 0 = Burn motherf*cker
    public float laziness;


    public Genotype(){
        Random rand = new Random();
        do{
            stamina = rand.nextFloat() + 0.5f;
        }while(stamina > 1f);
        iLikeWindows = rand.nextFloat();
        iLikeDoors = rand.nextFloat();
        iLikeSafeZone = rand.nextFloat();
        calm = rand.nextFloat();
        iDontLikeSmoke = rand.nextFloat();
        iDontLikeFire = rand.nextFloat();
        laziness = rand.nextFloat();
    }

}
