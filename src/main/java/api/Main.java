package api;

import api.routes.*;
import com.github.raffaeleragni.jx.http.*;
import com.github.raffaeleragni.jx.injection.*;
import java.util.*;

import static java.util.Optional.empty;

public final class Main {
  private Main() {}

  private static Optional<Server> globalServer = empty();

  public static void main(String[] args) {
    var server = new Server(3000);
    globalServer = Optional.of(server);

    var env = new Injection();
    env.addInstance(Server.class, server);
    env.createNew(Setup.class).setup(env);
    env.createNew(Mapper.class).mapToServer(server);
  }

  public static void stop() {
    globalServer.ifPresent(Server::terminate);
    globalServer = empty();
  }
}
