import java.util.Objects;

/** Slovní vyhodnocení známek s jednotnými horními hranicemi včetně půlky. */
public class VyhodnoceniProspechu {
    /** Slovní hodnocení ve stejném pořadí jako v dodaném kódu. */
    private final String[] slovniHodnocení = {
        "výborně", "chvalitebně", "dobře", "dostatečně", "nedostatečně"
    };

    /** Vytvoří vyhodnocovač. */
    public VyhodnoceniProspechu() { }

    /**
     * Vyhodnotí aritmetický průměr. Horní hranice 1,5; 2,5; 3,5 a 4,5
     * jsou zahrnuty do lepšího hodnocení podle sjednocené konvence dodaného kódu.
     * @param znamky neprázdné pole známek 1 až 5
     * @return jedno z pěti slovních hodnocení
     * @throws NullPointerException pokud je pole null
     * @throws IllegalArgumentException pokud je pole prázdné nebo známka neplatná
     */
    public String prospech(int znamky[]) {
        double prumer_znamek = prumer(znamky);
        if (prumer_znamek <= 1.5) return slovniHodnocení[0];
        if (prumer_znamek <= 2.5) return slovniHodnocení[1];
        if (prumer_znamek <= 3.5) return slovniHodnocení[2];
        if (prumer_znamek <= 4.5) return slovniHodnocení[3];
        return slovniHodnocení[4];
    }

    /**
     * Spočítá skutečný desetinný průměr bez změny vstupu.
     * @param znamky neprázdné pole známek 1 až 5
     * @return součet dělený počtem jako double
     * @throws NullPointerException pokud je pole null
     * @throws IllegalArgumentException pokud je pole prázdné nebo známka mimo 1 až 5
     */
    private double prumer(int znamky[]) {
        Objects.requireNonNull(znamky, "Známky nesmí být null.");
        if (znamky.length == 0) throw new IllegalArgumentException("Chybí známky.");
        long suma = 0;
        for (int znamka : znamky) {
            if (znamka < 1 || znamka > 5)
                throw new IllegalArgumentException("Známka musí být 1 až 5.");
            suma += znamka;
        }
        return (double) suma / znamky.length;
    }
}
