import static org.junit.jupiter.api.Assertions.assertTrue;

import com.mashape.unirest.http.exceptions.UnirestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DemoWithoutZendeskClientTest {

  private DemoWithoutZendeskClient demo;

  @BeforeEach
  void setUp() {
    this.demo = new DemoWithoutZendeskClient();
  }

  @Test
  void connect() throws UnirestException {
    this.demo.connect();
    assertTrue(this.demo.subjectStringList.size() > 0);
  }
}