package jw.com.br.EasyAgro.serversocket;

import java.io.*;
import java.net.*;
import java.util.*;

public class SupervisoraDeConexao extends Thread
{
    private boolean             cpfValido;
    private String              cpf;
    private Parceiro            usuario;
    private Socket              conexao;
    private ArrayList<Parceiro> usuarios;

    public SupervisoraDeConexao(Socket conexao, ArrayList<Parceiro> usuarios) throws Exception
    {
        if (conexao==null)
            throw new Exception ("Conexao ausente");

        if (usuarios==null)
            throw new Exception ("Usuarios ausentes");

        this.conexao  = conexao;
        this.usuarios = usuarios;
    }

    public void run ()
    {

        ObjectOutputStream transmissor;
        try
        {
            transmissor = new ObjectOutputStream(this.conexao.getOutputStream());
        }
        catch (Exception erro)
        {
            return;
        }

        ObjectInputStream receptor=null;
        try
        {
            receptor= new ObjectInputStream(this.conexao.getInputStream());
        }
        catch (Exception err0)
        {
            try
            {
                transmissor.close();
            }
            catch (Exception falha)
            {} // so tentando fechar antes de acabar a thread

            return;
        }

        try
        {
            this.usuario = new Parceiro (this.conexao, receptor, transmissor);
        }
        catch (Exception erro)
        {} // sei que passei os parametros corretos

        try
        {
            synchronized (this.usuarios)
            {
                this.usuarios.add (this.usuario);
            }


            for(;;)
            {
                Comunicado comunicado = this.usuario.envie ();

                if (comunicado==null)
                    return;
                else if (comunicado instanceof EnvioDeCpf)
                {
                    EnvioDeCpf envioDeCpf = (EnvioDeCpf) comunicado;

                    this.cpf = envioDeCpf.getCpf();
                }
                else if (comunicado instanceof PedidoDeResultado)
                {
                    this.cpf = this.cpf.replaceAll("[^0-9]", "");

                    this.cpfValido = CNP.isValidCPF(this.cpf);

                    this.usuario.receba(new Resultado(this.cpfValido));
                }

                else if (comunicado instanceof PedidoParaSair)
                {
                    synchronized (this.usuarios)
                    {
                        this.usuarios.remove (this.usuario);
                    }
                    this.usuario.adeus();
                }
            }
        }
        catch (Exception erro)
        {
            try
            {
                transmissor.close ();
                receptor.close ();
            }
            catch (Exception falha)
            {} // so tentando fechar antes de acabar a thread

            return;
        }
    }
}