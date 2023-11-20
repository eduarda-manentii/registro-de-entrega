package br.com.senai.saep.view;

import org.springframework.stereotype.Component;

@Component
public class SessionManager {
	
    private Integer idTransportadoraLogada;
    
    private String nomeTransportadoraLogada;

    public Integer getIdTransportadoraLogada() {
        return idTransportadoraLogada;
    }

    public void setIdTransportadoraLogada(Integer idTransportadoraLogada) {
        this.idTransportadoraLogada = idTransportadoraLogada;
    }
    
    public void setNomeTransportadoraLogada(String nomeTransportadoraLogada) {
        this.nomeTransportadoraLogada = nomeTransportadoraLogada;
    }
    
    public String getNomeTransportadoraLogada() {
        return nomeTransportadoraLogada;
    }
}
