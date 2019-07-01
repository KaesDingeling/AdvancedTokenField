package com.fo0.advancedtokenfield.data.model;

import com.vaadin.flow.templatemodel.TemplateModel;

public interface TokenTemplate extends TemplateModel {
	public void setValue(String value);
	public String getValue();
}