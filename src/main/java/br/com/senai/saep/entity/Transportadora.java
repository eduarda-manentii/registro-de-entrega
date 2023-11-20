package br.com.senai.saep.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Table(name = "transportadoras")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "Transportadora")
public class Transportadora {
	
	@Id
	@Column(name = "email")
	@EqualsAndHashCode.Include
	private String email;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "senha")
	private String senha;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_motorista")
	@NotNull(message = "O motorista é obrigatório.")
	private Motorista motorista;

}
