package pavel912.cer;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class App {
    private final String currencyISOCode;
    private final String date;

    public App(String currencyISOCode, String date) {
        this.currencyISOCode = currencyISOCode;
        this.date = date;
    }

    public String callApi() {
        if (!isValidDate()) {
            return "Incorrect date";
        }

        try {
            String exchangeRate = ApiCaller.getExchangeRate(this.currencyISOCode, this.date);
            String currencyName = ApiCaller.getCurrencyName(this.currencyISOCode);

            return String.format("%s: %s", currencyName, exchangeRate);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public boolean isValidDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate date = LocalDate.parse(this.date, formatter);
            return true;
        } catch (DateTimeException e) {
            return false;
        }
    }
}
