package org.project.unitech.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferResponseDto {

    private String fromIban;
    private String toIban;
    private BigDecimal amount;
    private String message;
}
