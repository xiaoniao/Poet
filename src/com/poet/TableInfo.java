package com.poet;

import java.util.List;

/**
 * 保存表信息
 */
public class TableInfo {

	private String tableName; // 表名
	private String classNmae; // 类名
	private List<Field> fields; // 字段列表

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getClassNmae() {
		return classNmae;
	}

	public void setClassNmae(String classNmae) {
		this.classNmae = classNmae;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

}
