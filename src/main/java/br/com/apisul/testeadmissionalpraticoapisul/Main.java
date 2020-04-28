package br.com.apisul.testeadmissionalpraticoapisul;

import java.util.List;

import br.com.apisul.testeadmissionalpraticoapisul.services.ElevadorServiceImplementation;
import br.com.apisul.testeadmissionalpraticoapisul.services.ElevadorServiceImplementation2;
import br.com.apisul.testeadmissionalpraticoapisul.services.IElevadorService;

public class Main {
	public static void main(String[] args) {
		IElevadorService elevador = new ElevadorServiceImplementation2();

		System.out.println(
				"Deve retornar uma List contendo o(s) andar(es) menos utilizado(s). \n(Menores que a media de Utilização por andar)");
		List<Integer> andarMenosUtilizado = elevador.andarMenosUtilizado();
		int size = andarMenosUtilizado.size();
		System.out.print("\t -> ");
		for (int i = 0; i < size; i++) {
			if (i != size - 1) {
				System.out.print(andarMenosUtilizado.get(i) + ", ");
			} else {
				System.out.println(andarMenosUtilizado.get(i));
			}
		}

		System.out.println();

		System.out.println(
				"Deve retornar uma List contendo o(s) elevador(es) mais frequentado(s). \n(Maior que a media de Utilização por elevador)");
		List<Character> elevadorMaisFrequentado = elevador.elevadorMaisFrequentado();
		int sizeElevador = elevadorMaisFrequentado.size();
		System.out.print("\t-> ");
		for (int i = 0; i < sizeElevador; i++) {
			if (i != sizeElevador - 1) {
				System.out.print(elevadorMaisFrequentado.get(i) + ", ");
			} else {
				System.out.println(elevadorMaisFrequentado.get(i));
			}
		}

		System.out.println();

		System.out.println(
				"Deve retornar uma List contendo o período de maior fluxo de cada um dos elevadores mais frequentados (se houver mais de um), (Obs: Retorna apenas um periodo por elevador podendo se repetir, pode-se fazer diferente e retornar mais de um por elevador)"
						+ "\n(Maior que a media de Utilização por turno)");
		List<Character> periodoMaiorFluxoElevadorMaisFrequentado = elevador.periodoMaiorFluxoElevadorMaisFrequentado();
		int sizeMaiorFluxo = elevadorMaisFrequentado.size();
		System.out.print("\t-> ");
		for (int i = 0; i < sizeMaiorFluxo; i++) {
			if (i != sizeMaiorFluxo - 1) {
				System.out.print(periodoMaiorFluxoElevadorMaisFrequentado.get(i) + ", ");
			} else {
				System.out.println(periodoMaiorFluxoElevadorMaisFrequentado.get(i));
			}
		}

		System.out.println();

		System.out.println("Deve retornar uma List contendo o(s) elevador(es) menos frequentado(s)."
				+ " \n(Menor que a media de Utilização por elevador)");
		List<Character> elevadorMenosFrequentado = elevador.elevadorMenosFrequentado();
		int sizeMenosFreq = elevadorMenosFrequentado.size();
		System.out.print("\t-> ");
		for (int i = 0; i < sizeMenosFreq; i++) {
			if (i != sizeMenosFreq - 1) {
				System.out.print(elevadorMenosFrequentado.get(i) + ", ");
			} else {
				System.out.println(elevadorMenosFrequentado.get(i));
			}
		}

		System.out.println();

		System.out.println(
				"Deve retornar uma List contendo o per�odo de menor fluxo de cada um dos elevadores menos frequentados "
						+ "(se houver mais de um), (Obs: Retorna apenas um periodo por elevador podendo se repetir, pode-se fazer diferente e retornar mais de um por elevador). \n(Menor que a media de Utilização por elevador)");
		List<Character> periodoMenorFluxoElevadorMenosFrequentado = elevador
				.periodoMenorFluxoElevadorMenosFrequentado();
		int sizeMenorFluxo = periodoMenorFluxoElevadorMenosFrequentado.size();
		System.out.print("\t-> ");
		for (int i = 0; i < sizeMenorFluxo; i++) {
			if (i != sizeMenorFluxo - 1) {
				System.out.print(periodoMenorFluxoElevadorMenosFrequentado.get(i) + ", ");
			} else {
				System.out.println(periodoMenorFluxoElevadorMenosFrequentado.get(i));
			}
		}

		System.out.println();

		System.out.println(
				"Deve retornar uma List contendo o(s) periodo(s) de maior utilização do conjunto de elevadores. "
						+ "\n(Maior que a media de Utilização por turno)");
		List<Character> periodoMaiorUtilizacaoConjuntoElevadores = elevador.periodoMaiorUtilizacaoConjuntoElevadores();
		int sizePeriodoMaiorUtilizacao = periodoMaiorUtilizacaoConjuntoElevadores.size();
		System.out.print("\t-> ");
		for (int i = 0; i < sizePeriodoMaiorUtilizacao; i++) {
			if (i != sizePeriodoMaiorUtilizacao - 1) {
				System.out.print(periodoMaiorUtilizacaoConjuntoElevadores.get(i) + ", ");
			} else {
				System.out.println(periodoMaiorUtilizacaoConjuntoElevadores.get(i));
			}
		}

		System.out.println();

		System.out.println(
				"Deve retornar um float (duas casas decimais) contendo o percentual de uso do elevador A em relação a todos os serviços prestados.");
		System.out.print("\t-> " + elevador.percentualDeUsoElevadorA());

		System.out.println();

		System.out.println(
				"Deve retornar um float (duas casas decimais) contendo o percentual de uso do elevador B em relação a todos os serviços prestados.");
		System.out.print("\t-> " + elevador.percentualDeUsoElevadorB());

		System.out.println();

		System.out.println(
				"Deve retornar um float (duas casas decimais) contendo o percentual de uso do elevador C em relação a todos os serviços prestados.");
		System.out.print("\t-> " + elevador.percentualDeUsoElevadorC());

		System.out.println();

		System.out.println(
				"Deve retornar um float (duas casas decimais) contendo o percentual de uso do elevador D em relação a todos os serviços prestados.");
		System.out.print("\t-> " + elevador.percentualDeUsoElevadorD());

		System.out.println();

		System.out.println(
				"Deve retornar um float (duas casas decimais) contendo o percentual de uso do elevador E em relação a todos os serviços prestados.");
		System.out.print("\t-> " + elevador.percentualDeUsoElevadorE());

	}
}