package jw.com.br.EasyAgro.dtos;

public record AdressDTO (
     String cep,
     String logradouro,
     String bairro,
     String localidade,
     String uf,
     String numero
){
}
