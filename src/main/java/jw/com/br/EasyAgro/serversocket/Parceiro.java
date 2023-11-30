package jw.com.br.EasyAgro.serversocket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.Semaphore;

public class Parceiro {

    private Socket conexao; //conexão com o servidor
    private ObjectInputStream receptor; //microfone
    private ObjectOutputStream transmissor; //autofalante
    private Comunicado proximoComunicado = null;
    private Semaphore mutEx = new Semaphore(1, true);

    public Parceiro(Socket conexao, ObjectInputStream receptor, ObjectOutputStream transmissor)
            throws Exception{
        if (conexao == null) throw new Exception("Conexão ausente.");
        if (receptor == null) throw new Exception("Receptor ausente.");
        if (transmissor == null) throw new Exception("Transmissor ausente.");

        this.conexao = conexao;
        this.transmissor = transmissor;
        this.receptor = receptor;
    }

    //por mais que se chame "RECEBA!" serve para TRANSMITIR para o PARCEIRO
    //se eu estou no programa do CLIENTE e uso "servidor.receba", o cliente esta mandando para o servidor
    public void receba(Comunicado comunicado) throws Exception{
        try{
            this.transmissor.writeObject(comunicado);//aqui talvez seja interessante mudar para readLine()
            this.transmissor.flush();
        }catch(IOException erro){
            throw new Exception("Erro na transmissão!");
        }
    }
    public Comunicado espie() throws Exception{
        try{
            this.mutEx.acquireUninterruptibly();
            if(this.proximoComunicado == null) {
                this.proximoComunicado = (Comunicado)this.receptor.readObject();// aqui talvez seja interessante mudar para readLine()
            }
            this.mutEx.release();
            return this.proximoComunicado;

        }catch(Exception erro){
            throw new Exception("Erro de recepção");
        }
    }

    public Comunicado envie() throws Exception{
        try{
            if(this.proximoComunicado == null) {
                this.proximoComunicado = (Comunicado)this.receptor.readObject();// aqui talvez seja interessante mudar para readLine()
            }
            Comunicado aux = this.proximoComunicado;
            this.proximoComunicado = null;
            return aux;

        }catch(Exception erro){
            throw new Exception("Erro de recepção");
        }
    }

    public void adeus()throws Exception{
        try{
            this.receptor.close();
            this.conexao.close();
            this.transmissor.close();

        }catch (Exception erro){}
    }

}