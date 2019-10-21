package com.fo0.advancedtokenfield.components;

import java.beans.PropertyChangeListener;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.fo0.advancedtokenfield.data.model.AdvancedTokenFieldConfig;
import com.fo0.advancedtokenfield.data.model.Token;
import com.fo0.advancedtokenfield.data.model.Token.TokenBuilder;
import com.fo0.advancedtokenfield.utils.CONSTANTS_AdvancedTokenField;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.polymertemplate.Id;

import lombok.Getter;

@Tag("advancedtokenfield-token-layout")
//@HtmlImport("frontend://AdvancedTokenField/token-layout.html")
public class TokenLayout extends HorizontalLayout {
	private static final long serialVersionUID = -7438343157114436699L;
	
	@Getter
	@Id("label")
	private Span label;
	@Getter
	@Id("remove")
	private Button remove;
	
	@Getter
	private Token data;
	private PropertyChangeListener changeListener = e -> update();
	private AdvancedTokenFieldConfig config;
	
	public static TokenBuilder builder() {
		return Token.builder();
	}
	
	public TokenLayout(Token data, AdvancedTokenFieldConfig config) {
		super();
		
		label = new Span();
		remove = new Button();
		
		add(label, remove);
		
		this.config = config;
		
		if (data != null) {
			data.setLayout(this);
		}
		
		this.data = data;
		
		if (config != null && config.isAllowTokenRemove()) {
			remove.addClickListener(e -> remove());
		} else {
			remove.setVisible(false);
		}
		
		if (config != null && config.getTokenClickListener() != null) {
			getElement().addEventListener("click", e -> {
				Token eData = getData();
				
				if (config.getTokenClickInterceptor() != null) {
					eData = config.getTokenClickInterceptor().action(eData);
				}
				
				if (eData != null) {
					config.getTokenClickListener().action(eData);
				}
			});
		}
		
		update();
	}
	
	public void update() {
		if (data != null && StringUtils.isNotBlank(data.getValue())) {
			label.setText(data.getValue());
		} else {
			label.setText(CONSTANTS_AdvancedTokenField.EMPTY_TOKEN_VALUE);
		}
		
		if (data != null && StringUtils.isNotBlank(data.getDescription())) {
			getElement().setAttribute("title", data.getDescription());
		} else {
			getElement().setAttribute("title", "");
		}
		
		if (data != null && StringUtils.isNotBlank(data.getId())) {
			getElement().setAttribute("data-id", data.getId());
		} else {
			getElement().setAttribute("data-id", "");
		}
		
		if (data != null && StringUtils.isNotBlank(data.getStyle())) {
			getElement().setAttribute("style", data.getStyle());
			
			if (data.getStyle().contains(";background-color:")) {
				String colorStyle = data.getStyle().split(";background-color:")[0];
				
				if (StringUtils.isNotBlank(colorStyle)) {
					remove.getElement().setAttribute("style", colorStyle);
				} else {
					remove.getElement().setAttribute("style", "");
				}
			} else {
				remove.getElement().setAttribute("style", "");
			}
		} else {
			getElement().setAttribute("style", "");
			remove.getElement().setAttribute("style", "");
		}
	}
	
	public void remove() {
		if (config != null && config.getField() != null) {
			config.getField().remove(this);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data != null && data.getId() != null) ? data.getId().hashCode() : 0);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TokenLayout other = (TokenLayout) obj;
		if (data == null) {
			if (other.getData() != null)
				return false;
		} else if (!data.equals(other.getData()))
			return false;
		else if (data != null && !data.idMatch(other.getData()))
				return false;
		return true;
	}

	@Override
	public String toString() {
		if (data != null) {
			return "TokenLayout [" + data.toString() + "]";
		} else {
			return "TokenLayout []";
		}
	}
	
	@Override
	protected void onAttach(AttachEvent attachEvent) {
		super.onAttach(attachEvent);
		
		if (data != null && !ArrayUtils.contains(data.getPropertySupport().getPropertyChangeListeners(), changeListener)) {
			data.getPropertySupport().addPropertyChangeListener(changeListener);
		}
	}
	
	@Override
	protected void onDetach(DetachEvent detachEvent) {
		super.onDetach(detachEvent);
		
		if (data != null && ArrayUtils.contains(data.getPropertySupport().getPropertyChangeListeners(), changeListener)) {
			data.getPropertySupport().removePropertyChangeListener(changeListener);
		}
	}
}