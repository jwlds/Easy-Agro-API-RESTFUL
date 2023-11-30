package jw.com.br.EasyAgro.serversocket;

public class EnvioDeCpf extends Comunicado{
    private String cpf;

    public EnvioDeCpf(String cpf){
        this.cpf = cpf;
    }
    public String getCpf(){
        return this.cpf;
    }

    @Override
    public String toString(){
        return this.cpf;
    }
}