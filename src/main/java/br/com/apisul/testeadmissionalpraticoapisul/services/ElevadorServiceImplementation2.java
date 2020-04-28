package br.com.apisul.testeadmissionalpraticoapisul.services;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.com.apisul.testeadmissionalpraticoapisul.dao.LeJSON;
import br.com.apisul.testeadmissionalpraticoapisul.model.Elevador;
import br.com.apisul.testeadmissionalpraticoapisul.model.Info;

public class ElevadorServiceImplementation2 implements IElevadorService {

	private static final int ANDARES = 16; // contando de 0 a 15 são 16 andares.
	private static final int ELEVADORES = 5;

	List<Info> listaInfo;

	DecimalFormat df = new DecimalFormat("#,###.00");

	public ElevadorServiceImplementation2() {
		try {
			File f = new File(".");
			File file = new File(f.getCanonicalPath() + "\\src\\resources\\input.json");
			LeJSON leJSON = new LeJSON();
			listaInfo = leJSON.leJSON(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// contador de utilização dos elevadores // preenche mapElevadores ('A', 0) com
	// letras e zeros
	private Map<Character, Integer> preencheElevadores() {
		Map<Character, Integer> mapElevadores = null;
		mapElevadores = new LinkedHashMap<Character, Integer>();
		mapElevadores.put('A', 0);
		mapElevadores.put('B', 0);
		mapElevadores.put('C', 0);
		mapElevadores.put('D', 0);
		mapElevadores.put('E', 0);
		return mapElevadores;
	}

	private Map<Character, Integer> preencheTurnos() {
		Map<Character, Integer> countMapTurnos = null;
		countMapTurnos = new LinkedHashMap<Character, Integer>();
		countMapTurnos.put('M', 0);
		countMapTurnos.put('V', 0);
		countMapTurnos.put('N', 0);

		return countMapTurnos;
	}

	public void printTurnos(Map<Character, Integer> countMapTurnos) {
		System.out.println();
		for (Character c : countMapTurnos.keySet()) {
			System.out.println("Turnos: " + c + " : " + countMapTurnos.get(c));
		}
	}

	public static <Character, Integer> void orderByValueDesc(Map<Character, Integer> mapElevadores2) {
		List<Map.Entry<Character, Integer>> entries = new ArrayList<>(mapElevadores2.entrySet());

		Collections.sort(entries, new Comparator<Map.Entry<Character, Integer>>() {
			@Override
			public int compare(Map.Entry<Character, Integer> a, Map.Entry<Character, Integer> b) {
				return (int) b.getValue() - (int) a.getValue();
			}
		});

		mapElevadores2.clear();
		for (Map.Entry<Character, Integer> e : entries) {
			mapElevadores2.put(e.getKey(), e.getValue());
		}
	}

	public static <Character, Integer> void orderByValueAsc(Map<Character, Integer> mapElevadores2) {
		List<Map.Entry<Character, Integer>> entries = new ArrayList<>(mapElevadores2.entrySet());

		Collections.sort(entries, new Comparator<Map.Entry<Character, Integer>>() {
			@Override
			public int compare(Map.Entry<Character, Integer> a, Map.Entry<Character, Integer> b) {
				return (int) a.getValue() - (int) b.getValue();
			}
		});

		mapElevadores2.clear();
		for (Map.Entry<Character, Integer> e : entries) {
			mapElevadores2.put(e.getKey(), e.getValue());
		}
	}

	public static <K, Integer> void orderByValueAscInt(Map<Integer, Integer> mapAndares) {
		List<Map.Entry<Integer, Integer>> entries = new ArrayList<>(mapAndares.entrySet());

		Collections.sort(entries, new Comparator<Map.Entry<Integer, Integer>>() {
			@Override
			public int compare(Map.Entry<Integer, Integer> a, Map.Entry<Integer, Integer> b) {
				return (int) a.getValue() - (int) b.getValue();
			}
		});

		mapAndares.clear();
		for (Map.Entry<Integer, Integer> e : entries) {
			mapAndares.put(e.getKey(), e.getValue());
		}
	}

	@Override
	public List<Integer> andarMenosUtilizado() {
		LinkedHashMap<Integer, Integer> andares = new LinkedHashMap<>();

		for (int i = 0; i < ANDARES; i++) {
			andares.put(i, 0);
		}

		int soma = 0;

		for (Info info : listaInfo) {
			int valor = andares.get(info.getAndar());
			valor++;
			andares.put(info.getAndar(), valor);
			soma++;
		}
		float media = soma / (float) ANDARES;
		orderByValueAscInt(andares);

		System.out.println("Média de utilização por andar: " + media);
		List<Integer> listaAndares = new ArrayList<Integer>();
		for (Integer in : andares.keySet()) {
			if (andares.get(in) < media) {
				listaAndares.add(in);
			}
		}
		return listaAndares;
	}

	@Override
	public List<Character> elevadorMaisFrequentado() {
		int[] countAndares = new int[ANDARES];
		for (int i = 0; i < ANDARES; i++) {
			countAndares[i] = 0;
		}
		int soma = 0;

		Map<Character, Integer> mapElevadores = preencheElevadores(); // contador de utilização dos elevadores //
																		// preenche mapElevadores

		for (Info info : listaInfo) {
			Integer valor = (Integer) mapElevadores.get(info.getElevador());
			mapElevadores.put(info.getElevador(), ++valor);
			// System.out.println(info.getElevador() + ": " +
			// mapElevadores.get(info.getElevador()) + ": " + valor);
		}

		float media = listaInfo.size() / (float) ELEVADORES; // quant utilização/quant elevadores
		System.out.println("Média de utilização por elevador: " + media);
		List<Character> listaElevadores = new ArrayList<Character>();

		orderByValueDesc(mapElevadores);

		for (Character c : mapElevadores.keySet()) {
			if ((int) mapElevadores.get(c) > media) {
				listaElevadores.add(c);
			}
		}

		mapElevadores.clear();
		return listaElevadores;
	}

	@Override
	public List<Character> periodoMaiorFluxoElevadorMaisFrequentado() {
		// contador de turnos // preenche countMapTurnos com 0s nos turnos ('M', 0)
		Map<Character, Integer> countMapTurnos = preencheTurnos();
		LinkedHashMap<Character, Character> periodo = new LinkedHashMap<Character, Character>();

		for (Character e : this.elevadorMaisFrequentado()) { // Letra elevador mais frequentado
			for (Info info : listaInfo) { // todas as entradas do JSON
				if (info.getElevador() == e) {
					int valor = countMapTurnos.get(info.getTurno());
					countMapTurnos.put(info.getTurno(), ++valor);
				}
			}
			Character maxT = null;
			for (Character turno : countMapTurnos.keySet()) {
				if (maxT == null) {
					maxT = turno;
				} else if (countMapTurnos.get(turno) > countMapTurnos.get(maxT)) {
					maxT = turno;
				}
			}
			periodo.put(e, maxT); // t de turno
		}

		List<Character> listaTurnos = new ArrayList<>();

		for (Character t : periodo.keySet()) {
			listaTurnos.add(periodo.get(t));
		}

		return listaTurnos;
	}

	@Override
	public List<Character> elevadorMenosFrequentado() {
		int[] countAndares = new int[ANDARES];
		for (int i = 0; i < ANDARES; i++) {
			countAndares[i] = 0;
		}
		int soma = 0;

		// contador de utiliza��o dos elevadores // preenche mapElevadores ('A', 0) com
		// letras e zeros
		Map<Character, Integer> mapElevadores = preencheElevadores();

		for (Info info : listaInfo) {
			Integer valor = (Integer) mapElevadores.get(info.getElevador());
			mapElevadores.put(info.getElevador(), ++valor);
			// System.out.println(info.getElevador() + ": " +
			// mapElevadores.get(info.getElevador()) + ": " + valor);
		}

		float media = listaInfo.size() / (float) ELEVADORES; // quant utilização/quant elevadores
		System.out.println("Média de utilização por elevador: " + media);
		List<Character> listaElevadores = new ArrayList<Character>();

		orderByValueAsc(mapElevadores);

		for (Character c : mapElevadores.keySet()) {
			if ((int) mapElevadores.get(c) < media) {
				listaElevadores.add(c);
			}
		}

		return listaElevadores;
	}

	@Override
	public List<Character> periodoMenorFluxoElevadorMenosFrequentado() {
		LinkedHashMap<Character, Character> periodo = new LinkedHashMap<Character, Character>();
		for (Character e : this.elevadorMenosFrequentado()) { // Letra elevador mais frequentado
			// contador de turnos // preenche countMapTurnos com 0s nos turnos ('M', 0)
			Map<Character, Integer> countMapTurnos = preencheTurnos();
			for (Info info : listaInfo) { // todas as entradas do JSON
				if (info.getElevador() == e) {
					Integer valor = countMapTurnos.get(info.getTurno());
					valor++;
					countMapTurnos.put(info.getTurno(), valor);
				}
			}
			Character maxT = null;
			for (Character turno : countMapTurnos.keySet()) {
				if (maxT == null) {
					maxT = turno;
				} else if (countMapTurnos.get(turno) < countMapTurnos.get(maxT)) {
					maxT = turno;
				}
			}
			periodo.put(e, maxT); // t de turno
			countMapTurnos.clear();
		}

		List<Character> listaTurnos = new ArrayList<>();

		for (Character t : periodo.keySet()) {
			listaTurnos.add(periodo.get(t));
		}

		return listaTurnos;
	}

	@Override
	public List<Character> periodoMaiorUtilizacaoConjuntoElevadores() {
		// contador de turnos // preenche countMapTurnos com 0s nos turnos ('M', 0)
		Map<Character, Integer> countMapTurnos = preencheTurnos();
		float media = listaInfo.size() / 3.0f; // quant utiliza��o/quant turnos

		for (Info info : listaInfo) { // todas as entradas do JSON
			int valor = countMapTurnos.get(info.getTurno());
			countMapTurnos.put(info.getTurno(), ++valor);
		}

		orderByValueDesc(countMapTurnos);

		List<Character> listaTurnos = new ArrayList<>();
		for (Character c : countMapTurnos.keySet()) {
			if ((int) countMapTurnos.get(c) > media) {
				listaTurnos.add(c);
			}
		}
		countMapTurnos.clear();

		return listaTurnos;
	}

	@Override
	public float percentualDeUsoElevadorA() {
		// contador de utiliza��o dos elevadores // preenche mapElevadores ('A', 0) com
		// letras e zeros
		Map<Character, Integer> mapElevadores = preencheElevadores();

		for (Info info : listaInfo) {
			if (info.getElevador() == 'A') {
				Integer valorA = 1;
				valorA += (Integer) mapElevadores.get('A');
				mapElevadores.put('A', valorA);
			}
		}

		BigDecimal bd = new BigDecimal(((mapElevadores.get('A') * 100) / (float) listaInfo.size())).setScale(2,
				RoundingMode.HALF_UP);
		float percentA = bd.floatValue();

		mapElevadores.clear();

		return percentA;
	}

	@Override
	public float percentualDeUsoElevadorB() {
		// contador de utiliza��o dos elevadores // preenche mapElevadores ('A', 0) com
		// letras e zeros
		Map<Character, Integer> mapElevadores = preencheElevadores();

		for (Info info : listaInfo) {
			if (info.getElevador() == 'B') {
				Integer valorB = 1;
				valorB += (Integer) mapElevadores.get('B');
				mapElevadores.put('B', valorB);
			}
		}

		BigDecimal bd = new BigDecimal(((mapElevadores.get('B') * 100) / (float) listaInfo.size())).setScale(2,
				RoundingMode.HALF_UP);
		float percentB = bd.floatValue();

		mapElevadores.clear();

		return percentB;
	}

	@Override
	public float percentualDeUsoElevadorC() {
		// contador de utilização dos elevadores // preenche mapElevadores ('A', 0) com
		// letras e zeros
		Map<Character, Integer> mapElevadores = preencheElevadores();

		for (Info info : listaInfo) {
			if (info.getElevador() == 'C') {
				Integer valorC = 1;
				valorC += (Integer) mapElevadores.get('C');
				mapElevadores.put('C', valorC);
			}
		}

		BigDecimal bd = new BigDecimal(((mapElevadores.get('C') * 100) / (float) listaInfo.size())).setScale(2,
				RoundingMode.HALF_UP);
		float percentC = bd.floatValue();

		mapElevadores.clear();

		return percentC;
	}

	@Override
	public float percentualDeUsoElevadorD() {
		// contador de utiliza��o dos elevadores // preenche mapElevadores ('A', 0) com
		// letras e zeros
		Map<Character, Integer> mapElevadores = preencheElevadores();

		for (Info info : listaInfo) {
			if (info.getElevador() == 'D') {
				Integer valorD = 1;
				valorD += (Integer) mapElevadores.get('D');
				mapElevadores.put('D', valorD);
			}
		}

		BigDecimal bd = new BigDecimal(((mapElevadores.get('D') * 100) / (float) listaInfo.size())).setScale(2,
				RoundingMode.HALF_UP);
		float percentD = bd.floatValue();

		mapElevadores.clear();

		return percentD;
	}

	@Override
	public float percentualDeUsoElevadorE() {
		// contador de utilização dos elevadores // preenche mapElevadores ('A', 0) com
		// letras e zeros
		Map<Character, Integer> mapElevadores = preencheElevadores();

		for (Info info : listaInfo) {
			if (info.getElevador() == 'E') {
				Integer valorE = 1;
				valorE += (Integer) mapElevadores.get('E');
				mapElevadores.put('E', valorE);
			}
		}

		BigDecimal bd = new BigDecimal(((mapElevadores.get('E') * 100) / (float) listaInfo.size())).setScale(2,
				RoundingMode.HALF_UP);
		float percentE = bd.floatValue();

		mapElevadores.clear();

		return percentE;
	}
}
