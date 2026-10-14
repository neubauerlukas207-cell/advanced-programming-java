import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

/** Testy skutečného zadání s jednotnými inkluzivními hranicemi půlek. */
class VyhodnoceniProspechuTest {
    /** Testovaný objekt. */
    private final VyhodnoceniProspechu v = new VyhodnoceniProspechu();

    /** Ověří všech pět slovních hodnocení.
     * @param znamka zadaná známka
     * @param text očekávané hodnocení
     */
    @ParameterizedTest
    @CsvSource({"1,výborně", "2,chvalitebně", "3,dobře", "4,dostatečně", "5,nedostatečně"})
    void jednotliveZnamky(int znamka, String text) {
        assertEquals(text, v.prospech(new int[]{znamka}));
    }

    /** Půlky patří k lepšímu hodnocení, stejně jako většina větví dodaného kódu.
     * @param a první známka
     * @param b druhá známka
     * @param text očekávané hodnocení
     */
    @ParameterizedTest
    @CsvSource({"1,2,výborně", "2,3,chvalitebně", "3,4,dobře", "4,5,dostatečně"})
    void presnePoloviny(int a, int b, String text) {
        assertEquals(text, v.prospech(new int[]{a,b}));
    }

    /** Ověří průměry těsně pod a nad všemi hranicemi.
     * @param a nižší známka
     * @param b vyšší známka
     * @param pod výsledek pod půlkou
     * @param nad výsledek nad půlkou
     */
    @ParameterizedTest
    @CsvSource({"1,2,výborně,chvalitebně", "2,3,chvalitebně,dobře",
        "3,4,dobře,dostatečně", "4,5,dostatečně,nedostatečně"})
    void obeStranyHranice(int a, int b, String pod, String nad) {
        assertEquals(pod, v.prospech(new int[]{a,a,b}));
        assertEquals(nad, v.prospech(new int[]{a,b,b}));
    }

    /** Smíšené známky mají aritmetický průměr tři. */
    @Test void smesZnamek() {
        assertEquals("dobře", v.prospech(new int[]{1,2,3,4,5}));
    }
    /** Prázdné pole má jednoznačně definovanou chybu. */
    @Test void prazdnePole() {
        assertThrows(IllegalArgumentException.class, () -> v.prospech(new int[]{}));
    }
    /** Null je odmítnuto. */
    @Test void nullPole() {
        assertThrows(NullPointerException.class, () -> v.prospech(null));
    }
    /** Známky mimo školní stupnici jsou odmítnuty. */
    @Test void neplatneZnamky() {
        for (int n : new int[]{0,6,-1})
            assertThrows(IllegalArgumentException.class, () -> v.prospech(new int[]{n}));
    }
    /** Výpočet nemění pořadí ani obsah vstupu. */
    @Test void nemeniVstup() {
        int[] a = {2,1};
        v.prospech(a);
        assertArrayEquals(new int[]{2,1}, a);
    }
}
