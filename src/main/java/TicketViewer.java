import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;
import org.zendesk.client.v2.Zendesk;
import org.zendesk.client.v2.ZendeskResponseException;
import org.zendesk.client.v2.model.Ticket;
import io.github.cdimascio.dotenv.Dotenv;

/**
 * TicketRequester class handles the defined need to list tickets from Zendesk API
 */
public class TicketViewer {

  private final ArrayList<Ticket> ticketList = new ArrayList<>();
  private static final String NEXT_PAGE = "1";
  private static final String PARTICULAR_TICKET = "2";
  private static final String QUIT = "q";
  private static final String DOMAIN = "DOMAIN";
  private static final String EMAIL = "EMAIL";
  private static final String TOKEN = "TOKEN";

  /**
   * This method connects to the zendesk API using environment variables in .env
   *
   * @return the connected Zendesk object
   */
  public Zendesk connect() {
    try {
      Dotenv dotenv = Dotenv.load();
      return new Zendesk.Builder(dotenv.get(DOMAIN))
          .setUsername(dotenv.get(EMAIL))
          .setToken(dotenv.get(TOKEN))
          .build();
    } catch (ZendeskResponseException | IllegalArgumentException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * this method processes the Zendesk Object and stores all the tickets in an ArrayList
   *
   * @param zd the Zendesk Object
   */
  public void processTickets(Zendesk zd) {
    if (zd == null) {
      System.err.println("Connection cannot be established");
      System.exit(1);
    }
    zd.getTickets().forEach(ticketList::add);
    zd.close();
  }

  /**
   * this method lists a particular ticket according to the given index
   *
   * @param index the ticket index
   */
  public void listTickets(int index) {
    if (index < 0 || index >= ticketList.size()) {
      throw new IllegalArgumentException("The provided ticket number is incorrect");
    }
    System.out.println(
        "Ticket number: " + index + ". Ticket subject: " + ticketList.get(index).getSubject()
            + ". Submitted by " + ticketList.get(index)
            .getSubmitterId() + " of type " + ticketList.get(index).getType());
    System.out.println("The description is:\n" + ticketList.get(index).getDescription());
  }

  /**
   * this method lists all the tickets in the index range
   *
   * @param indexL the left index (inclusive)
   * @param indexR the right index (exclusive)
   */
  public void listTickets(int indexL, int indexR) {
    if (indexL < 0 || indexL > ticketList.size() || indexR > ticketList.size()) {
      throw new IllegalArgumentException("The provided ticket number is incorrect");
    }
    for (int i = indexL; i < indexR; i++) {
      System.out.println(
          "Ticket number " + i + ". Ticket subject: " + ticketList.get(i).getSubject()
              + ". Submitted by " + ticketList.get(i)
              .getSubmitterId() + " of type " + ticketList.get(i).getType());
    }
  }

  /**
   * this method guides the user to view the tickets
   *
   * @param in the input stream for input
   */
  public void userInteraction(InputStream in) {
    int start = 0;
    int end = Math.min(25, ticketList.size());
    Scanner scanner = new Scanner(in);
    while (true) {
      if (start >= ticketList.size() || end > ticketList.size()) {
        System.out.println("Reached the end of list");
        return;
      }
      System.out.println("Displaying tickets from " + start + " to " + end + "\n");
      listTickets(start, end);

      System.out.println("\nDo you wish to view next page of tickets (type " + NEXT_PAGE
          + ") or view a particular ticket (type " + PARTICULAR_TICKET + ")?");
      System.out.println("Press " + QUIT + " to quit.\n");
      String input = scanner.next();
      switch (input) {
        case NEXT_PAGE -> {
          start = end + 1;
          end = Math.min(25 + end, ticketList.size());
        }
        case PARTICULAR_TICKET -> {
          while (true) {
            System.out.println("\nWhich ticket do you like to view? (Use ticket number)");
            System.out.println("Type " + QUIT + " to go back to ticket list view\n");
            String ticketNumberString = scanner.next();
            if (ticketNumberString.equals(QUIT)) {
              break;
            }
            int ticketNumber = Integer.parseInt(ticketNumberString);
            listTickets(ticketNumber);
          }
        }
        case QUIT -> {
          System.out.println("\nProgram exiting");
          return;
        }
        default -> throw new IllegalArgumentException(
            "The input must be " + NEXT_PAGE + " or " + PARTICULAR_TICKET);
      }
    }
  }

  /**
   * the main method of the program
   *
   * @param args the cml arguments
   */
  public static void main(String[] args) {
    TicketViewer tickerRequester = new TicketViewer();
    tickerRequester.processTickets(tickerRequester.connect());
    tickerRequester.userInteraction(System.in);
  }

  /**
   * get the ticketList
   *
   * @return ArrayList of tickets
   */
  public ArrayList<Ticket> getTicketList() {
    return ticketList;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TicketViewer that = (TicketViewer) o;
    return ticketList.equals(that.ticketList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ticketList);
  }

  @Override
  public String toString() {
    return "TicketViewer{" +
        "ticketList=" + ticketList +
        '}';
  }
}
