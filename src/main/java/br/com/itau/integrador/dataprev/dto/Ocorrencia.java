package br.com.itau.integrador.dataprev.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

public class Ocorrencia implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String descricao;
	private LocalDate data_criacao;
	private LocalDate data_atualizacao;
	private StatusOcorrencia status;
	
	public Ocorrencia(){
		this.data_criacao = LocalDate.now();
		this.data_atualizacao = LocalDate.now();
		this.status = StatusOcorrencia.ABERTO;
	}
	
	public Ocorrencia(final String id, final String descricao){
		this.id = id;
		this.descricao = descricao;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public LocalDate getData_criacao() {
		return data_criacao;
	}
	public void setData_criacao(LocalDate data_criacao) {
		this.data_criacao = data_criacao;
	}
	public LocalDate getData_atualizacao() {
		return data_atualizacao;
	}
	public void setData_atualizacao(LocalDate data_atualizacao) {
		this.data_atualizacao = data_atualizacao;
	}
	public StatusOcorrencia getStatus() {
		return status;
	}
	public void setStatus(StatusOcorrencia status) {
		this.status = status;
	}
	
	@Override
	public String toString(){
		return "Id: " + id + "|Descricao: " + this.descricao;
	}
	
	public static class Builder {
		private Ocorrencia ocorrencia;
		public Builder(){
			ocorrencia = new Ocorrencia();
			ocorrencia.setId(UUID.randomUUID().toString());
		}
		
		public Builder descricao(final String descricao){
			ocorrencia.setDescricao(descricao);
			return this;
		}
	
		public Ocorrencia build(){
			return ocorrencia;
		}
	}
}
