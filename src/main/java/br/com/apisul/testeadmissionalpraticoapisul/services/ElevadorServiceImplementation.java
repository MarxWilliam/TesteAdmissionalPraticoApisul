package br.com.apisul.testeadmissionalpraticoapisul.services;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Currency;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import br.com.apisul.testeadmissionalpraticoapisul.dao.LeJSON;
import br.com.apisul.testeadmissionalpraticoapisul.model.Elevador;
import br.com.apisul.testeadmissionalpraticoapisul.model.Info;

public class ElevadorServiceImplementation implements IElevadorService {

	private static final int ANDARES = 16; // contando de 0 a 15s�o 16 andares.
	private static final int ELEVADORES = 5;

	List<Info> listaInfo;
	Map<Character, Integer> mapElevadores; // contador de utilização dos elevadores
	Map<Character, Integer> countMapTurnos; // contador de turnos

	List<Integer> andarMenosUtilizado;
	List<Character> elevadorMaisFrequentado;
	List<Character> periodoMaiorFluxoElevadorMaisFrequentado;
	List<Character> elevadorMenosFrequentado;
	List<Character> periodoMenorFluxoElevadorMenosFrequentado;
	List<Character> periodoMaiorUtilizacaoConjuntoElevadores;

	float percentA = 0;
	float percentB = 0;
	float percentC = 0;
	float percentD = 0;
	float percentE = 0;

	NumberFormat dc;

	public ElevadorServiceImplementation() {
		try {
			File f = new File(".");
			File file = new File(f.getCanonicalPath() + "\\src\\resources\\input.json");
			LeJSON leJSON = new LeJSON();
			listaInfo = leJSON.leJSON(file);

			this.andarMenosUtilizado = andarMenosUtilizadoImp();
			this.elevadorMaisFrequentado = elevadorMaisFrequentadoImp();
			this.periodoMaiorFluxoElevadorMaisFrequentado = periodoMaiorFluxoElevadorMaisFrequentadoImp();
			this.elevadorMenosFrequentado = elevadorMenosFrequentadoImp();
			this.periodoMenorFluxoElevadorMenosFrequentado = periodoMenorFluxoElevadorMenosFrequentadoImp();
			this.periodoMaiorUtilizacaoConjuntoElevadores = periodoMaiorUtilizacaoConjuntoElevadoresImp();

			calculaPercentualElevadores();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void preencheElevadores() {
		mapElevadores = new LinkedHashMap<Character, Integer>();
		mapElevadores.put('A', 0);
		mapElevadores.put('B', 0);
		mapElevadores.put('C', 0);
		mapElevadores.put('D', 0);
		mapElevadores.put('E', 0);
	}

	private void preencheTurnos() {
		countMapTurnos = new LinkedHashMap<Character, Integer>();
		countMapTurnos.put('M', 0);
		countMapTurnos.put('V', 0);
		countMapTurnos.put('N', 0);
	}

	@Override
	public List<Integer> andarMenosUtilizado() {
		return this.andarMenosUtilizado;
	}

	@Override
	public List<Character> elevadorMaisFrequentado() {
		return elevadorMaisFrequentado;
	}

	@Override
	public List<Character> periodoMaiorFluxoElevadorMaisFrequentado() {
		return this.periodoMaiorFluxoElevadorMaisFrequentado;
	}

	@Override
	public List<Character> elevadorMenosFrequentado() {
		return elevadorMenosFrequentado;
	}

	@Override
	public List<Character> periodoMenorFluxoElevadorMenosFrequentado() {
		return this.periodoMenorFluxoElevadorMenosFrequentado;
	}

	@Override
	public List<Character> periodoMaiorUtilizacaoConjuntoElevadores() {
		return this.periodoMaiorUtilizacaoConjuntoElevadores;
	}

	@Override
	public float percentualDeUsoElevadorA() {
		return this.percentA;
	}

	@Override
	public float percentualDeUsoElevadorB() {
		return this.percentB;
	}

	@Override
	public float percentualDeUsoElevadorC() {
		return this.percentC;
	}

	@Override
	public float percentualDeUsoElevadorD() {
		return this.percentD;
	}

	@Override
	public float percentualDeUsoElevadorE() {
		return this.percentE;
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

	public List<Integer> andarMenosUtilizadoImp() {
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

	public List<Character> elevadorMaisFrequentadoImp() {
		int[] countAndares = new int[ANDARES];
		for (int i = 0; i < ANDARES; i++) {
			countAndares[i] = 0;
		}
		int soma = 0;

		preencheElevadores(); // preenche mapElevadores

		for (Info info : listaInfo) {
			Integer valor = (Integer) mapElevadores.get(info.getElevador());
			this.mapElevadores.put(info.getElevador(), ++valor);
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

	public List<Character> periodoMaiorFluxoElevadorMaisFrequentadoImp() {
		preencheTurnos(); // preenche countMapTurnos
		LinkedHashMap<Character, Character> periodo = new LinkedHashMap<Character, Character>();

		for (Character e : this.elevadorMaisFrequentado) { // Letra elevador mais frequentado
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
			countMapTurnos.clear();
			preencheTurnos();
		}

		List<Character> listaTurnos = new ArrayList<>();

		for (Character t : periodo.keySet()) {
			listaTurnos.add(periodo.get(t));
		}

		return listaTurnos;
	}

	public List<Character> elevadorMenosFrequentadoImp() {
		int[] countAndares = new int[ANDARES];
		for (int i = 0; i < ANDARES; i++) {
			countAndares[i] = 0;
		}
		int soma = 0;

		preencheElevadores(); // preenche mapElevadores

		for (Info info : listaInfo) {
			Integer valor = (Integer) mapElevadores.get(info.getElevador());
			this.mapElevadores.put(info.getElevador(), ++valor);
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

		mapElevadores.clear();

		return listaElevadores;
	}

	public List<Character> periodoMenorFluxoElevadorMenosFrequentadoImp() {
		LinkedHashMap<Character, Character> periodo = new LinkedHashMap<Character, Character>();
		for (Character e : this.elevadorMenosFrequentado) { // Letra elevador mais frequentado
			preencheTurnos(); // preenche countMapTurnos
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
				} else if (countMapTurnos.get(turno) < countMapTurnos.get(maxT)) {
					maxT = turno;
				}
			}
			periodo.put(new String(e.toString()).charAt(0), maxT); // t de turno
			countMapTurnos.clear();
		}

		List<Character> listaTurnos = new ArrayList<>();

		for (Character t : periodo.keySet()) {
			listaTurnos.add(periodo.get(t));
		}

		return listaTurnos;
	}

	public List<Character> periodoMaiorUtilizacaoConjuntoElevadoresImp() {

		float media = listaInfo.size() / 3.0f; // quant utilização/quant turnos

		preencheTurnos(); // preenche countMapTurnos com 0s nos turnos

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

	public void calculaPercentualElevadores() {
		preencheElevadores();

		for (Info info : listaInfo) {
			System.out.println(
					"Andar: " + info.getAndar() + " Elevador: " + info.getElevador() + " Turno: " + info.getTurno());
			switch (info.getElevador()) {
			case 'A':
				Integer valorA = 1;
				valorA += (Integer) mapElevadores.get('A');
				mapElevadores.put('A', valorA);
				break;
			case 'B':
				Integer valorB = 1;
				valorB += (Integer) mapElevadores.get('B');
				mapElevadores.put('B', valorB);
				break;
			case 'C':
				Integer valorC = 1;
				valorC += (Integer) mapElevadores.get('C');
				mapElevadores.put('C', valorC);
				break;
			case 'D':
				Integer valorD = 1;
				valorD += (Integer) mapElevadores.get('D');
				mapElevadores.put('D', valorD);
				break;
			case 'E':
				Integer valorE = 1;
				valorE += (Integer) mapElevadores.get('E');
				mapElevadores.put('E', valorE);
				break;
			default:
				System.out.println("Erro: Elevador n�o cadastrado.");
			}
		}

		System.out.println("Calculo ddo percentual:\n");
		for (Character e : mapElevadores.keySet()) {
			System.out.println(e + ": " + mapElevadores.get(e));
		}

		BigDecimal bd = new BigDecimal(((mapElevadores.get('A') * 100) / (float) listaInfo.size())).setScale(2,
				RoundingMode.HALF_UP);
		this.percentA = bd.floatValue();
		bd = new BigDecimal(((mapElevadores.get('B') * 100) / (float) listaInfo.size())).setScale(2,
				RoundingMode.HALF_UP);
		this.percentB = bd.floatValue();
		bd = new BigDecimal(((mapElevadores.get('C') * 100) / (float) listaInfo.size())).setScale(2,
				RoundingMode.HALF_UP);
		this.percentC = bd.floatValue();
		bd = new BigDecimal(((mapElevadores.get('D') * 100) / (float) listaInfo.size())).setScale(2,
				RoundingMode.HALF_UP);
		this.percentD = bd.floatValue();
		bd = new BigDecimal(((mapElevadores.get('E') * 100) / (float) listaInfo.size())).setScale(2,
				RoundingMode.HALF_UP);
		this.percentE = bd.floatValue();

		mapElevadores.clear();
	}

	public float f2Decimal(float value) {
		DecimalFormat fmt = new DecimalFormat("0,00");
		String string = fmt.format(value);
		String[] part = string.split("[,]");
		String string2 = part[0] + "." + part[1];
		float v = Float.parseFloat(string2);
		return v;
	}
}
