package jw.com.br.EasyAgro.dtos.mercadopago;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PayerIdentificationDTO {
    @NotNull
    private String type;

    @NotNull
    private String number;
}