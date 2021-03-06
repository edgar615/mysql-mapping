package com.github.edgar615.mysql.mapping;

import com.google.common.base.CharMatcher;
import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Edgar on 2016/4/1.
 *
 * @author Edgar  Date 2016/4/1
 */
public class TableMappingOptions {
  //支持通配符,product, exa*, *e
  public static final String DEFAULT_IGNORE_TABLES = null;

  //支持通配符,product, exa*, *e
  public static final String DEFAULT_IGNORE_COLUMN = null;

  public static final String DEFAULT_JDBC_DRIVER = "com.mysql.jdbc.Driver";

  public static final String DEFAULT_JDBC_ARG = "";

  public static final String DEFAULT_HOST = "localhost";

  public static final int DEFAULT_PORT = 3306;

  public static final int DEFAULT_LOGIN_TIMEOUT = 10;

  public static final String DEFAULT_DATABASE = "test";

  public static final String DEFAULT_USERNAME = "root";

  public static final String DEFAULT_PASSWORD = "";

  //忽略的字段
  private final List<String> ignoreColumnList = new ArrayList<String>();

  //使用前缀匹配忽略的表
  private final List<String> ignoreColumnStartsWithPattern = new ArrayList<String>();

  //使用后缀匹配忽略的表
  private final List<String> ignoreColumnEndsWithPattern = new ArrayList<String>();

  //忽略的表
  private final List<String> ignoreTableList = new ArrayList<String>();

  //使用前缀匹配忽略的表
  private final List<String> ignoreTableStartsWithPattern = new ArrayList<String>();

  //使用后缀匹配忽略的表
  private final List<String> ignoreTableEndsWithPattern = new ArrayList<String>();

  //只生成这些表
  private final List<String> tableList = new ArrayList<>();

  private String ignoreTablesStr = DEFAULT_IGNORE_TABLES;

  private String ignoreColumnsStr = DEFAULT_IGNORE_COLUMN;

  private String driverClass = DEFAULT_JDBC_DRIVER;

  private String host = DEFAULT_HOST;

  private int port = DEFAULT_PORT;

  private String database = DEFAULT_DATABASE;

  private String jdbcArg = DEFAULT_JDBC_ARG;

  private String username = DEFAULT_USERNAME;

  private String password = DEFAULT_PASSWORD;

  private int loginTimeout = DEFAULT_LOGIN_TIMEOUT;

  /**
   * Default constructor
   */
  public TableMappingOptions() {
    setIgnoreTable();

    setIgnoreColumn();
  }

  public String getJdbcUrl() {
    String jdbcUrl = "jdbc:mysql://" + host + ":" + port + "/" + database;
    if (!Strings.isNullOrEmpty(jdbcArg)) {
      jdbcUrl += "?" + jdbcArg;
    }
    return jdbcUrl;
  }

  public String getHost() {
    return host;
  }

  public TableMappingOptions setHost(String host) {
    this.host = host;
    return this;
  }

  public int getPort() {
    return port;
  }

  public TableMappingOptions setPort(int port) {
    this.port = port;
    return this;
  }

  public int getLoginTimeout() {
    return loginTimeout;
  }

  public TableMappingOptions setLoginTimeout(int loginTimeout) {
    this.loginTimeout = loginTimeout;
    return this;
  }

  public String getDatabase() {
    return database;
  }

  public TableMappingOptions setDatabase(String database) {
    this.database = database;
    return this;
  }

  public String getJdbcArg() {
    return jdbcArg;
  }

  public TableMappingOptions setJdbcArg(String jdbcArg) {
    this.jdbcArg = jdbcArg;
    return this;
  }

  public List<String> getIgnoreColumnList() {
    return ignoreColumnList;
  }

  public List<String> getIgnoreTableList() {
    return ignoreTableList;
  }

  public List<String> getIgnoreTableStartsWithPattern() {
    return ignoreTableStartsWithPattern;
  }

  public List<String> getIgnoreTableEndsWithPattern() {
    return ignoreTableEndsWithPattern;
  }

  public String getIgnoreTablesStr() {
    return ignoreTablesStr;
  }

  public TableMappingOptions setIgnoreTablesStr(String ignoreTablesStr) {
    this.ignoreTablesStr = ignoreTablesStr;
    this.setIgnoreTable();
    return this;
  }

  public TableMappingOptions addGenTable(String tableName) {
    this.tableList.add(tableName);
    return this;
  }

  public List<String> getTableList() {
    return tableList;
  }

  public TableMappingOptions addGenTables(List<String> tableNames) {
    this.tableList.addAll(tableNames);
    return this;
  }

  public String getIgnoreColumnsStr() {
    return ignoreColumnsStr;
  }

  public TableMappingOptions setIgnoreColumnsStr(String ignoreColumnsStr) {
    this.ignoreColumnsStr = ignoreColumnsStr;
    this.setIgnoreColumn();
    return this;
  }

  public List<String> getIgnoreColumnStartsWithPattern() {
    return ignoreColumnStartsWithPattern;
  }

  public List<String> getIgnoreColumnEndsWithPattern() {
    return ignoreColumnEndsWithPattern;
  }

  public String getDriverClass() {
    return driverClass;
  }

  public TableMappingOptions setDriverClass(String driverClass) {
    this.driverClass = driverClass;
    return this;
  }

  public String getUsername() {
    return username;
  }

  public TableMappingOptions setUsername(String username) {
    this.username = username;
    return this;
  }

  public String getPassword() {
    return password;
  }

  public TableMappingOptions setPassword(String password) {
    this.password = password;
    return this;
  }

  protected void setIgnoreTable() {
    if (!Strings.isNullOrEmpty(ignoreTablesStr)) {
      StringTokenizer strTok = new StringTokenizer(ignoreTablesStr, ",");
      while (strTok.hasMoreTokens()) {
        String token = strTok.nextToken().toLowerCase().trim();
        if (CharMatcher.anyOf("*").indexIn(token) == 0) {
          this.ignoreTableEndsWithPattern.add(token.substring(1, token.length()));
        } else if (CharMatcher.anyOf("*").lastIndexIn(token) == token.length() - 1) {
          this.ignoreTableStartsWithPattern.add(token.substring(0, token.length() - 1));
        } else {
          this.ignoreTableList.add(token);
        }
      }
    }
  }

  protected void setIgnoreColumn() {

    if (!Strings.isNullOrEmpty(ignoreColumnsStr)) {
      StringTokenizer strTok = new StringTokenizer(ignoreColumnsStr, ",");
      while (strTok.hasMoreTokens()) {
        String token = strTok.nextToken().toLowerCase().trim();
        if (CharMatcher.anyOf("*").indexIn(token) == 0) {
          this.ignoreColumnEndsWithPattern.add(token.substring(1, token.length()));
        } else if (CharMatcher.anyOf("*").lastIndexIn(token) == token.length() - 1) {
          this.ignoreColumnStartsWithPattern.add(token.substring(0, token.length() - 1));
        } else {
          this.ignoreColumnList.add(token);
        }
      }
    }
  }
}
