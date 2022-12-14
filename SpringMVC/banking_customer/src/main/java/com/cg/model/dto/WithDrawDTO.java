package com.cg.model.dto;

import com.cg.model.Withdraw;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WithDrawDTO implements Validator {

    private long customerId;
    private String fullName;
    private BigDecimal balance;

    @NotNull(message = "The Transaction amount is required")
    @DecimalMin(value = "49", message = "Transaction Amount must be greater than or equal to 50", inclusive = false)
    @DecimalMax(value = "10000001", message = "Transaction Amount must be less than or equa to 10.000.000", inclusive = false)
    private BigDecimal transactionAmount;

    public WithDrawDTO(long customerId, String fullName, BigDecimal balance) {
        this.customerId = customerId;
        this.fullName = fullName;
        this.balance = balance;
    }


    @Override
    public boolean supports(Class<?> clazz) {
        return WithDrawDTO.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        WithDrawDTO withDrawDTO = (WithDrawDTO) target;
        BigDecimal transactionAmount = withDrawDTO.getTransactionAmount();

        if (transactionAmount != null) {
            if (transactionAmount.toString().length() > 9) {
                errors.rejectValue("transactionAmount", "transactionAmount.length");
            }
            if (transactionAmount.toString().matches("(^$}[0-9]*$)")) {
                errors.rejectValue("transactionAmount", "transactionAmount.matches");
            }
        } else {
              errors.rejectValue("transactionAmount","transactionAmount.null");
        }
    }

    public Withdraw toWithdraw(){
        return new Withdraw()
                .setCustomerId(customerId)
                .setTransactionAmount(transactionAmount);
    }
}
