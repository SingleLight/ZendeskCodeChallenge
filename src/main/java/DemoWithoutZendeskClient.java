import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 * class that demos approach to using the api without the zendesk client
 */
public class DemoWithoutZendeskClient {

  /**
   * made public solely for testing purposes
   */
  public ArrayList<String> subjectStringList = new ArrayList<>();

  /**
   * main method
   *
   * @param args command line arguments
   * @throws UnirestException throws if the api calls are bad
   */
  public static void main(String[] args) throws UnirestException {
    DemoWithoutZendeskClient demo = new DemoWithoutZendeskClient();
    demo.connect();
  }

  /**
   * connect to the zendesk api and query first 100 tickets
   *
   * @throws UnirestException throws if the api calls are bad
   */
  public void connect() throws UnirestException {
    Dotenv dotenv = Dotenv.load();
    HttpResponse<String> httpResponse = Unirest.get(dotenv.get("DOMAIN") + "/api/v2/requests.json")
        .header("Accept", "application/json")
        .basicAuth(dotenv.get("EMAIL") + "/token", dotenv.get("TOKEN")).asString();
    JSONObject js = (JSONObject) JSONValue.parse(httpResponse.getBody());
    JSONArray jsa = (JSONArray) js.get("requests");
    for (Object obj : jsa) {
      System.out.println("The subject of the ticket is " + ((JSONObject) obj).get("subject"));
      subjectStringList.add(((JSONObject) obj).get("subject").toString());
    }
  }
}
