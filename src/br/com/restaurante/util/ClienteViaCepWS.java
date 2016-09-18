package br.com.restaurante.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.restaurante.model.EnderecoModel;
import br.com.topsys.web.util.TSFacesUtil;

public class ClienteViaCepWS {

    public static EnderecoModel buscarCep(String cep) {
    	
        String json;
        EnderecoModel enderecoModel = null;

        try {
        	
        	URL url = new URL("http://viacep.com.br/ws/"+ cep +"/json");
            
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(5000); //set timeout to 5 seconds
            
            InputStream is = urlConnection.getInputStream();
            
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            StringBuilder jsonSb = new StringBuilder();

            br.lines().forEach(l -> jsonSb.append(l.trim()));

            json = jsonSb.toString();
            
            Map<String,String> mapa = new HashMap<>();

            Matcher matcher = Pattern.compile("\"\\D.*?\": \".*?\"").matcher(json);
            
            while (matcher.find()) {
                String[] group = matcher.group().split(":");
                mapa.put(group[0].replaceAll("\"", "").trim(), group[1].replaceAll("\"", "").trim());
            }
            
            enderecoModel = new EnderecoModel();
            
            enderecoModel.setLogradouro(mapa.get("logradouro"));
            enderecoModel.setComplemento(mapa.get("complemento"));
            enderecoModel.setBairro(mapa.get("bairro"));
            enderecoModel.setLocalidade(mapa.get("localidade"));
            
        } catch (SocketTimeoutException e) {
        	   
        	TSFacesUtil.addErrorMessage("Serviço indisponível. Verifique sua conexão com a internet!");
        	
        } catch (Exception e) {
            
        	TSFacesUtil.addErrorMessage("Serviço indisponível. Verifique sua conexão com a internet!");
        	
        }
        	
        return enderecoModel;
    }

    public static void main(String[] args) throws IOException {
        
    	/*String json = buscarCep("40435365");
    	
        System.out.println(json);

        Map<String,String> mapa = new HashMap<>();

        Matcher matcher = Pattern.compile("\"\\D.*?\": \".*?\"").matcher(json);
        while (matcher.find()) {
            String[] group = matcher.group().split(":");
            mapa.put(group[0].replaceAll("\"", "").trim(), group[1].replaceAll("\"", "").trim());
        }

        System.out.println(mapa);*/
    }
}