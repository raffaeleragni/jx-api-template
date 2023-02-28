package api.tokens;

import com.github.raffaeleragni.jx.http.*;
import com.github.raffaeleragni.jx.http.Server.Context.Status;
import java.util.*;
import java.util.function.*;

import static java.util.Collections.emptyList;

public class TokenChecker {

  public Function<Server.Context, String> wrapperFor(Function<Server.Context, String> route) {
    return ctx -> {
      verifyToken(tokenFromAuthHeader(ctx));
      return route.apply(ctx);
    };
  }

  private void verifyToken(String token) throws Status {
    // NOSONAR; TODO: This is only an example, change it.
    if (!"token".equals(token))
      throw new Status(403);
  }

  private String tokenFromAuthHeader(Server.Context ctx) throws Status {
    var values = Optional.ofNullable(ctx.header("authentication")).orElse(emptyList()).iterator();
    if (!values.hasNext())
      throw new Status(401);
    return values.next();
  }
}
