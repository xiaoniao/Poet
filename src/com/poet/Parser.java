package com.poet;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 解析sql语句结构
 */
public class Parser {

	private static Map<String, Class<?>> map = new HashMap<>();

	static {
		map.put("tinyint", Integer.class);
		map.put("smallint", Integer.class);
		map.put("mediumint", Integer.class);
		map.put("Integer", Integer.class);
		map.put("bigint", Long.class);
		map.put("int", Integer.class);

		map.put("float", Double.class);
		map.put("double", Double.class);
		map.put("decimal", Double.class);

		map.put("char", String.class);
		map.put("varchar", String.class);
		map.put("tinyblob", String.class);
		map.put("tinytext", String.class);
		map.put("blob", String.class);
		map.put("text", String.class);
		map.put("mediumblob", String.class);
		map.put("mediumtext", String.class);
		map.put("logngblob", String.class);
		map.put("longtext", String.class);
		map.put("enum", String.class);
		map.put("set", String.class);

		map.put("Date", Date.class);
		map.put("time", Date.class);
		map.put("year", Date.class);
		map.put("datetime", Date.class);
		map.put("timestamp", Date.class);
	}

	public TableInfo parse(String sql) {
		TableInfo tableInfo = new TableInfo();
		// 表名
		int tableFromIndex = sql.indexOf("`") + 1;
		int tableToIndex = sql.indexOf("`", tableFromIndex);
		String tableName = sql.substring(tableFromIndex, tableToIndex);

		StringBuffer className = new StringBuffer();
		boolean is_ = false;
		for (int i = 0; i < tableName.length(); i++) {
			char c = tableName.charAt(i);
			if (i == 0) {
				c = (char) (c - (char) 32);
			}
			if (c == '_') {
				is_ = true;
			} else {
				if (is_) {
					className.append((char) (c - (char) 32));
				} else {
					className.append(c);
				}
				is_ = false;
			}
		}
		tableInfo.setClassNmae(className.toString());
		tableInfo.setTableName(tableName);

		// 获取字段数据
		int dataFromIndex = sql.indexOf('(') + 1;
		int dataToIndex = sql.lastIndexOf(')');
		String data = sql.substring(dataFromIndex, dataToIndex);

		List<Field> fields = new ArrayList<>();
		String[] line = data.split(",");
		for (int i = 0; i < line.length - 1; i++) {
			Field field = new Field();
			String lineStr = line[i];
			int lineFromIndex = lineStr.indexOf("`") + 1;
			int lineToIndex = lineStr.indexOf("`", lineFromIndex);
			String fieldName = lineStr.substring(lineFromIndex, lineToIndex);
			Class<?> fieldType = null;
			for (String type : map.keySet()) {
				if (lineStr.contains(type)) {
					fieldType = map.get(type);
					break;
				}
			}
			field.setFieldName(fieldName);
			field.setFieldType(fieldType);
			fields.add(field);
		}
		
		// 设置主键
		String last = line[line.length - 1];
		int fromIndex = last.indexOf("`") + 1;
		int toIndex = last.indexOf("`", fromIndex);
		String primary = last.substring(fromIndex, toIndex);
		for (Field field : fields) {
			if (field.getFieldName().equals(primary)) {
				field.setPrimaryKey(true);
			}
		}
		
		tableInfo.setFields(fields);
		return tableInfo;
	}
}
