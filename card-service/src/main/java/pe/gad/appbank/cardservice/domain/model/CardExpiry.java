package pe.gad.appbank.cardservice.domain.model;

import java.time.LocalDate;
import java.time.YearMonth;

public record CardExpiry(int month, int year) {
    public CardExpiry {
        if (month < 1 || month > 12) throw new IllegalArgumentException("Invalid month");
        YearMonth expiry = YearMonth.of(year, month);
        if (expiry.isBefore(YearMonth.now())) {
            throw new IllegalArgumentException("Expiry date cannot be in the past");
        }
    }

    public static CardExpiry fiveYearsFromNow() {
        LocalDate future = LocalDate.now().plusYears(5);
        return new CardExpiry(future.getMonthValue(), future.getYear());
    }

    public static CardExpiry of(Integer expiryMonth, Integer expiryYear) {
        return new CardExpiry(expiryMonth, expiryYear);
    }
}
