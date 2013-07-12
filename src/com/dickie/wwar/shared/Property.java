package com.dickie.wwar.shared;

public class Property {
	public Property(String propName, Object value) {
		super();
		this.propName = propName;
		this.value = value;
	}
	public String getPropName() {
		return propName;
	}
	public void setPropName(String propName) {
		this.propName = propName;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	private String propName;
	private Object value;
}
