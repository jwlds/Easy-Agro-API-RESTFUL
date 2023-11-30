package jw.com.br.EasyAgro.serversocket;

import java.net.*;
public class TratadoraDeComunicadoDeDesligamento extends Thread{

    private Parceiro servidor;
    public TratadoraDeComunicadoDeDesligamento (Parceiro servidor) throws Exception
    {
        if (servidor==null) throw new Exception ("Porta invalida");

        this.servidor = servidor;
    }

    public void run ()
    {
        for(;;){
            try{
                if (this.servidor.espie() instanceof ComunicadoDeDesligamento) System.exit(0);
            }
            catch (Exception erro)
            {}
        }
    }
}