package searching.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import searching.algorithms.Node;
import searching.algorithms.SearchUtil;
import searching.slagalica.KonfiguracijaSlagalice;
import searching.slagalica.Slagalica;
import searching.slagalica.gui.SlagalicaViewer;

/**
 * Razred sadrži glavni program koji ispisuje korake u slaganju slagalice ili
 * kako rješenje nije nađeno ako se slagalica ne može složiti.
 * <p>
 * Ako rješenje postolji aktivirat će se gui. Pristiskom na gui prikazat će se
 * sljedeći korak u slaganju slagalice.
 * <p>
 * 
 * @author Filip Hustić
 *
 */
public class SlagalicaMain {

	/**
	 * Demnzije slagalice.
	 */
	private static int DIMENZIJA_SLAGALICE = 9;

	/**
	 * Ulazna točka programa.
	 * <p>
	 * Kroz argumente je zadana početna konfiguracija slagalice, ako je
	 * konfiguracija pogrešna prekida se sa programom
	 * <p>
	 * <p>
	 * Konfiguracija se sastoji od 9 uzastopnih brojeva u kojima se bez ponavaljanja
	 * pojavljuju brojevi od 0-9. npr 213456780. 0 predstavlja prazan kvadrat.
	 * <p>
	 * 
	 * @param args kao prvi argument je zadana početna konfiguracija slagalice.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Slagalica očekuje jedan argument,početnu konfiguraciju.");
			return;
		}
		String ulaz = args[0];
		if (ulaz.length() != DIMENZIJA_SLAGALICE) {
			System.out.println("Očekivana duljina početne konfiguracije je 9.");
			return;
		}
		if (!ulaz.contains("0") || !ulaz.contains("1") || !ulaz.contains("2") || !ulaz.contains("3")
				|| !ulaz.contains("4") || !ulaz.contains("5") || !ulaz.contains("6") || !ulaz.contains("8")) {
			System.out.println(
					"Početna konfiguracije nije valjana. Zahtjeva se da je prisutan svaki broj od 1-8, te 0 koja predstavlja prazan kvadrat.");
			return;
		}

		int[] konfiguracija = new int[DIMENZIJA_SLAGALICE];

		for (int i = 0; i < DIMENZIJA_SLAGALICE; ++i) {
			konfiguracija[i] = Integer.parseInt(ulaz.substring(i, i + 1));
		}

		Slagalica slagalica = new Slagalica(new KonfiguracijaSlagalice(konfiguracija));

		Node<KonfiguracijaSlagalice> rješenje = SearchUtil.bfsv(slagalica, slagalica, slagalica);

		if (rješenje == null) {
			System.out.println("Nisam uspio pronaći rješenje.");
		} else {
			System.out.println("Imam rješenje. Broj poteza je: " + rješenje.getCost());

			List<KonfiguracijaSlagalice> lista = new ArrayList<>();
			Node<KonfiguracijaSlagalice> trenutni = rješenje;

			while (trenutni != null) {
				lista.add(trenutni.getState());
				trenutni = trenutni.getParent();
			}

			Collections.reverse(lista);
//			lista.stream().forEach(k -> {
//				System.out.println(k);
//				System.out.println();
//			});
			SlagalicaViewer.display(rješenje);
		}

	}
}
