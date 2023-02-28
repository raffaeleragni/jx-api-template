package api;

import com.github.raffaeleragni.jx.http.*;
import com.github.raffaeleragni.jx.network.*;
import java.util.*;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class MainTest {

  @BeforeAll
  static void intialize() {
    Main.main(new String[]{});
  }

  @AfterAll
  static void destroy() {
    Main.stop();
  }

  @Test
  void testServerUp() {
    assertThat(Network.isPortAvailable(3000), is(false));
  }

  @Test
  void testGet200() {
    var client = new Client();
    var result = client.getString("http://localhost:3000/");
    assertThat(result, is("{}"));
  }

  @Test
  void testDbTest() {
    var client = new Client();
    var result = client.getString("http://localhost:3000/test-connection");
    assertThat(result, is("1"));
  }

  @Test
  void testWithoutToken() {
    var client = new Client();
    var result = client.get("http://localhost:3000/user/1");
    assertThat(result.status(), is(401));
  }

  @Test
  void testWithBadToken() {
    var client = new Client();
    var tokenHeaders = Map.of("Authentication", List.of("bad"));
    var result = client.get("http://localhost:3000/user/1", tokenHeaders);
    assertThat(result.status(), is(403));
  }

  @Test
  void testWithGoodToken() {
    var client = new Client();
    var tokenHeaders = Map.of("Authentication", List.of("token"));
    var result = client.get("http://localhost:3000/user/1", tokenHeaders);
    assertThat(result.status(), is(200));
    assertThat(result.body(), is("1"));
  }
}
