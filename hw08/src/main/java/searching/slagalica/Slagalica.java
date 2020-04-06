package searching.slagalica;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import searching.algorithms.Transition;

/**
 * Razred sadrži metode za upravljanje slagalicom.
 * <p>
 * Metode su: 
 * test - testira da li je dana konfiguracija konačna konfiguracija.
 * get - vraća početnu konfiguraciju.
 * apply - vraća nasljednike danog stanja.
 * <p>
 * 
 * @author Filip Hustić
 *
 */
public class Slagalica implements Supplier<KonfiguracijaSlagalice>, Predicate<KonfiguracijaSlagalice>,
		Function<KonfiguracijaSlagalice, List<Transition<KonfiguracijaSlagalice>>> {

	/**
	 * Broj redaka u slagalici.
	 */
	private final int BROJ_REDAKA_SLAGALICE;
	/**
	 * Broj stupaca u slagalici.
	 */
	private final int BROJ_STUPACA_SLAGALICE;

	/**
	 * Konačno stanje slagalice.
	 */
	private final int[] KONAČNO_STANJE = { 1, 2, 3, 4, 5, 6, 7, 8, 0 };

	/**
	 * Početna konfiguracija slagalice.
	 */
	private KonfiguracijaSlagalice pocetnaKonfiguracija;

	/**
	 * Konstruktor koji prima početnu konfiguraciju i iz nje izvlači broj redaka i
	 * broj stupaca.
	 * 
	 * @param pocetnaKonfiguracija početna konfiguracija.
	 */
	public Slagalica(KonfiguracijaSlagalice pocetnaKonfiguracija) {
		super();
		this.pocetnaKonfiguracija = pocetnaKonfiguracija;
		this.BROJ_REDAKA_SLAGALICE = KonfiguracijaSlagalice.getBrojRedaka();
		this.BROJ_STUPACA_SLAGALICE = KonfiguracijaSlagalice.getBrojStupaca();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Transition<KonfiguracijaSlagalice>> apply(KonfiguracijaSlagalice konf) {
		List<Transition<KonfiguracijaSlagalice>> konfiguracije = new ArrayList<>();
		int indexPraznog = konf.indexOfSpace();
		// u konfiguraciji ne postoji 0
		if (indexPraznog == -1) {
			return konfiguracije;
		}

		int[] trenutnoStanje = konf.getPolje();

		if (!izvanSlagalice(indexPraznog, indexPraznog - 1)) {
			int[] novoStanje = zamijeni(indexPraznog, indexPraznog - 1, trenutnoStanje);
			konfiguracije.add(new Transition(new KonfiguracijaSlagalice(novoStanje), 1));
		}
		if (!izvanSlagalice(indexPraznog, indexPraznog + 1)) {
			int[] novoStanje = zamijeni(indexPraznog, indexPraznog + 1, trenutnoStanje);
			konfiguracije.add(new Transition(new KonfiguracijaSlagalice(novoStanje), 1));
		}
		if (!izvanSlagalice(indexPraznog, indexPraznog - BROJ_STUPACA_SLAGALICE)) {
			int[] novoStanje = zamijeni(indexPraznog, indexPraznog - 3, trenutnoStanje);
			konfiguracije.add(new Transition(new KonfiguracijaSlagalice(novoStanje), 1));
		}
		if (!izvanSlagalice(indexPraznog, indexPraznog + BROJ_STUPACA_SLAGALICE)) {
			int[] novoStanje = zamijeni(indexPraznog, indexPraznog + 3, trenutnoStanje);
			konfiguracije.add(new Transition(new KonfiguracijaSlagalice(novoStanje), 1));
		}

		return konfiguracije;
	}

	/**
	 * Pomoćna metoda koja testira ako je dan index izvan slagalice.
	 * 
	 * @param indexPraznog index praznog elementa.
	 * @param index        koji se testira da li je izvan slagalice.
	 * @return true ako je index izvan slagalice, false ako je index unutar
	 *         slagalice.
	 */
	private boolean izvanSlagalice(int indexPraznog, int index) {

		if (index < 0 || index >= BROJ_REDAKA_SLAGALICE * BROJ_STUPACA_SLAGALICE)
			return true;
		else if (((indexPraznog + 1) % BROJ_STUPACA_SLAGALICE == 0) && ((index + 1) % BROJ_STUPACA_SLAGALICE == 1))
			return true;
		else if (((index + 1) % BROJ_STUPACA_SLAGALICE == 0) && ((indexPraznog + 1) % BROJ_STUPACA_SLAGALICE == 1))
			return true;
		else
			return false;

	}

	/**
	 * Pomoćna metoda koja vraća novo polje brojeva sa zamijenjenim elementima na
	 * indexPrvog i indexDrugog.
	 * 
	 * @param indexPrvog
	 * @param indexDrugog
	 * @param stanje      raspored brojeva u slagalici.
	 * @return novo polje sa zamijenjenim elementima na indexPrvog i indexDrugo.
	 */
	private int[] zamijeni(int indexPrvog, int indexDrugog, int[] stanje) {
		int[] novoStanje = Arrays.copyOf(stanje, stanje.length);
		int prvi = novoStanje[indexPrvog];
		novoStanje[indexPrvog] = novoStanje[indexDrugog];
		novoStanje[indexDrugog] = prvi;

		return novoStanje;
	}

	@Override
	public boolean test(KonfiguracijaSlagalice konf) {
		return Arrays.compare(KONAČNO_STANJE, konf.getPolje()) == 0 ? true : false;
	}

	@Override
	public KonfiguracijaSlagalice get() {
		return pocetnaKonfiguracija;
	}

}
