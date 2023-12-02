package jw.com.br.EasyAgro.dtos;

import jw.com.br.EasyAgro.domain.user.my.Address;
import lombok.Getter;
import lombok.Setter;


public record UserDTO(
        String name,
        String nickname,
         String login,
         String password,
         String phoneNumber,
         String cpf,
        Address address) {
}
