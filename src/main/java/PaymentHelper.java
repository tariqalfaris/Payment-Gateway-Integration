import java.util.HashMap;
import java.util.Map;

public class PaymentHelper {

    // provider 1 = legacy, provider 2 = stripe-like
    public static void pay(int provider, double amount, String currency, String customerToken) {

        if (provider == 1) {
            long cents = Math.round(amount * 100);
            LegacyPayGateway legacy = new LegacyPayGateway();
            legacy.submitTransaction(cents, currency, customerToken);

        } else if (provider == 2) {
            Map<String, String> meta = new HashMap<>();
            meta.put("customerToken", customerToken);
            StripeLikeClient stripe = new StripeLikeClient();
            stripe.charge(currency, amount, meta);

        } else {
            throw new IllegalStateException("Unknown provider: " + provider);
        }
    }
}

class LegacyPayGateway {
    public void submitTransaction(long cents, String isoCurrency, String customerToken) {
        System.out.println("LegacyPayGateway: charged " + cents + " cents ("
                + isoCurrency + ") to customer token " + customerToken);
    }
}

class StripeLikeClient {
    public void charge(String currency, double amount, Map<String, String> meta) {
        System.out.println("StripeLikeClient: charged " + amount + " " + currency
                + " with metadata " + meta);
    }
}