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
@Table(name = "entregas")
@Entity(name = "Entrega")
@ToString
public class Entrega {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)	
	@EqualsAndHashCode.Include
	private Integer id;
	
	@NotBlank(message = "A descrição da carga é obrigatória")
	@Column(name = "descricao")
	private String descricaoDaCarga;
	
	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_motorista")
	@NotNull(message = "O motorista é obrigatório")
	private Motorista motorista;
	
}
