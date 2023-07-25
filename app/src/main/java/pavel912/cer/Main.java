package pavel912.cer;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import java.util.concurrent.Callable;

@Command(name = "currency_rates", mixinStandardHelpOptions = true, version = "currency_rates 0.1",
        description = "Gets a currency exchange rate for specified ISO currency code and date")
public final class Main implements Callable<Integer> {

    @Option(names = {"--code"}, description = "Currency ISO code")
    private String currencyCode = "R01235";

    @Option(names = {"--date"}, description = "Date of currency exchange rate in format dd/MM/yyyy")
    private String date = "14/03/2001";

    @Override
    public Integer call() throws Exception {
        if (currencyCode == null || date == null) {
            System.out.println("Arguments can't be empty");
            return 0;
        }

        App app = new App(currencyCode, date);
        System.out.println(app.callApi());

        return 0;
    }

    public static void main(String[] args) {
        CommandLine cli = new CommandLine(new Main());
        int exitCode = cli.execute(args);
        System.out.println(exitCode);
    }

}