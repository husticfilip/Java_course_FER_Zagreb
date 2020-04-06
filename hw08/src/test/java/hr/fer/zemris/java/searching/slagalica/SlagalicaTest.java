package hr.fer.zemris.java.searching.slagalica;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import searching.algorithms.Transition;
import searching.slagalica.KonfiguracijaSlagalice;
import searching.slagalica.Slagalica;

public class SlagalicaTest {

	@Test
	public void testLeftUpEdge() {
		int[] stanje = { 0, 1, 2, 3, 4, 5, 6, 7, 8 };
		KonfiguracijaSlagalice konf = new KonfiguracijaSlagalice(stanje);
		Slagalica sl = new Slagalica(konf);

		List<Transition<KonfiguracijaSlagalice>> transitions = sl.apply(konf);

		assertEquals(2, transitions.size());

		assertEquals(0,
				Arrays.compare(transitions.get(0).getState().getPolje(), new int[] { 1, 0, 2, 3, 4, 5, 6, 7, 8 }));
		assertEquals(0,
				Arrays.compare(transitions.get(1).getState().getPolje(), new int[] { 3, 1, 2, 0, 4, 5, 6, 7, 8 }));
	}

	@Test
	public void testRightUpEdge() {
		int[] stanje = { 2, 1, 0, 3, 4, 5, 6, 7, 8 };
		KonfiguracijaSlagalice konf = new KonfiguracijaSlagalice(stanje);
		Slagalica sl = new Slagalica(konf);

		List<Transition<KonfiguracijaSlagalice>> transitions = sl.apply(konf);

		assertEquals(2, transitions.size());

		assertEquals(0,
				Arrays.compare(transitions.get(0).getState().getPolje(), new int[] { 2, 0, 1, 3, 4, 5, 6, 7, 8 }));
		assertEquals(0,
				Arrays.compare(transitions.get(1).getState().getPolje(), new int[] { 2, 1, 5, 3, 4, 0, 6, 7, 8 }));
	}

	@Test
	public void testLeftDownEdge() {
		int[] stanje = { 6, 1, 2, 3, 4, 5, 0, 7, 8 };
		KonfiguracijaSlagalice konf = new KonfiguracijaSlagalice(stanje);
		Slagalica sl = new Slagalica(konf);

		List<Transition<KonfiguracijaSlagalice>> transitions = sl.apply(konf);

		assertEquals(2, transitions.size());

		assertEquals(0,
				Arrays.compare(transitions.get(1).getState().getPolje(), new int[] { 6, 1, 2, 0, 4, 5, 3, 7, 8 }));
		assertEquals(0,
				Arrays.compare(transitions.get(0).getState().getPolje(), new int[] { 6, 1, 2, 3, 4, 5, 7, 0, 8 }));
	}

	@Test
	public void testRightDownEdge() {
		int[] stanje = { 8, 1, 2, 3, 4, 5, 6, 7, 0 };
		KonfiguracijaSlagalice konf = new KonfiguracijaSlagalice(stanje);
		Slagalica sl = new Slagalica(konf);

		List<Transition<KonfiguracijaSlagalice>> transitions = sl.apply(konf);

		assertEquals(2, transitions.size());

		assertEquals(0,
				Arrays.compare(transitions.get(0).getState().getPolje(), new int[] { 8, 1, 2, 3, 4, 5, 6, 0, 7 }));
		assertEquals(0,
				Arrays.compare(transitions.get(1).getState().getPolje(), new int[] { 8, 1, 2, 3, 4, 0, 6, 7, 5 }));
	}

	@Test
	public void testNoZero() {
		int[] stanje = { 8, 1, 2, 3, 4, 5, 6, 7, 10 };
		KonfiguracijaSlagalice konf = new KonfiguracijaSlagalice(stanje);
		Slagalica sl = new Slagalica(konf);

		List<Transition<KonfiguracijaSlagalice>> transitions = sl.apply(konf);

		assertEquals(0, transitions.size());

	}
}
