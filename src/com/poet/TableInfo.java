package com.poet;

import java.util.List;

/**
 * �������Ϣ
 */
public class TableInfo {

	private String tableName; // ����
	private String classNmae; // ����
	private List<Field> fields; // �ֶ��б�

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
