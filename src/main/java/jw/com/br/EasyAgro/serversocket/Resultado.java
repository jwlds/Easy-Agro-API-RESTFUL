package jw.com.br.EasyAgro.serversocket;

public class Resultado extends Comunicado{

    private boolean resultado;

    public Resultado(boolean resultado){
        this.resultado = resultado;
    }
    public boolean getResultado(){
        return this.resultado;
    }
    @Override
    public String toString(){
        return ""+this.resultado;
    }
}