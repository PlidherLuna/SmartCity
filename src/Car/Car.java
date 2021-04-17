package Car;

import java.util.Random;

/**
 * Le auto sono generate casualmente con una capacità di generare 456milioni di auto con targhe distinte secondo il formato AA001AA.
 * Ogni auto ha una categoria (benzina, diesel, gas o elettrica) che indica l'ammontare di inquinamento che può produrre.
 */
public class Car {
    private final int PETROL = 0;
    private final int DIESEL = 1;
    private final int GAS = 2;
    private final int ELECTRIC = 3;
    private int category; //Indica la categoria dell'auto ovvero Diesel o Benzina in quando una inquina piu' dell'altra
    private static String plate = null; //targa
    private static Integer numGenerator = 0; //contatore cifre targa
    private static String firstLetters = "AA"; //contatore lettere iniziali
    private static String lastLetters = "AA"; //contatore lettere finali


    public Car(){
        Random rn = new Random();
        int randomNum = 1 + rn.nextInt(100);
        if(randomNum > 0 && randomNum <= 50) this.category = PETROL;
        else if(randomNum > 50 && randomNum <= 80) this.category = DIESEL;
        else if(randomNum > 80 && randomNum <= 95) this.category = GAS;
        else this.category = ELECTRIC;
        numGenerator++;
        int plateDifference = 3 - numGenerator.toString().length();
        //Incremento il generatore se le cifre della targa sono 3
        if(numGenerator > 99 && numGenerator < 998){
            plate = firstLetters + numGenerator + lastLetters;
        }
        //Inserisco tanti 0 per quante sono le cifre per un totale di 3 cifre
        else if(plateDifference > 0) {
            String zeros = "";
            for(int i = 0; i<plateDifference; i++)
                zeros = zeros + "0";
            plate = firstLetters + zeros + numGenerator + lastLetters;
        }
        //Al raggiungimento del numero 998 cambio l'ultima cifra e resetto il generatore
        else if(lastLetters.charAt(1) >= 'A' && lastLetters.charAt(1) < 'Z'){
            char newLet = lastLetters.charAt(1);
            newLet++;
            lastLetters = lastLetters.substring(0,1) + newLet;
            plate = firstLetters + numGenerator + lastLetters;
            numGenerator = 0;
        }
        //Se l'ultima lettera raggiunge la Z cambio la penultima e resetto l'ultima e generatore
        else if(lastLetters.charAt(0) >= 'A' && lastLetters.charAt(0) < 'Z'){
            char newLet = lastLetters.charAt(0);
            newLet++;
            lastLetters = newLet + "A";
            plate = firstLetters + numGenerator + lastLetters;
            numGenerator = 0;
        }
        //Se la penultima lettera raggiunge la Z cambio la 2a lettera e resetto le ultime 2 lettere ed il generatore
        else if(firstLetters.charAt(1) >= 'A' && firstLetters.charAt(1) < 'Z'){
            char newLet = firstLetters.charAt(1);
            newLet++;
            firstLetters = firstLetters.substring(0,1) + newLet;
            lastLetters = "AA";
            plate = firstLetters + numGenerator + lastLetters;
            numGenerator = 0;
        }
        //Se la seconda lettera raggiunge la Z cambio la prima e resetto le altre lettere ed il generatore
        else if(firstLetters.charAt(0) >= 'A' && firstLetters.charAt(0) < 'Z'){
            char newLet = firstLetters.charAt(0);
            newLet++;
            firstLetters = newLet + "A";
            lastLetters = "AA";
            plate = firstLetters + numGenerator + lastLetters;
            numGenerator = 0;
        }
    }

    public int getCategory() {
        return category;
    }

    public String getPlate() {
        return plate;
    }
}
