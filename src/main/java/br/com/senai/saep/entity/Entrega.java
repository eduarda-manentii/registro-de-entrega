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
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Table(name = "entregas")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "Entrega")
public class Entrega {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Integer id;
	
	@Column(name = "descricao")
	private String descricao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_motorista")
	@NotNull(message = "O motorista é obrigatório.")
	private Motorista motorista;
	
	@Transient
	public boolean isPersistida() {
		return getId() != null && getId() > 0;
	}

}
