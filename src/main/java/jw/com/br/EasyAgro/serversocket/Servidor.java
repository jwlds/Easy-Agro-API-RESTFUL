package jw.com.br.EasyAgro.serversocket;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Servidor
{
    public static final String PORTA_PADRAO = "3000";

    public static void main(String[] args) throws IOException {
        String porta = Servidor.PORTA_PADRAO;

        ArrayList<Parceiro> usuarios = new ArrayList<Parceiro>();
        AceitadoraDeConexao aceitadoraDeConexao = null;
        try{
            aceitadoraDeConexao = new AceitadoraDeConexao(porta, usuarios);
            aceitadoraDeConexao.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));

        for(;;){
            System.out.println ("O servidor esta ativo! Para desativa-lo,");
            System.out.println ("use o comando \"desativar\"\n");
            System.out.print   ("> ");

            String comando = teclado.readLine();

            if (comando.toLowerCase().equals("desativar")) {
                synchronized (usuarios) {
                    ComunicadoDeDesligamento comunicadoDeDesligamento = new ComunicadoDeDesligamento();

                    for (Parceiro usuario : usuarios) {
                        try {
                            usuario.receba(comunicadoDeDesligamento);
                            usuario.adeus();
                        } catch (Exception erro) {
                        }
                    }
                }
                System.out.println ("O servidor foi desativado!\n");
                System.exit(0);
            }else{
                System.err.println ("Comando invalido!\n");
            }
        }
    }
}