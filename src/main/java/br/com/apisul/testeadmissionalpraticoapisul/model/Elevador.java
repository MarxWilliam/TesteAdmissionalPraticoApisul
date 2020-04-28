package br.com.apisul.testeadmissionalpraticoapisul.model;

public class Elevador {
	Character caractere;
	int contador;

	public Elevador() {
	}

	public Elevador(Character caractere, int contador) {
		this.caractere = caractere;
		this.contador = contador;
	}

	public Character getCaractere() {
		return caractere;
	}

	public void setCaractere(Character caractere) {
		this.caractere = caractere;
	}

	public int getContador() {
		return contador;
	}

	public void setContador(int contador) {
		this.contador = contador;
	}

}
