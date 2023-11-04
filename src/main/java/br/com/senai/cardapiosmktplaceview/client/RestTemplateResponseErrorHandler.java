package br.com.senai.cardapiosmktplaceview.client;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {    	
        return httpResponse.getStatusCode().is4xxClientError()
        		|| httpResponse.getStatusCode().is5xxServerError();
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
    	String body = IOUtils.toString(httpResponse.getBody(), "UTF-8");
    	JSONObject responseJson = new JSONObject(body);
    	StringBuilder errosMsg = new StringBuilder();
    	for (Object erro : responseJson.getJSONArray("erros")) {
    		JSONObject erroJson = (JSONObject)erro;
    		errosMsg.append("  -").append(erroJson.get("mensagem")).append("\n");
    	}
        if (httpResponse.getStatusCode().is5xxServerError()){
        	throw new RuntimeException("Os seguintes erros (servidor) ocorreram: \n" + errosMsg);        	        
        }else if (httpResponse.getStatusCode().is4xxClientError()){
        	throw new RuntimeException("Os seguintes erros (cliente) ocorreram: \n" + errosMsg);
        }
    }
}
