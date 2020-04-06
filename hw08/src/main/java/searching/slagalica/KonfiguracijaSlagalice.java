package searching.slagalica;

import java.util.Arrays;

/**
 * Razred predstavlja jednu konfiguraciju slagalice tj. razmještaj brojeva u
 * slagalici.
 * 
 * @author Filip Hustić
 *
 */
public class KonfiguracijaSlagalice {

	/**
	 * Polje koje sadrži razmještaj bojeva u slagalici.
	 */
	private int[] stanje;
	/**
	 * Broj redaka slagalice.
	 */
	private static final int BROJ_REDAKA = 3;
	/**
	 * Broj stupaca slagalice.
	 */
	private static final int BROJ_STUPACA = 3;

	/**
	 * Konstrukotr koji prima inicijalan razmještaj brojeva u slagalici.
	 * @param stanje
	 */
	public KonfiguracijaSlagalice(int[] stanje) {
		super();
		this.stanje = stanje;
	}

	/**
	 * 
	 * @return polje razmještaja brojeva u slagalici.
	 */
	public int[] getPolje() {
		return stanje;
	}

	/**
	 * 
	 * @return index praznog elementa.
	 */
	public int indexOfSpace() {
		for (int i = 0; i < stanje.length; ++i) {
			if (stanje[i] == 0)
				return i;
		}
		return -1;
	}

	/**
	 * 
	 * @return broj redaka slagalice.
	 */
	public static int getBrojRedaka() {
		return BROJ_REDAKA;
	}

	/**
	 * 
	 * @return broj stupaca slagalice
	 */
	public static int getBrojStupaca() {
		return BROJ_STUPACA;
	}
	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < BROJ_REDAKA; ++i) {
			for (int j = 0; j < BROJ_STUPACA; ++j) {
				int element = stanje[i * BROJ_REDAKA + j];

				if (element == 0) {
					builder.append("*");
				} else {
					builder.append(element);
				}

				builder.append(" ");
			}
			builder.append(System.getProperty("line.separator"));
		}

		return builder.toString();
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(stanje);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KonfiguracijaSlagalice other = (KonfiguracijaSlagalice) obj;
		if (!Arrays.equals(stanje, other.stanje))
			return false;
		return true;
	}

}
