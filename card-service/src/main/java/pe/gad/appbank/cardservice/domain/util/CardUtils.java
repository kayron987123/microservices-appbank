package pe.gad.appbank.cardservice.domain.util;

import java.util.Random;

public class CardUtils {
    private static final Random random = new Random();

    public static String generateCardNumber(String bin) {
        final int length = 16;
        StringBuilder builder = new StringBuilder(bin);

        while (builder.length() < length - 1) {
            builder.append(random.nextInt(10));
        }

        int checkDigit = calculateLuhnCheckDigit(builder.toString());
        builder.append(checkDigit);

        return builder.toString();
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

        return (10 - (sum % 10)) % 10;
    }
}
