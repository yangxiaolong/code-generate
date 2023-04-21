package com.nl.generate.code.vo;

public class ReferenceClassVO {
	
	private String sourceClassFullName;
	
	private String targetClassFullName;
	
	private String sourceClass;
	
	private String sourcePackageName;
	
	private String targetPackageName;

	public String getSourceClassFullName() {
		return sourceClassFullName;
	}

	public void setSourceClassFullName(String sourceClassFullName) {
		this.sourceClassFullName = sourceClassFullName;
	}

	public String getTargetClassFullName() {
		return targetClassFullName;
	}

	public void setTargetClassFullName(String targetClassFullName) {
		this.targetClassFullName = targetClassFullName;
	}

	public String getSourceClass() {
		return sourceClass;
	}

	public void setSourceClass(String sourceClass) {
		this.sourceClass = sourceClass;
	}

	public String getSourcePackageName() {
		return sourcePackageName;
	}

	public void setSourcePackageName(String sourcePackageName) {
		this.sourcePackageName = sourcePackageName;
	}

	public String getTargetPackageName() {
		return targetPackageName;
	}

	public void setTargetPackageName(String targetPackageName) {
		this.targetPackageName = targetPackageName;
	}

	@Override
	public String toString() {
		return "ReferenceClassVO [sourceClassFullName=" + sourceClassFullName
				+ ", targetClassFullName=" + targetClassFullName
				+ ", sourceClass=" + sourceClass + ", sourcePackageName="
				+ sourcePackageName + ", targetPackageName="
				+ targetPackageName + "]";
	}
	
}
