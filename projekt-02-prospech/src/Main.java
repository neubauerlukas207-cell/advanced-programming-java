import java.util.Arrays;

/** Spustitelná ukázka opravených metod; testovací třídy jsou ve složce test. */
public class Main {
    /** Vytvoří objekt ukázkové aplikace. */
    public Main() { }
    /**
     * Vypíše slovní hodnocení, výsledek intervalu, vzorky a komparaci.
     * @param args nepoužité argumenty příkazového řádku
     */
    public static void main(String[] args) {
        System.out.println(new VyhodnoceniProspechu().prospech(new int[]{1,2,2}));
        System.out.println(Zpracovani.interval(0,10,0));
        System.out.println(Arrays.toString(new Zpracovani().vzorkySestupně(5,0,2)));
        System.out.println(ZpracovaniMoje.comp(2,5));
    }
}
