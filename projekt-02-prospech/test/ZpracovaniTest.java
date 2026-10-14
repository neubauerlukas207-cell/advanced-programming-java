import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.*;

/** Hraniční testy otevřeného intervalu a sestupných vzorků z dodaného kódu. */
class ZpracovaniTest {
    /** Testovaný objekt. */
    private final Zpracovani z = new Zpracovani();

    /** Ověří hodnoty pod mezí, na obou mezích, uvnitř a nad intervalem.
     * @param a dolní mez
     * @param b horní mez
     * @param x testovaná hodnota
     * @param expected očekávaný výsledek
     */
    @ParameterizedTest
    @CsvSource({"0,10,-1,false", "0,10,0,false", "0,10,1,true", "0,10,9,true",
        "0,10,10,false", "0,10,11,false", "10,0,5,false", "2,2,2,false", "-10,-2,-5,true"})
    void interval(int a, int b, int x, boolean expected) {
        assertEquals(expected, Zpracovani.interval(a,b,x));
    }
    /** Porovnání musí fungovat i pro krajní int. */
    @Test void krajniInt() {
        assertTrue(Zpracovani.interval(Integer.MIN_VALUE, Integer.MAX_VALUE, 0));
    }
    /** Sestup začíná v kam a dolní odkud se nezahrnuje. */
    @Test void celeKroky() {
        assertArrayEquals(new double[]{5,4,3,2}, z.vzorkySestupně(5,1,1), 1e-12);
    }
    /** Necelý podíl vyžaduje zaokrouhlení počtu vzorků nahoru. */
    @Test void nedelitelnyRozsah() {
        assertArrayEquals(new double[]{5,3,1}, z.vzorkySestupně(5,0,2), 1e-12);
    }
    /** Desetinný krok se nesmí projevit ztrátou nebo přidáním vzorku. */
    @Test void desetinneKroky() {
        assertArrayEquals(new double[]{0.3,0.2,0.1}, z.vzorkySestupně(0.3,0,0.1), 1e-12);
    }
    /** Meze mohou být záporné. */
    @Test void zaporneMeze() {
        assertArrayEquals(new double[]{-1,-2}, z.vzorkySestupně(-1,-3,1), 1e-12);
    }
    /** Prázdný rozsah vrátí prázdné pole. */
    @Test void stejneMeze() {
        assertArrayEquals(new double[]{}, z.vzorkySestupně(2,2,1));
    }
    /** I při kroku větším než rozsah vznikne počáteční vzorek. */
    @Test void velkyKrok() {
        assertArrayEquals(new double[]{5}, z.vzorkySestupně(5,1,10), 1e-12);
    }
    /** Opačný směr mezí je odmítnut. */
    @Test void obraceneMeze() {
        assertThrows(IllegalArgumentException.class, () -> z.vzorkySestupně(1,5,1));
    }
    /** Shodné meze dovolují bezpečně otestovat i chybný nulový krok původního kódu. */
    @Test void neplatnyKrok() {
        for (double k : new double[]{0,-1,Double.NaN,Double.POSITIVE_INFINITY})
            assertThrows(IllegalArgumentException.class, () -> z.vzorkySestupně(1,1,k));
    }
    /** Shodné nekonečné meze nevyvolají v původním kódu obří alokaci. */
    @Test void nekonecneMeze() {
        for (double n : new double[]{Double.NaN,Double.POSITIVE_INFINITY,Double.NEGATIVE_INFINITY})
            assertThrows(IllegalArgumentException.class, () -> z.vzorkySestupně(n,n,1));
    }
}
