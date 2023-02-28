package api.routes;

import com.github.raffaeleragni.jx.http.Server.Context;
import java.util.function.*;

public class Index implements Function<Context, String> {

  @Override
  public String apply(Context ctx) {
    return "{}";
  }
}
