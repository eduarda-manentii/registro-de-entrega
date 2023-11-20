package br.com.senai.saep.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Table(name = "transportadoras")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "Transportadora")
public class Transportadora {
	
	@Id
	@Column(name = "id")
	@EqualsAndHashCode.Include
	private Integer id;
	
	@Column(name = "login")
	private String email;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "senha")
	private String senha;
	
}
