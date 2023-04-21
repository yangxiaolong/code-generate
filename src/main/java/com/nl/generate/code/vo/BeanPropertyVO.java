package com.nl.generate.code.vo;

public class BeanPropertyVO {

	private String columnName;

	private String littleColumnName;

	private String bigColumnName;

	private String dataType;

	private String simpleDataType;

	private Boolean isNullable;

	private String columnComment;

	private String columnKey;

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Boolean getIsNullable() {
		return isNullable;
	}

	public void setIsNullable(Boolean isNullable) {
		this.isNullable = isNullable;
	}

	public String getSimpleDataType() {
		return simpleDataType;
	}

	public void setSimpleDataType(String simpleDataType) {
		this.simpleDataType = simpleDataType;
	}

	public String getLittleColumnName() {
		return littleColumnName;
	}

	public void setLittleColumnName(String littleColumnName) {
		this.littleColumnName = littleColumnName;
	}

	public String getBigColumnName() {
		return bigColumnName;
	}

	public void setBigColumnName(String bigColumnName) {
		this.bigColumnName = bigColumnName;
	}

	public String getColumnComment() {
		return columnComment;
	}

	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}

	public String getColumnKey() {
		return columnKey;
	}

	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}

	public String getColumnName() {
		return columnName;
	}
	
	public boolean isPrimaryKey() {
		if(this.getColumnKey().equals("PRI")) {
			return true;
		}
		return false;
	}
}	
