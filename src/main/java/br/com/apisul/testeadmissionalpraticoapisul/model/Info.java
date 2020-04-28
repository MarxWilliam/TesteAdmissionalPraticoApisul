package br.com.apisul.testeadmissionalpraticoapisul.model;

public class Info {
	int andar;
	char elevador;
	char turno;

	public Info() {

	}

	public Info(int andar, char elevador, char turno) {
		this.andar = andar;
		this.elevador = elevador;
		this.turno = turno;
	}

	public int getAndar() {
		return andar;
	}

	public void setAndar(int andar) {
		this.andar = andar;
	}

	public char getElevador() {
		return elevador;
	}

	public void setElevador(char elevador) {
		this.elevador = elevador;
	}

	public char getTurno() {
		return turno;
	}

	public void setTurno(char turno) {
		this.turno = turno;
	}
}
