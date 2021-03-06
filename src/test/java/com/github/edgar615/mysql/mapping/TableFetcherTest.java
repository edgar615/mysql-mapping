package com.github.edgar615.mysql.mapping;

import java.util.List;

/**
 * Created by Edgar on 2018/5/22.
 *
 * @author Edgar  Date 2018/5/22
 */
public class TableFetcherTest {
  public static void main(String[] args) throws Exception {

    TableMappingOptions options = new TableMappingOptions()
            .setHost("localhost")
            .setDatabase("user_new")
            .setUsername("admin")
            .setPassword("123456");
    TableMapping mapping = new TableMapping(options);
    List<Table> tables = mapping.fetchTable();
    System.out.println(tables);
  }
}
