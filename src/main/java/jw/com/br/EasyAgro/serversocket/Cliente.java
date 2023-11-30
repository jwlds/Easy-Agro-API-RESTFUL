package jw.com.br.EasyAgro.serversocket;

import org.springframework.stereotype.Service;

import java.io.*;
import java.net.Socket;

@Service
public class Cliente {

    public boolean iniciarCliente(String cpf) throws IOException {
        String host = "localhost";
        int porta = 3000;

        try (Socket conexao = new Socket(host, porta);
             ObjectOutputStream transmissor = new ObjectOutputStream(conexao.getOutputStream());
             ObjectInputStream receptor = new ObjectInputStream(conexao.getInputStream())) {

            Parceiro servidor = new Parceiro(conexao, receptor, transmissor);

            TratadoraDeComunicadoDeDesligamento tratadoraDecomunicadoDeDesligamento = new TratadoraDeComunicadoDeDesligamento(servidor);
            tratadoraDecomunicadoDeDesligamento.start();

            // Enviar o CPF para o servidor
            servidor.receba(new EnvioDeCpf(cpf));

            // Receber a resposta do servidor
            servidor.receba(new PedidoDeResultado());

            Comunicado comunicado;
            do {
                comunicado = servidor.espie();
            } while (!(comunicado instanceof Resultado));

            Resultado resultado = (Resultado) servidor.envie();


            servidor.adeus();
            return resultado.getResultado();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}