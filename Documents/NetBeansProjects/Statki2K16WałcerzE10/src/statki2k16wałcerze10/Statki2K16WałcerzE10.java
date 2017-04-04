package statki2k16wałcerze10;

import java.util.Scanner;
import java.util.Arrays;
import java.util.Random;

public class Statki2K16WałcerzE10 {
    
    public static void main(String[] args) {
        
        switch(opcjeMenu()){
            case "1" :
                System.out.println("Welcome in single player mode!");
                
                pvc();
                
                break;
            case "2" :
                System.out.println("Welcome in multi player mode!");
                pvp();
                break;
            case "3" :
                System.out.println("Welcome in Instruction :)");
                inst();
                System.out.println("Have a nice play! :)");
                main(args);
                break;
            default :
                System.err.println("Hey man, you have to choose 1, 2 or 3. You have no another opctions!");
                main(args);
                break;
        }
        
                System.out.println();
                System.out.println("Do you want to play again?");
                System.out.print("(Y)es or (N)ot : ");

                Scanner scan = new Scanner(System.in);
                String choise = scan.nextLine();
                
                if (choise == "Y") main(args);
                else System.exit(0);
    }
    
    /**
     * Funkcja wypisuje opcje menu
     * @return - zwraca opcje z menu
     */
    public static String opcjeMenu(){
        System.out.println("Menu:");
        System.out.println("1. Player vs Computer");
        System.out.println("2. Player vs Player");
        System.out.println("3. Instuctions.");
        Scanner scan = new Scanner(System.in);
        String wybor;
        wybor = scan.nextLine();
        return wybor;
    }
    
    /**
     * Funkcja tworzy tablice i wypisuje ją defaultowymi znakami
     * @return - gotą tablice do grania
     */
    public static String[][] tworzenieMapy(){
        String[][] plansza = new String[12][12];
        for (int i = 0; i < 12; i++){
            for (int j = 0; j < 12; j++){
                if (i == 0 || i == 11){
                    plansza[i][j] = String.valueOf(j);
                }
                else {
                    if (j == 0 || j == 11)
                    {
                        int x = 64 + i;
                        plansza[i][j] = Character.toString((char)(64 + i)); 
                    }
                    else plansza[i][j] = "O";
                }
                if(i == 0 && j == 0 || i == 11 && j == 11 || i == 0 && j == 11 || i == 11 && j == 0)plansza[0][0] = "X";
            }
        }
        plansza[0][11] = 
        plansza[11][0] = 
        plansza[11][11] = "X";
        return plansza;
    }
    
    /**
     * wypisuje plansze po prostu xd
     * @param plansza 
     */
    public static void wypiszPlansza(String[][] plansza){
        System.out.println();
        for (String[] plansza1 : plansza) {
            System.out.println(Arrays.toString(plansza1));
        }
    }
    
    /**
     * wypisuje plansze czerwona po prostu xd
     * @param plansza 
     */
    public static void wypiszPlanszaCZ(String[][] plansza){
        System.out.println();
        String ANSI_RED = "\u001B[31m";
        for (String[] plansza1 : plansza) {
            System.out.println( ANSI_RED + Arrays.toString(plansza1) + ANSI_RED);
        }
    }
        
    /**
     * wypisuje instrukcje
     */
    public static void wypiszInst(){
        System.out.println();
        System.out.println("This is your board where you may to put ships");
        System.out.println("There are 4. one square ships,");
        System.out.println("and 3. dwo squeres ships,");
        System.out.println("and 2. three squeres ships,");
        System.out.println("at least single four squeres ship.");
        System.out.println("Finalay you have to choose point of start four squeres ship and the direction of it.");
    }
    
    /**
     * Zmusze do wybrania Litery pomiędzy A a J
     * @return zwraca wybraną literę 
     */
    public static String wspLit(){
        System.out.println();
        System.out.print("Choose from A to J: ");
        Scanner scan = new Scanner(System.in);
        String wspolrzednaLit;
        wspolrzednaLit = scan.nextLine();

        while( !"A".equals(wspolrzednaLit) && !"B".equals(wspolrzednaLit) && !"C".equals(wspolrzednaLit) && !"D".equals(wspolrzednaLit) && !"E".equals(wspolrzednaLit) && !"F".equals(wspolrzednaLit) && !"G".equals(wspolrzednaLit) && !"H".equals(wspolrzednaLit) && !"I".equals(wspolrzednaLit) && !"J".equals(wspolrzednaLit)) {
            System.out.println();
            System.out.println("Man you have to choose corectly!");
            System.out.println("A - J");
            wspolrzednaLit = scan.nextLine();
            }
        return wspolrzednaLit;
    }
    
    /**
     * losuje litere od A do J 
     * @return zwraca te litere
     */
    public static String losowaWspLit(){
        Random losowa = new Random();
        
        char wspLit = (char)(losowa.nextInt(10) + 65);
        String SwspLit = null;
        SwspLit = "" + wspLit;
         
        return SwspLit;        
    }
    
    /**
     * Zmusza do wybrania Cyfry od 1 do 10
     * @return zwraca te cyfre
     */
    public static String wspCyf(){
        System.out.println();
        System.out.print("Choose from 1  to 10 : ");
        Scanner scan = new Scanner(System.in);
        String wspolrzednaCyfr = "0";
        wspolrzednaCyfr = scan.nextLine();

        while( !"1".equals(wspolrzednaCyfr) && !"2".equals(wspolrzednaCyfr) && !"3".equals(wspolrzednaCyfr) && !"4".equals(wspolrzednaCyfr) &&  !"5".equals(wspolrzednaCyfr) &&  !"6".equals(wspolrzednaCyfr) && !"7".equals(wspolrzednaCyfr) && !"8".equals(wspolrzednaCyfr) && !"9".equals(wspolrzednaCyfr) && !"10".equals(wspolrzednaCyfr)) {
            System.out.println();
            System.out.println("Man you have to choose corectly!");
            System.out.println("1 - 10");
            wspolrzednaCyfr = scan.nextLine();
        }
        return wspolrzednaCyfr;
    }
    
    /**
     * generuje losową liczbę od 1 do 10 jako string 
     * @return 
     */
    public static String losowaWspCyf(){
        Random losowa = new Random();
        char wspCyf = (char)(losowa.nextInt(10) + 49);
        String SwspCyf;
        
        if (wspCyf == 58) SwspCyf = "10";
        else SwspCyf = "" + wspCyf;
        
        return SwspCyf;
    }
    
    /**
     * Funkcja wykonuje sprawdzenie poprawnosci danych wejsciowych przez użytkownika oraz 
     * możliwość postawienie w tym miejscu gwaizdku 
     * @param plansza - pobiera plansze żeby sprawdzić czy może w tym miejscu postawić klocek 
     * @return tablica dwu elementowa
     */
    public static String[] obieWspolrzedne (String[][] plansza){
        String []wspolrzedne = new String[2];
        do{
            wspolrzedne[0] = wspLit();// PRZY POMOCY FUNKCJI POBIERA PIERWSZA WSPÓŁŻĘDNĄ 
            wspolrzedne[1] = wspCyf(); // A TU DRUGA 
        }while("*".equals(plansza[Integer.valueOf(wspolrzedne[0].charAt(0)) - 64][Integer.valueOf(wspolrzedne[1])]) || "*".equals(plansza[Integer.valueOf(wspolrzedne[0].charAt(0)) - 64 + 1][Integer.valueOf(wspolrzedne[1])]) || "*".equals(plansza[Integer.valueOf(wspolrzedne[0].charAt(0)) - 64 - 1][Integer.valueOf(wspolrzedne[1])]) || "*".equals(plansza[Integer.valueOf(wspolrzedne[0].charAt(0)) - 64][Integer.valueOf(wspolrzedne[1]) + 1]) || "*".equals(plansza[Integer.valueOf(wspolrzedne[0].charAt(0)) - 64][Integer.valueOf(wspolrzedne[1]) - 1]) || "*".equals(plansza[Integer.valueOf(wspolrzedne[0].charAt(0)) - 64 + 1][Integer.valueOf(wspolrzedne[1]) + 1]) || "*".equals(plansza[Integer.valueOf(wspolrzedne[0].charAt(0)) - 64 + 1][Integer.valueOf(wspolrzedne[1]) - 1]) || "*".equals(plansza[Integer.valueOf(wspolrzedne[0].charAt(0)) - 64 - 1][Integer.valueOf(wspolrzedne[1]) + 1]) || "*".equals(plansza[Integer.valueOf(wspolrzedne[0].charAt(0)) - 64 - 1][Integer.valueOf(wspolrzedne[1]) - 1]) );
            // @up TEN WARUNEK JEST TROSZKE POJEBANY ALE OGARNIA CZY MOŻNA POSTAWIĆ W TYM MIEJSCU STATEK xD
        return wspolrzedne; 
    } 
    
    /**
     * to samo co funkcja wyżej tylko że losowo 
     * @param plansza - aby sprawdzić czy można 
     * @return to co wyżej
     */
    public static String[] losoweObieWspolrzedne (String[][] plansza){
        String []wspolrzedne = new String[2];
        do{
                wspolrzedne[0] = losowaWspLit();// PRZY POMOCY FUNKCJI POBIERA PIERWSZA WSPÓŁŻĘDNĄ 
                wspolrzedne[1] = losowaWspCyf(); // A TU DRUGA 
            }while("*".equals(plansza[Integer.valueOf(wspolrzedne[0].charAt(0)) - 64][Integer.valueOf(wspolrzedne[1])]) || "*".equals(plansza[Integer.valueOf(wspolrzedne[0].charAt(0)) - 64 + 1][Integer.valueOf(wspolrzedne[1])]) || "*".equals(plansza[Integer.valueOf(wspolrzedne[0].charAt(0)) - 64 - 1][Integer.valueOf(wspolrzedne[1])]) || "*".equals(plansza[Integer.valueOf(wspolrzedne[0].charAt(0)) - 64][Integer.valueOf(wspolrzedne[1]) + 1]) || "*".equals(plansza[Integer.valueOf(wspolrzedne[0].charAt(0)) - 64][Integer.valueOf(wspolrzedne[1]) - 1]) || "*".equals(plansza[Integer.valueOf(wspolrzedne[0].charAt(0)) - 64 + 1][Integer.valueOf(wspolrzedne[1]) + 1]) || "*".equals(plansza[Integer.valueOf(wspolrzedne[0].charAt(0)) - 64 + 1][Integer.valueOf(wspolrzedne[1]) - 1]) || "*".equals(plansza[Integer.valueOf(wspolrzedne[0].charAt(0)) - 64 - 1][Integer.valueOf(wspolrzedne[1]) + 1]) || "*".equals(plansza[Integer.valueOf(wspolrzedne[0].charAt(0)) - 64 - 1][Integer.valueOf(wspolrzedne[1]) - 1]) );
            // @up TEN WARUNEK JEST TROSZKE POJEBANY ALE OGARNIA CZY MOŻNA POSTAWIĆ W TYM MIEJSCU STATEK xD
        return wspolrzedne; 
    } 
    
    /**
     * Funkcja pobiera od użytkownika kierunek w którym
     * ma być umiejscowiony statek
     * 
     * @param dlStatku - 4,3,2,1
     * @param plansza - plansza dla której ustalamy kierunek 
     * @param wspolrzednaLit - wsp pionowa
     * @param wspolrzednaCyfr - wsp pozioma 
     * @return - kierunek 
     */
    public static String kierunek(int dlStatku, String [][]plansza, String wspolrzednaLit, String wspolrzednaCyfr){
        System.out.println();
        System.out.println("So now you have to choose directon of " + dlStatku + ". square ship.");
        System.out.print("(U)p, (D)own, (R)ight, (L)eft :");

        Scanner scan = new Scanner(System.in);
        String direction = "X";
        boolean check;

        do{//usytalenie kierunku i jego poprawnowści 
            direction = scan.nextLine();

            while(!"U".equals(direction) && !"D".equals(direction) && !"R".equals(direction) && !"L".equals(direction)){
                System.out.println();
                System.out.println("Man you have to choose corectly!");
                direction = scan.nextLine();
            }

            check = sprawdzenieStatku(plansza, wspolrzednaLit, wspolrzednaCyfr, direction, dlStatku);
            
            if (!check){
                System.out.println();
                System.out.println("You have made a bad choise :c");
            }
            
        }while(check == false);

        return direction;
    }
    
    /**
     * Robi to samo co funkcja wyżej tyle że lsowo
     * 
     * @param dlStatku - 4,3,2,1
     * @param plansza - dla kopmutera 
     * @param wspolrzednaLit - wsp pionowa 
     * @param wspolrzednaCyfr - wsp pozioma
     * @return - zwraca wygenerowany kierunek 
     */
    public static String losowyKierunek (int dlStatku, String [][]plansza, String wspolrzednaLit, String wspolrzednaCyfr){
        Random losowa = new Random();
        String direction = "X";
        int rand;
        
        do{
            rand = losowa.nextInt(4);
            
            switch(rand){
                case 0 : direction = "U"; break;
                case 1 : direction = "D"; break;
                case 2 : direction = "R"; break;
                case 3 : direction = "L"; break;
            }
        }while(!sprawdzenieStatku(plansza, wspolrzednaLit, wspolrzednaCyfr, direction, dlStatku));
        
        return direction;        
    }
    
    /**
     * Sprawdza możliwość położenia w tym miejscu statku poprzez try 
     * 
     * @param plansza - tablica z poczatkową wartością i już gotowymi 
     * @param wspX - wspolrzedna Litoreowa 
     * @param wspY - wspolrzedna cyfrowa 
     * @param dlStatku - dlugosc statku 
     * @return 
     */
    public static boolean sprawdzenieStatku(String[][] plansza, String wspX, String wspY, String kierunek, int dlStatku){
        try{
            switch(kierunek){
                case "U" :
                    for(int i = 1; i < dlStatku; i ++){
                        if ((!"O".equals(plansza[Integer.valueOf(wspX.charAt(0)) - 64 - i][Integer.valueOf(wspY)]) || "*".equals(plansza[Integer.valueOf(wspX.charAt(0)) - 64 - i][Integer.valueOf(wspY) + 1]) || "*".equals(plansza[Integer.valueOf(wspX.charAt(0)) - 64 - i][Integer.valueOf(wspY) - 1])) &&
                           (!"O".equals(plansza[Integer.valueOf(wspX.charAt(0)) - 64 - i - 1][Integer.valueOf(wspY)]) || "*".equals(plansza[Integer.valueOf(wspX.charAt(0)) - 64 - i - 1][Integer.valueOf(wspY) + 1]) || "*".equals(plansza[Integer.valueOf(wspX.charAt(0)) - 64 - i - 1][Integer.valueOf(wspY) - 1])) ) return false;
                    }
                    break;
                case "D" : 
                    for(int i = 1; i < dlStatku; i ++){
                        if ((!"O".equals(plansza[Integer.valueOf(wspX.charAt(0)) - 64 + i][Integer.valueOf(wspY)]) || "*".equals(plansza[Integer.valueOf(wspX.charAt(0)) - 64 + i][Integer.valueOf(wspY) + 1]) || "*".equals(plansza[Integer.valueOf(wspX.charAt(0)) - 64 + i][Integer.valueOf(wspY) - 1])) &&
                            (!"O".equals(plansza[Integer.valueOf(wspX.charAt(0)) - 64 + i + 1][Integer.valueOf(wspY)]) || "*".equals(plansza[Integer.valueOf(wspX.charAt(0)) - 64 + i + 1][Integer.valueOf(wspY) + 1]) || "*".equals(plansza[Integer.valueOf(wspX.charAt(0)) - 64 + i + 1][Integer.valueOf(wspY) - 1]))) return false;
                    }
                    break;
                case "L" :
                    for(int i = 1; i < dlStatku; i ++){
                        if ((!"O".equals(plansza[Integer.valueOf(wspX.charAt(0)) - 64][Integer.valueOf(wspY) - i]) || "*".equals(plansza[Integer.valueOf(wspX.charAt(0)) - 64 + 1][Integer.valueOf(wspY) - i]) || "*".equals(plansza[Integer.valueOf(wspX.charAt(0)) - 64 - 1][Integer.valueOf(wspY) - i])) &&
                            (!"O".equals(plansza[Integer.valueOf(wspX.charAt(0)) - 64][Integer.valueOf(wspY) - i - 1]) || "*".equals(plansza[Integer.valueOf(wspX.charAt(0)) - 64 + 1][Integer.valueOf(wspY) - i - 1]) || "*".equals(plansza[Integer.valueOf(wspX.charAt(0)) - 64 - 1][Integer.valueOf(wspY) - i - 1]))) return false;
                    }
                    break;
                case "R" :
                    for(int i = 1; i < dlStatku; i ++){
                        if ((!"O".equals(plansza[Integer.valueOf(wspX.charAt(0)) - 64][Integer.valueOf(wspY) + i]) || "*".equals(plansza[Integer.valueOf(wspX.charAt(0)) - 64 + 1][Integer.valueOf(wspY) + i]) || "*".equals(plansza[Integer.valueOf(wspX.charAt(0)) - 64 - 1][Integer.valueOf(wspY) + i])) && 
                            (!"O".equals(plansza[Integer.valueOf(wspX.charAt(0)) - 64][Integer.valueOf(wspY) + i + 1]) || "*".equals(plansza[Integer.valueOf(wspX.charAt(0)) - 64 + 1][Integer.valueOf(wspY) + i + 1]) || "*".equals(plansza[Integer.valueOf(wspX.charAt(0)) - 64 - 1][Integer.valueOf(wspY) + i + 1]))) return false;
                    }
                    break;
            }
        }catch(Exception e){
            return false;
        }
        return true; 
    }
    
    /**
     * Funkcja zapisuje do danej tablicy cały statek
     * 
     * @param plansza
     * @param wspX
     * @param wspY
     * @param kierunek
     * @param dlStatku 
     */
    public static void wpisanieStatku(String[][] plansza, String wspX, String wspY, String kierunek, int dlStatku){
        switch(kierunek){
                case "U" :
                    for(int i = 1; i < dlStatku; i ++){
                        plansza[Integer.valueOf(wspX.charAt(0)) - 64 - i][Integer.valueOf(wspY)] = "*";
                    }
                    break;
                case "D" : 
                    for(int i = 1; i < dlStatku; i ++){
                        plansza[Integer.valueOf(wspX.charAt(0)) - 64 + i][Integer.valueOf(wspY)] = "*";
                    }
                    break;
                case "L" :
                    for(int i = 1; i < dlStatku; i ++){
                        plansza[Integer.valueOf(wspX.charAt(0)) - 64][Integer.valueOf(wspY) - i] = "*";
                    }
                    break;
                case "R" :
                    for(int i = 1; i < dlStatku; i ++){
                        plansza[Integer.valueOf(wspX.charAt(0)) - 64][Integer.valueOf(wspY) + i] = "*";
                    }
                    break;
            }
    }
    
    /**
     * Dodaje cały jeden statek 
     * 
     * @param plansza - do tej planszy 
     * @param dlStatku - o tej długosci 
     */
    public static void pelnaObslugaWprowadzaniaStatku (String[][] plansza, int dlStatku){
        
        String wspolrzednaCyfr, wspolrzednaLit;
        String []wspolrzedne;
        
        wspolrzedne = obieWspolrzedne(plansza);
        wspolrzednaLit =  wspolrzedne[0];
        wspolrzednaCyfr = wspolrzedne[1];

        plansza[Integer.valueOf(wspolrzednaLit.charAt(0)) - 64][Integer.valueOf(wspolrzednaCyfr)] = "*";
        wypiszPlansza(plansza);

        wpisanieStatku(plansza, wspolrzednaLit, wspolrzednaCyfr, kierunek(dlStatku, plansza, wspolrzednaLit, wspolrzednaCyfr), dlStatku); // WYPISUJE STATEK O DLUGOSCI (int)J NA TABLICY

        wypiszPlansza(plansza);
    }
    
    /**
     * Dodaj losowy statek poprzez wykorzystanie funkcji losowych
     * 
     * @param plansza - do tej planszy
     * @param dlStatku  - o tej dlugosci 
     */
    public static void pelnaObslugaDodawaniaLosowegoStatku(String[][] plansza, int dlStatku){
        
        String wspolrzednaCyfr , wspolrzednaLit;
        String []wspolrzedne;
        
        wspolrzedne = losoweObieWspolrzedne(plansza);
        
        wspolrzednaLit =  wspolrzedne[0];
        wspolrzednaCyfr = wspolrzedne[1];

        plansza[Integer.valueOf(wspolrzednaLit.charAt(0)) - 64][Integer.valueOf(wspolrzednaCyfr)] = "*";

        wpisanieStatku(plansza, wspolrzednaLit, wspolrzednaCyfr, losowyKierunek(dlStatku, plansza, wspolrzednaLit, wspolrzednaCyfr), dlStatku); // WYPISUJE STATEK O DLUGOSCI (int)J NA TABLICY
    }
    
    /**
     * tura[0] - win czy nie
     * tura[1] - strike dla gracza
     * tura[2] - strike dla computera
     * @param plansza1
     * @param plansza2
     * @param tarcza
     * @param iloscTrafien1
     * @param iloscTrafien2
     * @return 
     */
    public static int[] turaPVC(String[][] plansza1, String[][] plansza2, String[][] tarcza, int[] licznik1){
        boolean []tura = new boolean[3];
        int []licznik  = licznik1;
        
        String []wspolrzedne;
        String[][] pusta = tworzenieMapy();
        
        String wspolrzednaCyfr , wspolrzednaLit;
        
        do{ // ruch gracza
            System.out.println();
            System.out.println("Choose a place of bomb's explosion!");
            do{
                wspolrzedne = losoweObieWspolrzedne(pusta);
                wspolrzednaLit =  wspolrzedne[0];
                wspolrzednaCyfr = wspolrzedne[1];
            }while(!"O".equals(tarcza[Integer.valueOf((wspolrzednaLit).charAt(0)) - 64][Integer.valueOf(wspolrzednaCyfr)]) && !"*".equals(plansza1[Integer.valueOf((wspolrzednaLit).charAt(0)) - 64][Integer.valueOf(wspolrzednaCyfr)]));            
            
            
            if ("*".equals(plansza2[Integer.valueOf((wspolrzednaLit).charAt(0)) - 64][Integer.valueOf(wspolrzednaCyfr)])){
                
               // plansza2[Integer.valueOf((wspolrzednaLit).charAt(0)) - 64][Integer.valueOf(wspolrzednaCyfr)] = "x";
                tarcza[Integer.valueOf((wspolrzednaLit).charAt(0)) - 64][Integer.valueOf(wspolrzednaCyfr)] = "x";
                
                wypiszPlansza(tarcza);
                
                System.out.println();
                System.out.println("TARCZA");
                System.out.println();
                System.out.println("You hit it!");
                licznik[0] ++;
                tura[1] = true;
                
            }
            else{
                
                tarcza[Integer.valueOf((wspolrzednaLit).charAt(0)) - 64][Integer.valueOf(wspolrzednaCyfr)] = "M";
                
                wypiszPlansza(tarcza);  
                
                System.out.println();
                System.out.println("TARCZA");
                System.out.println();
                System.out.println("You missed.");
                tura[1] = false;
            }
        
        }while (tura[1]);
        
        do{ // ruch komputera
            do{
                wspolrzedne = losoweObieWspolrzedne(pusta);
                wspolrzednaLit =  wspolrzedne[0];
                wspolrzednaCyfr = wspolrzedne[1];
                
            }while(!"O".equals(plansza1[Integer.valueOf((wspolrzednaLit).charAt(0)) - 64][Integer.valueOf(wspolrzednaCyfr)]) && !"*".equals(plansza1[Integer.valueOf((wspolrzednaLit).charAt(0)) - 64][Integer.valueOf(wspolrzednaCyfr)]));
            
            
            if ("*".equals(plansza1[Integer.valueOf((wspolrzednaLit).charAt(0)) - 64][Integer.valueOf(wspolrzednaCyfr)])){
                plansza1[Integer.valueOf((wspolrzednaLit).charAt(0)) - 64][Integer.valueOf(wspolrzednaCyfr)] = "x";
                
                wypiszPlanszaCZ(plansza1);
                
                System.out.println("This is your bord with hit mark");
                System.out.println();
                System.out.println("Computer just hit!");
                licznik[1] ++;
                tura[2] = true;
            }
            else{
                plansza1[Integer.valueOf((wspolrzednaLit).charAt(0)) - 64][Integer.valueOf(wspolrzednaCyfr)] = "M";
                
                wypiszPlanszaCZ(plansza1);
                
                System.out.println("This is your bord with miss mark");
                System.out.println();
                System.out.println("Computer's missed!");
                tura[2] = false;
            }
        }while (tura[2]);
        return licznik;
    }
    
    
    public static int turaPVP(String[][] plansza1, String[][] plansza2, String[][] tarcza1, int licznik1){
        boolean []tura = new boolean[3];
        int licznik  = licznik1;
        
        String []wspolrzedne;
        String[][] pusta = tworzenieMapy();
        
        String wspolrzednaCyfr , wspolrzednaLit;
        
        do{ // ruch gracza
            System.out.println();
            System.out.println("Choose a place of bomb's explosion!");
            do{
                wspolrzedne = losoweObieWspolrzedne(pusta);
                wspolrzednaLit =  wspolrzedne[0];
                wspolrzednaCyfr = wspolrzedne[1];
            }while(!"O".equals(tarcza1[Integer.valueOf((wspolrzednaLit).charAt(0)) - 64][Integer.valueOf(wspolrzednaCyfr)]) && !"*".equals(plansza1[Integer.valueOf((wspolrzednaLit).charAt(0)) - 64][Integer.valueOf(wspolrzednaCyfr)]));            
            
            
            if ("*".equals(plansza2[Integer.valueOf((wspolrzednaLit).charAt(0)) - 64][Integer.valueOf(wspolrzednaCyfr)])){
                
               // plansza2[Integer.valueOf((wspolrzednaLit).charAt(0)) - 64][Integer.valueOf(wspolrzednaCyfr)] = "x";
                tarcza1[Integer.valueOf((wspolrzednaLit).charAt(0)) - 64][Integer.valueOf(wspolrzednaCyfr)] = "x";
                
                wypiszPlansza(tarcza1);
                
                System.out.println();
                System.out.println("TARCZA");
                System.out.println();
                System.out.println("You hit it!");
                licznik ++;
                tura[1] = true;
                
            }
            else{
                
                tarcza1[Integer.valueOf((wspolrzednaLit).charAt(0)) - 64][Integer.valueOf(wspolrzednaCyfr)] = "M";
                
                wypiszPlansza(tarcza1);  
                
                System.out.println();
                System.out.println("TARCZA");
                System.out.println();
                System.out.println("You missed.");
                tura[1] = false;
            }
        
        }while (tura[1]);
        
        return licznik;
    }
    
    
    public static void pvc(){
        
        String[][] planszaGracza = tworzenieMapy(); // PRZYPISUJE GOTOWĄ PUSTĄ MAPE
        String[][] planszaKomputera = tworzenieMapy();
        String[][] tarcza = tworzenieMapy();
        
        wypiszPlansza(planszaGracza); // WYPISUJE JA NA TABLICY
        
        wypiszInst(); // WYPISUJE MALE PODPOWIEDZI DO GRY
        
        pelnaObslugaDodawaniaLosowegoStatku(planszaGracza, 4);
        for (int i = 0; i < 2; i ++)pelnaObslugaDodawaniaLosowegoStatku(planszaGracza, 3);
        for (int i = 0; i < 3; i ++)pelnaObslugaDodawaniaLosowegoStatku(planszaGracza, 2);
        for (int i = 0; i < 4; i ++)pelnaObslugaDodawaniaLosowegoStatku(planszaGracza, 1);
        
        System.out.println();
        wypiszPlansza(planszaGracza);
        System.out.println();
        System.out.println("This is your mape of ships.");
        
        pelnaObslugaDodawaniaLosowegoStatku(planszaKomputera, 4);
        for (int i = 0; i < 2; i ++)pelnaObslugaDodawaniaLosowegoStatku(planszaKomputera, 3);
        for (int i = 0; i < 3; i ++)pelnaObslugaDodawaniaLosowegoStatku(planszaKomputera, 2);
        for (int i = 0; i < 4; i ++)pelnaObslugaDodawaniaLosowegoStatku(planszaKomputera, 1);
        
        wypiszPlansza(planszaKomputera);
        System.out.println();
        System.out.println("This is your oponent mape of ships.");
        
       
       int[] licznik1 = new int[2];
       int[] licznik2 = new int[2];
       licznik1[0] = licznik1[1] = licznik2[0] = licznik2[1] = 0;
       
        for(; licznik1[0] < 20 && licznik1[1] < 20;){
           licznik1 = turaPVC(planszaGracza, planszaKomputera, tarcza, licznik2);
           licznik2 = licznik1;  
        }
        if(licznik1[0] == 20) System.out.println("CONGRATULATIONS YOU WON!!!");
        else System.out.println("Sorry you lost ;c");
    }
    
    
    public static void pvp(){
        
        String[][] planszaGracza1 = tworzenieMapy(); // PRZYPISUJE GOTOWĄ PUSTĄ MAPE
        String[][] planszaGracza2 = tworzenieMapy();
        String[][] tarcza1 = tworzenieMapy();
        String[][] tarcza2 = tworzenieMapy();
        
        wypiszPlansza(planszaGracza1); // WYPISUJE JA NA TABLICY
        
        wypiszInst(); // WYPISUJE MALE PODPOWIEDZI DO GRY
        System.out.println("Turn for GAMER1");
        for (int i = 0; i < 1; i ++)pelnaObslugaDodawaniaLosowegoStatku(planszaGracza1, 4);
        for (int i = 0; i < 2; i ++)pelnaObslugaDodawaniaLosowegoStatku(planszaGracza1, 3);
        for (int i = 0; i < 3; i ++)pelnaObslugaDodawaniaLosowegoStatku(planszaGracza1, 2);
        for (int i = 0; i < 4; i ++)pelnaObslugaDodawaniaLosowegoStatku(planszaGracza1, 1);
        
        System.out.println();
        wypiszPlansza(planszaGracza1);
        System.out.println();
        
        System.out.println("This is GAMER1 mape of ships.");
        System.out.printf("\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.println("Turn for GAMER2");
        
        for (int i = 0; i < 1; i ++)pelnaObslugaDodawaniaLosowegoStatku(planszaGracza2, 4);
        for (int i = 0; i < 2; i ++)pelnaObslugaDodawaniaLosowegoStatku(planszaGracza2, 3);
        for (int i = 0; i < 3; i ++)pelnaObslugaDodawaniaLosowegoStatku(planszaGracza2, 2);
        for (int i = 0; i < 4; i ++)pelnaObslugaDodawaniaLosowegoStatku(planszaGracza2, 1);
        
        System.out.println();
        wypiszPlansza(planszaGracza2);
        System.out.println();
        System.out.println("This is GAMER2 mape of ships.");
        
       
       int licznik1 = 0, licznik2 = 0, licznik3 = 0, licznik4 = 0;
       
        for(; licznik1 < 20 && licznik1 < 20;){
           System.out.println("Now turn for GAMER1");
           licznik1 = turaPVP(planszaGracza1, planszaGracza2, tarcza1, licznik2);
           licznik2 = licznik1;  
           
           System.out.println("Now turn for GAMER2");
           licznik3 = turaPVP(planszaGracza2, planszaGracza1, tarcza2, licznik4);
           licznik4 = licznik3; 
        }
        
        
        if(licznik1 == 20) System.out.println("CONGRATULATIONS GAMER1 WON!!!");
        else System.out.println("CONGRATULATIONS GAMER1 WON!!!");
    }
    
    
    
    public static void inst(){   
        System.out.println("If you don't know rules of this games you are a lame ;c");
        System.out.println("Sorry ;c");
        System.out.println("");
        System.out.println("So now you want to play a game?");
        System.out.print("(Y)es or (N)ot : ");
        
        Scanner scan = new Scanner(System.in);
        String choise = scan.nextLine();
        
        switch(choise){
            case "Y" : 
                System.out.println("Are you really sure that ?");
                System.out.print("(Y)es or (N)ot : ");
                choise = scan.nextLine();
                break;
            
            case "N" :
                System.out.println("Are you really sure that ? ;c");
                System.out.print("(Y)es or (N)ot : ");
                choise = scan.nextLine();
                break;
            
            default :
                System.out.println("Man you are so naughty!");
                System.out.println("Instrucions was clear,");
                System.out.println("You have only choise 'Y' or 'N'");
                System.err.println("You are to stupid to play this game!");
                System.exit(-1);
                break;
        }
    }
}

