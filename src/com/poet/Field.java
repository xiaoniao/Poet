package com.poet;

/*
 * 字段属性
 */
public class Field {

	private String fieldName; // 字段名
	private Class<?> fieldType; // 字段类型
	private boolean isPrimaryKey; // 是否是主键

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Class<?> getFieldType() {
		return fieldType;
	}

	public void setFieldType(Class<?> fieldType) {
		this.fieldType = fieldType;
	}

	public boolean isPrimaryKey() {
		return isPrimaryKey;
	}

	public void setPrimaryKey(boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

}
