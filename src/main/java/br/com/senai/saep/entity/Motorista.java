package br.com.senai.saep.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Table(name = "motoristas")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "Motorista")
public class Motorista {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	private Integer id;
	
	@Column(name = "nome_completo")
	private String nome_completo;
	
	@Column(name = "cnh")
	private String cnh;

}
