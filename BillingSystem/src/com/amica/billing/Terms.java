package com.amica.billing;
import java.util.Optional;
import java.time.LocalDate;
import lombok.Data;
import lombok.AllArgsConstructor;

public enum Terms {
    CASH(0),
    CREDIT_30(30),
    CREDIT_45(45),
    CREDIT_60(60),
    CREDIT_90(90);

    private final int days;

    Terms(int days) {
        this.days = days;
    }

    public int getDays() {
        return days;
    }
}
