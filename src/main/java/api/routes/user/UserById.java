package api.routes.user;

import com.github.raffaeleragni.jx.http.*;
import java.util.function.*;

public class UserById implements Function<Server.Context, String> {

  @Override
  public String apply(Server.Context ctx) {
    return ctx.extraPath();
  }
}
