package br.com.senai.saep.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.senai.saep.entity.Motorista;
import br.com.senai.saep.repository.MotoristasRepository;

@Service
public class MotoristaService {
	
	@Autowired
	private MotoristasRepository repository;
	
	public Motorista salvar(Motorista motorista) {
		return repository.save(motorista);
	}
	
	 public List<Motorista> buscarPorTransportadora(Integer idDaTransportadora) {
		 return (List<Motorista>) repository.buscarPorTransportadora(idDaTransportadora);
    }
	 
	 public List<Motorista> listarPorNome(String nome) {
		 return (List<Motorista>) repository.ListarrPorNome("%" + nome + "%");
    }
	 
    @Autowired
    public MotoristaService(MotoristasRepository repository) {
        this.repository = repository;
    }

}
