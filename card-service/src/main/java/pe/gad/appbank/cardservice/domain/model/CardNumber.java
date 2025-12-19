package pe.gad.appbank.cardservice.domain.model;

import pe.gad.appbank.cardservice.domain.enums.CardNetwork;

import java.util.Random;

public record CardNumber(String value) {
    private static final Random random = new Random();

    public CardNumber {
        if (value == null || value.length() < 13 || value.length() > 19) {
            throw new IllegalArgumentException("Invalid card number length");
        }
        if (!value.matches("\\d+")) {
            throw new IllegalArgumentException("Card number must contain only digits");
        }
    }



    public static CardNumber generate(CardNetwork network) {
        String bin;
        if (network == CardNetwork.VISA) {
            bin = "4";
        } else if (network == CardNetwork.MASTERCARD) {
            bin = "51";
        } else {
            bin = "4";
        }

        int targetLength = 16;
        StringBuilder builder = new StringBuilder(bin);

        while (builder.length() < targetLength - 1) {
            builder.append(random.nextInt(10));
        }

        int checkDigit = calculateLuhnCheckDigit(builder.toString());
        builder.append(checkDigit);

        return new CardNumber(builder.toString());
    }

    private static int calculateLuhnCheckDigit(String number) {
        int sum = 0;
        boolean alternate = true;

        for (int i = number.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(number.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) n = (n % 10) + 1;
            }
            sum += n;
            alternate = !alternate;
        }

        int remainder = sum % 10;
        return (remainder == 0) ? 0 : 10 - remainder;
    }

    public static CardNumber of(String cardNumber) {
        return new CardNumber(cardNumber);
    }

    public String mask() {
        if (value.length() < 4) return value;
        return "**** **** **** " + value.substring(value.length() - 4);
    }

    @Override
    public String toString() {
        return value;
    }
}
