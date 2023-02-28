package api;

import api.routes.Index;
import api.routes.TestConnection;
import api.routes.user.*;
import api.tokens.*;
import com.github.raffaeleragni.jx.http.*;
import java.util.*;
import java.util.function.*;

class Mapper {
  final Map<String, Function<Server.Context, String>> routes;

  Mapper(
    Index index,
    TestConnection testConnection,
    TokenChecker tokenChecker,
    UserById userById) {

    routes = new HashMap<>();
    routes.put("/", index);
    routes.put("/test-connection", testConnection);
    routes.put("/user/", userById);

    setupAuth(tokenChecker);
  }

  void mapToServer(Server server) {
    routes.entrySet().forEach(e -> server.map(e.getKey(), e.getValue()));
  }

  private void setupAuth(TokenChecker tokenChecker) {
    List.of(
      "/user"
    ).stream()
      .flatMap(prefix -> routes.keySet().stream().filter(s -> s.startsWith(prefix)))
      .forEach(path -> wrapForAuth(path, tokenChecker));
  }

  private void wrapForAuth(String key, TokenChecker tokenChecker) {
    var route = routes.get(key);
    route = tokenChecker.wrapperFor(route);
    routes.put(key, route);
  }
}
