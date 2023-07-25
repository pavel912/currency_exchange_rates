package pavel912.cer;

import  org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;


public class ApiCaller {
    public static String getExchangeRate(String currencyISOCode, String date) throws IOException, URISyntaxException, InterruptedException {
        String url = String.format("http://www.cbr.ru/scripts/XML_dynamic.asp?date_req1=%s&date_req2=%s&VAL_NM_RQ=%s", date, date, currencyISOCode);

        String responseBody = call(url);
        Document doc;

        try {
            doc = XMLParser.loadXMLFromString(responseBody);
        } catch (Exception e) {
            throw new RuntimeException("Error while parsing xml");
        }

        NodeList nodeList = doc.getElementsByTagName("Record");

        if (nodeList.getLength() == 0) {
            throw new RuntimeException("Currency not found");
        }

        if (nodeList.getLength() > 1) {
            throw new RuntimeException("Too many records");
        }

        Node node = nodeList.item(0);

        NodeList recordChildList = node.getChildNodes();

        return recordChildList.item(1).getFirstChild().getNodeValue();
    }

    public static String getCurrencyName(String currencyISOCode) throws IOException, URISyntaxException, InterruptedException {
        String url = "http://www.cbr.ru/scripts/XML_val.asp?d=0";

        String responseBody = call(url);
        Document doc;

        try {
            doc = XMLParser.loadXMLFromString(responseBody);
        } catch (Exception e) {
            throw new RuntimeException("Error while parsing xml");
        }

        NodeList nodeList = doc.getElementsByTagName("Item");

        for (int i = 0; i < nodeList.getLength(); i++) {
            if (currencyISOCode.equals(nodeList.item(i).getAttributes().item(0).getNodeValue())) {
                return nodeList.item(i).getChildNodes().item(1).getFirstChild().getNodeValue();
            }
        }

        throw new RuntimeException("Currency name not found");
    }

    private static String call(String url) throws IOException, InterruptedException, URISyntaxException {
        URI targetURL = new URI(url);
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(targetURL)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }
}
