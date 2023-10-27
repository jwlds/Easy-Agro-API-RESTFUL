package jw.com.br.EasyAgro.dtos;

import lombok.Getter;
import lombok.Setter;


public record UserDTO(
         String login,
         String password,
         String phoneNumber,
         String cpf) {
}
