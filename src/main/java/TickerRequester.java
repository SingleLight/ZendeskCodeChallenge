import java.util.ArrayList;
import org.zendesk.client.v2.Zendesk;
import org.zendesk.client.v2.ZendeskResponseException;
import org.zendesk.client.v2.model.Ticket;


public class TickerRequester {

  private final ArrayList<Ticket> ticketList = new ArrayList<>();
  private static final String YES = "y";
  private static final String NO = "n";
  private static final String DOMAIN = "DOMAIN";
  private static final String EMAIL = "EMAIL";
  private static final String TOKEN = "TOKEN";

  public Zendesk connect() {
    try {
      return new Zendesk.Builder(System.getenv(DOMAIN))
          .setUsername(System.getenv(EMAIL))
          .setToken(System.getenv(TOKEN))
          .build();
    } catch (ZendeskResponseException | IllegalArgumentException e) {
      e.printStackTrace();
      return null;
    }
  }

  public void processTickets(Zendesk zd) {
    if (zd == null) {
      System.err.println("Connection cannot be established");
      System.exit(1);
    }
    zd.getTickets().forEach(ticketList::add);
    zd.close();
  }

  public void listAllTickets() {
    ticketList.forEach((t) -> System.out.println(t.getSubject()));
  }


  public static void main(String[] args) {
    TickerRequester tickerRequester = new TickerRequester();
    tickerRequester.processTickets(tickerRequester.connect());
    tickerRequester.listAllTickets();
  }
}
