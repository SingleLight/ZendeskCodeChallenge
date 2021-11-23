import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.zendesk.client.v2.Zendesk;
import org.zendesk.client.v2.model.Ticket;

class TicketViewerTest {

  private TicketViewer ticketViewer;

  @BeforeEach
  void setUp() {
    ticketViewer = new TicketViewer();
  }

  @Test
  void connect() {
    Zendesk zd = ticketViewer.connect();
    assertNotEquals(null, zd);
    assertEquals(Zendesk.class, zd.getClass());
    zd.close();
  }

  @Test
  void processTickets() {
    ticketViewer.processTickets(ticketViewer.connect());
    ArrayList<Ticket> tktList = ticketViewer.getTicketList();
    assertNotEquals(0, tktList.size());
  }

  @Test
  void listTickets() {
    ticketViewer.processTickets(ticketViewer.connect());
    assertThrows(IllegalArgumentException.class, () -> ticketViewer.listTickets(-1));
  }

  @Test
  void testListTickets() {
    ticketViewer.processTickets(ticketViewer.connect());
    assertThrows(IllegalArgumentException.class, () -> ticketViewer.listTickets(-1, 100));
  }

  @Test
  void userInteraction() {
    ticketViewer.processTickets(ticketViewer.connect());
    String input = "1\n2\n5\nq\nq";
    InputStream in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
    assertDoesNotThrow(() -> ticketViewer.userInteraction(in));
    String badInput = "1\n-1\n";
    InputStream badIn = new ByteArrayInputStream(badInput.getBytes(StandardCharsets.UTF_8));
    assertThrows(IllegalArgumentException.class, () -> ticketViewer.userInteraction(badIn));
  }

  @Test
  void getTicketList() {
    assertEquals(new TicketViewer().getTicketList(), ticketViewer.getTicketList());
    TicketViewer test = new TicketViewer();
    test.processTickets(test.connect());
    assertNotEquals(test.getTicketList(), ticketViewer.getTicketList());
  }

  @Test
  void testEquals() {
    assertEquals(this.ticketViewer, this.ticketViewer);
    assertEquals(this.ticketViewer, new TicketViewer());
  }

  @Test
  void testHashCode() {
    assertEquals(this.ticketViewer.hashCode(), this.ticketViewer.hashCode());
  }

  @Test
  void testToString() {
    assertTrue(this.ticketViewer.toString().contains("TicketViewer"));
  }
}