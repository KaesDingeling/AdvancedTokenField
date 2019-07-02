package com.fo0.advancedtokenfield.data.model;

import java.beans.PropertyChangeSupport;

import org.apache.commons.lang3.StringUtils;

import com.fo0.advancedtokenfield.AdvancedTokenField;
import com.fo0.advancedtokenfield.components.TokenLayout;
import com.fo0.advancedtokenfield.data.enums.ETokenStyle;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
public class Token {
	@Getter private final PropertyChangeSupport propertySupport = new PropertyChangeSupport(this);
	
	@Getter private String id;
	@Getter private String value;
	@Getter private String description;
	@Getter private String style;
	
	@Getter
	@Setter
	private Object data;
	
	@Getter
	@Setter
	private TokenLayout layout;
	
	public TokenLayout create(AdvancedTokenField field) {
		return new TokenLayout(this, field.getConfig());
	}
	
	public TokenLayout create(AdvancedTokenFieldConfig config) {
		return new TokenLayout(this, config);
	}
	
	public void setIdAndValue(String idAndValue) {
		setId(idAndValue);
		setValue(idAndValue);
	}
	
	public void setId(String id) {
		String oldValue = this.id;
		this.id = id;
		propertySupport.firePropertyChange("id", oldValue, id);
	}
	
	public void setValue(String value) {
		String oldValue = this.id;
		this.value = value;
		propertySupport.firePropertyChange("value", oldValue, value);
	}
	
	public void setDescription(String description) {
		String oldValue = this.description;
		this.description = description;
		propertySupport.firePropertyChange("description", oldValue, description);
	}

	public void setStyle(ETokenStyle style) {
		setStyle((style != null ? style.getStyle() : ""));
	}
	
	public void setStyle(String style) {
		String oldValue = this.style;
		this.style = style;
		propertySupport.firePropertyChange("style", oldValue, style);
	}
	
	public boolean idMatch(Token other) {
		if (other != null && other.getId() != null) {
			return StringUtils.equals(id, other.getId());
		}
		
		return false;
	}
	
	public Token cloneWithLayout() {
		Token token = clone();
		token.setLayout(layout);
		return token;
	}
	
	@Override
	public Token clone() {
		return Token.builder()
				.id(id)
				.value(value)
				.description(description)
				.style(style)
				.data(data)
				.build();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Token other = (Token) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id != null) ? id.hashCode() : 0);
		return result;
	}
	
	@Override
	public String toString() {
		return "Token ["
				+ (id != null ? "id=" + id + ", " : "")
				+ (value != null ? "value=" + value + ", " : "")
				+ (description != null ? "description=" + description + ", " : "")
				+ (style != null ? "style=" + style + ", " : "") + "]";
	}
}