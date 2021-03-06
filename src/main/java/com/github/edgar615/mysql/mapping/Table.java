package com.github.edgar615.mysql.mapping;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 数据表.
 *
 * @author Edgar  Date 2016/4/1
 */
public class Table {

  private final String remarks;

  private final String name;

  private final List<Column> columns = new ArrayList<>();

  private Table(String name, String remarks) {
    this.name = name;
    this.remarks = remarks;
  }

  public static Table create(String name, String remarks) {
    return new Table(name, remarks);
  }

  public void addColumn(Column column) {
    columns.add(column);
  }

  public String getName() {
    return name;
  }

  public List<Column> getColumns() {
    return columns;
  }

  public String getRemarks() {
    return remarks;
  }

  public List<String> getFields() {
    return columns.stream()
            .filter(c -> !c.isIgnore())
            .map(c -> c.getName())
            .collect(Collectors.toList());
  }

  public List<String> getVirtualFields() {
    return columns.stream()
            .filter(c -> c.isGenColumn())
            .map(c -> c.getName())
            .collect(Collectors.toList());
  }

  public boolean getContainsVirtual() {
    return columns.stream()
            .anyMatch(c -> c.isGenColumn());
  }

  /**
   * 如果只有一个主键，通过这个类获取主键.
   *
   * @return
   */
  public String getPk() {
    return columns.stream()
            .filter(c -> !c.isIgnore())
            .filter(c -> c.isPrimary())
            .map(c -> c.getName())
            .findFirst()
            .get();
  }

  /**
   * 如果只有一个主键，通过这个类获取主键类型.
   *
   * @return
   */
  public ParameterType getPkType() {
    return columns.stream()
            .filter(c -> !c.isIgnore())
            .filter(c -> c.isPrimary())
            .map(c -> c.getParameterType())
            .findFirst()
            .get();
  }

  /**
   * 返回主键列表
   *
   * @return
   */
  public List<Map.Entry<String, ParameterType>> getPkList() {
    return columns.stream()
            .filter(c -> !c.isIgnore())
            .filter(c -> c.isPrimary())
            .map(c -> Maps.immutableEntry(c.getName(), c.getParameterType()))
            .collect(Collectors.toList());
  }

  /**
   * 返回驼峰格式
   * @return
   */
  public String getUpperCamelName() {
    return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, name);
  }


  @Override
  public String toString() {
    return "Table{" +
           "remarks='" + remarks + '\'' +
           ", name='" + name + '\'' +
           ", columns=" + columns +
           '}';
  }
}
