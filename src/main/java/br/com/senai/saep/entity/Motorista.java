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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "motoristas")
@Entity(name = "Motorista")
@ToString
public class Motorista {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@EqualsAndHashCode.Include
	private Integer id;
	
	@NotBlank(message = "O nome do motorista é obrigatório")
	@Column(name = "nomeCompleto")
	private String nome;
	
	@NotBlank(message = "A CNH do motorista é obrigatória")
	@Column(name = "cnh")
	private String cnh;
	
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_transportadora")
	@NotNull(message = "A transportadora é obrigatória")
	private Transportadora transportadora;

}
