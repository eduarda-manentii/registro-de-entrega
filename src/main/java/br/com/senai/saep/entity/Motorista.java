package br.com.senai.saep.entity;

import org.springframework.stereotype.Component;

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

@Data
@Table(name = "motoristas")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "Motorista")
@Component
public class Motorista {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
    @Column(name = "id")
	private Integer id;
	
	@Column(name = "nome_completo")
	private String nomeCompleto;
	
	@Column(name = "cnh")
	private String cnh;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_transportadora")
	@NotNull(message = "A transportadora é obrigatória.")
	private Transportadora transportadora;

	@Override
	public String toString() {
		return nomeCompleto;
	}
	
}
