package br.com.senai.saep.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data @ToString
@Table(name = "motoristas")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "Motorista")
public class Motorista {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
	@ToString.Exclude
	private Integer id;
	
	@Column(name = "nome_completo")
	private String nome_completo;
	
	@Column(name = "cnh")
	@ToString.Exclude
	private String cnh;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_transportadora")
	@NotNull(message = "A transportadora é obrigatória.")
	@ToString.Exclude
	private Transportadora transportadora;
	
}
