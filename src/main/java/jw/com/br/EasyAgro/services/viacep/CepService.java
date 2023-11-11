package jw.com.br.EasyAgro.services.viacep;

import com.google.gson.Gson;
import jw.com.br.EasyAgro.dtos.VIaCepDTO;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class CepService {

    public VIaCepDTO findAddressByCep(String cep) throws IOException, InterruptedException {

        String Url = "http://viacep.com.br/ws/" + cep + "/json";
        URI address = URI.create(Url);

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(address).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String enderecoJsonEmString = response.body();

        return new Gson().fromJson(enderecoJsonEmString, VIaCepDTO.class);

    }
}