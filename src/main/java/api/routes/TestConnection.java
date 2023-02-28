package api.routes;

import api.*;
import com.github.raffaeleragni.jx.http.*;
import com.github.raffaeleragni.jx.jdbc.*;
import java.util.function.*;

public class TestConnection implements Function<Server.Context, String> {

  final Jdbc db;

  public TestConnection(Setup.JdbcSupplier jdbcSupplier) {
    this.db = jdbcSupplier.get();
  }

  @Override
  public String apply(Server.Context ctx) {
    return String.valueOf(selectOne());
  }

  private int selectOne() {
    return db.selectOneValue(Integer.class, "select 1", st -> {}).orElse(0);
  }
}
