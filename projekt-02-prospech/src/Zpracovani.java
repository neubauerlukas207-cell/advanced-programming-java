import java.math.BigDecimal;
import java.math.RoundingMode;

/** Porovnání v otevřeném intervalu a sestupné generování vzorků. */
public class Zpracovani {
    /** Vytvoří objekt pro generování vzorků. */
    public Zpracovani() { }

    /**
     * Testuje otevřený interval bez obou krajních mezí.
     * @param odkud dolní mez
     * @param kam horní mez
     * @param value testovaná hodnota
     * @return true právě když odkud je menší než value a value je menší než kam
     */
    public static boolean interval(int odkud, int kam, int value) {
        return (odkud < value) && (value < kam);
    }

    /**
     * Vytvoří kam, kam-krok, kam-2*krok, ... dokud je hodnota větší než odkud.
     * Horní kam se zahrnuje, dolní odkud se nezahrnuje; stejná mez dá prázdné pole.
     * Zachovává směr a pořadí parametrů doložené dodanou implementací.
     * Počet vzorků je ceil((kam-odkud)/krok); výpočet v BigDecimal zabraňuje
     * kumulovanému zaokrouhlování při odečítání desetinných kroků v double.
     * @param kam počáteční horní mez
     * @param odkud vyloučená dolní mez
     * @param krok kladný konečný krok
     * @return sestupné vzorky nebo prázdné pole pro shodné meze
     * @throws IllegalArgumentException při neplatných či obrácených mezích,
     *         nekladném kroku, více než milionu vzorků nebo nedostatečném rozlišení double
     */
    public double[] vzorkySestupně(double kam, double odkud, double krok) {
        if (!Double.isFinite(kam) || !Double.isFinite(odkud) || !Double.isFinite(krok)
                || krok <= 0 || kam < odkud)
            throw new IllegalArgumentException("Konečné meze, kam >= odkud a krok > 0.");
        if (kam == odkud) return new double[0];
        BigDecimal start = BigDecimal.valueOf(kam);
        BigDecimal end = BigDecimal.valueOf(odkud);
        BigDecimal step = BigDecimal.valueOf(krok);
        BigDecimal count = start.subtract(end).divide(step, 0, RoundingMode.CEILING);
        if (count.compareTo(BigDecimal.valueOf(1_000_000)) > 0)
            throw new IllegalArgumentException("Nejvýše milion vzorků.");
        double[] values = new double[count.intValueExact()];
        for (int i = 0; i < values.length; i++) {
            values[i] = start.subtract(step.multiply(BigDecimal.valueOf(i))).doubleValue();
            if (values[i] <= odkud || (i > 0 && values[i] >= values[i-1]))
                throw new IllegalArgumentException("Krok je příliš malý pro rozlišení double.");
        }
        return values;
    }
}
