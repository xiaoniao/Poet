package com.poet;

/*
 * �ֶ�����
 */
public class Field {

	private String fieldName; // �ֶ���
	private Class<?> fieldType; // �ֶ�����
	private boolean isPrimaryKey; // �Ƿ�������

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
