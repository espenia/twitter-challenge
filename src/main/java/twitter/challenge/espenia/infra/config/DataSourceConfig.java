package twitter.challenge.espenia.infra.config;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Configuration
@Slf4j
public class DataSourceConfig {
  @Value("${spring.datasource.url}")
  private String url;

  @Value("${spring.datasource.username}")
  private String username;

  @Value("${spring.datasource.password}")
  private String password;

  @Value("${spring.datasource.hikari.connection-init-sql}")
  private String connectionInitSql;

  @Value("${spring.datasource.hikari.maximum-pool-size}")
  private int maximumPoolSize;

  @Value("${spring.datasource.hikari.minimum-idle}")
  private int minimumIdle;

  @Value("${spring.datasource.hikari.connection-timeout}")
  private int connectionTimeout;

  @Value("${spring.datasource.hikari.leak-detection-threshold}")
  private int leakDetectionThreshold;

  @Value("${spring.datasource.hikari.prep-stmt-cache-size}")
  private int prepStmtCacheSize;

  @Value("${spring.datasource.hikari.prep-stmt-cache-sql-limit}")
  private int prepStmtCacheSqlLimit;

  @Value("${spring.datasource.hikari.cache-prep-stmts}")
  private Boolean cachePrepStmts;

  @Value("${spring.datasource.hikari.use-server-prep-stmts}")
  private Boolean useServerPrepStmts;

  @Value("${spring.datasource.hikari.maxLifeTime}")
  private int maxLifeTime;

  /**
   * mysql data source configuration.
   *
   * @return DataSource
   */
  @Bean
  @Primary
  @Profile({"prod", "stage"})
  public DataSource dataSource() {

    HikariDataSource dataSource =
        DataSourceBuilder.create()
            .username(username)
            .password(password)
            .type(HikariDataSource.class)
            .driverClassName("com.mysql.cj.jdbc.Driver")
            .url(url)
            .build();

    log.info("setting up datasource for prod or stage");
    setTypeSpecificProperties(dataSource);
    return dataSource;
  }

  /**
   * h2 data source configuration.
   *
   * @return DataSource
   */
  @Bean
  @FlywayDataSource
  @Profile({"test", "local", "integration_test"})
  DataSource flywayDataSourceTest() {
    log.info("Flyaway initialization... for test");
    return DataSourceBuilder.create().url(url).driverClassName("org.h2.Driver").build();
  }

  private void setTypeSpecificProperties(HikariDataSource dataSource) {
    dataSource.setConnectionInitSql(connectionInitSql);
    dataSource.setMaximumPoolSize(maximumPoolSize);
    dataSource.setMinimumIdle(minimumIdle);
    dataSource.setConnectionTimeout(connectionTimeout);
    dataSource.setLeakDetectionThreshold(leakDetectionThreshold);
    dataSource.addDataSourceProperty("prepStmtCacheSize", prepStmtCacheSize);
    dataSource.addDataSourceProperty("prepStmtCacheSqlLimit", prepStmtCacheSqlLimit);
    dataSource.addDataSourceProperty("cachePrepStmts", cachePrepStmts);
    dataSource.addDataSourceProperty("useServerPrepStmts", useServerPrepStmts);
    dataSource.setMaxLifetime(maxLifeTime);
  }
}
