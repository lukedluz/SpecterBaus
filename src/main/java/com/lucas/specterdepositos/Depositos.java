package com.lucas.specterdepositos;

import java.util.List;
import java.util.stream.Collectors;

public class Depositos {

	private String jogador;
	private int tamanho;
	private List<String> itens;
	public Depositos(String jogador, int tamanho, List<String> itens) {
		this.jogador = jogador;
		this.tamanho = tamanho;
		this.itens = itens;
	}
	public String getJogador() {
		return jogador;
	}
	public void setJogador(String jogador) {
		this.jogador = jogador;
	}
	public int getTamanho() {
		return tamanho;
	}
	public void setTamanho(int tamanho) {
		this.tamanho = tamanho;
	}
	public List<String> getItens() {
		return itens;
	}
	public void setItens(List<String> itens) {
		this.itens = itens;
	}
	public static List<Depositos> getAll(){
		return Main.getInstance().cache.values().stream().collect(Collectors.toList());
	}
	public void save() {
		Main.getInstance().data.set("Depositos." + getJogador() + ".Tamanho", getTamanho());
		Main.getInstance().data.set("Depositos." + getJogador() + ".Itens", getItens());
		Main.getInstance().data.saveConfig();
	}
}
