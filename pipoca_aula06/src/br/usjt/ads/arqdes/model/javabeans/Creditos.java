package br.usjt.ads.arqdes.model.javabeans;

import java.util.Arrays;

public class Creditos {
	private int id;
	private Elenco[] cast;
	private Equipe[] crew;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Elenco[] getCast() {
		return cast;
	}
	public void setCast(Elenco[] cast) {
		this.cast = cast;
	}
	public Equipe[] getCrew() {
		return crew;
	}
	public void setCrew(Equipe[] crew) {
		this.crew = crew;
	}
	@Override
	public String toString() {
		return "Creditos [id=" + id + ", cast=" + Arrays.toString(cast) + ", crew=" + Arrays.toString(crew) + "]";
	}
	
}
