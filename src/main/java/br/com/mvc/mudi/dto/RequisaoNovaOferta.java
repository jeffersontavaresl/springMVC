package br.com.mvc.mudi.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import br.com.mvc.mudi.model.Oferta;

public class RequisaoNovaOferta {

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private Long pedidoId;
	
	// PATTERN PARA DIZER QUE A STRING SÓ RECEBERÁ VALORES COM DUAS CASAS DECIMAIS
	@Pattern(regexp = "^\\d+(\\.\\d{2})?$")
	@NotNull
	private String valor;
	
	
	// PATTERN PARA DIZER QUE A STRING SÓ RECEBERÁ VALORES EM FORMATO DE DATA
	@Pattern(regexp = "^\\d{2}/\\d{2}/\\d{4}$")
	@NotNull
	private String dataDaEntrega;
	
	private String comentario;

	
	public Long getPedidoId() {
		return pedidoId;
	}

	public void setPedidoId(Long pedidoId) {
		this.pedidoId = pedidoId;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getDataDaEntrega() {
		return dataDaEntrega;
	}

	public void setDataDaEntrega(String dataDaEntrega) {
		this.dataDaEntrega = dataDaEntrega;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	
	// MÉTODO PARA TRANSFORMAR A REQUISIÇÃO EM UMA OFERTA PARA SER SALVAR NO BANCO DE DADOS.
	
	public Oferta toOferta() {
		Oferta oferta = new Oferta();
		oferta.setComentario(this.comentario);
		oferta.setDataDaEntrega(LocalDate.parse(this.dataDaEntrega, formatter));
		oferta.setValor(new BigDecimal(this.valor));
		return oferta;
	}
	
	
}
