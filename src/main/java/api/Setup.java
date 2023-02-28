package api;

import com.github.raffaeleragni.jx.injection.*;
import com.github.raffaeleragni.jx.jdbc.*;
import com.zaxxer.hikari.*;
import java.sql.*;
import java.util.function.*;

import static com.github.raffaeleragni.jx.exceptions.Exceptions.unchecked;

public class Setup {
  public interface ConnectionSupplier extends Supplier<Connection> {}
  public interface JdbcSupplier extends Supplier<Jdbc> {}

  public void setup(Injection env) {
    var ds = new HKDataSource();
    env.addInstance(ConnectionSupplier.class, ds);
    env.addInstance(JdbcSupplier.class, () -> new Jdbc(ds::get));
  }
}

class HKDataSource implements Setup.ConnectionSupplier {
  HikariDataSource dataSource;

  HKDataSource() {
    HikariConfig config = new HikariConfig();
    config.setJdbcUrl("jdbc:h2:mem:api");
    config.setUsername("sa");
    config.setPassword("");
    config.addDataSourceProperty("cachePrepStmts", "true");
    config.addDataSourceProperty("prepStmtCacheSize", "250");
    config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

    dataSource = new HikariDataSource(config);
  }

  @Override
  public Connection get() {
    return unchecked(() -> dataSource.getConnection());
  }
}
