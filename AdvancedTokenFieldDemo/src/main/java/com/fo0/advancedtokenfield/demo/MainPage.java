package com.fo0.advancedtokenfield.demo;

import java.util.List;

import com.fo0.advancedtokenfield.AdvancedTokenField;
import com.fo0.advancedtokenfield.data.model.AdvancedTokenFieldConfig;
import com.fo0.advancedtokenfield.data.model.Token;
import com.google.common.collect.Lists;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Test Page")
@Push
@Route("")
public class MainPage extends VerticalLayout {
	private static final long serialVersionUID = 3377297262856851411L;

	public MainPage() {
		super();
		
		add(new Label("TokenField with add/addNew/remove"));
		add(new AdvancedTokenField(AdvancedTokenFieldConfig.builder()
				.availableTokens(createAvailableDefaultTokens())
				.initTokens(createDefaultTokens())
				.allowTokenAdd(true)
				.allowTokenAddNew(true)
				.allowTokenRemove(true)
				.build()));
		
		add(new Label("TokenField with add/remove"));
		add(new AdvancedTokenField(AdvancedTokenFieldConfig.builder()
				.availableTokens(createAvailableDefaultTokens())
				.initTokens(createDefaultTokens())
				.allowTokenAdd(true)
				.allowTokenAddNew(false)
				.allowTokenRemove(true)
				.build()));
		
		add(new Label("TokenField with add"));
		add(new AdvancedTokenField(AdvancedTokenFieldConfig.builder()
				.availableTokens(createAvailableDefaultTokens())
				.initTokens(createDefaultTokens())
				.allowTokenAdd(true)
				.allowTokenAddNew(false)
				.allowTokenRemove(false)
				.build()));
		
		add(new Label("TokenField with remove and no input"));
		add(new AdvancedTokenField(AdvancedTokenFieldConfig.builder()
				.availableTokens(createAvailableDefaultTokens())
				.initTokens(createDefaultTokens())
				.allowTokenAdd(false)
				.allowTokenAddNew(false)
				.allowTokenRemove(true)
				.build()));
		
		add(new Label("TokenField with no input"));
		add(new AdvancedTokenField(AdvancedTokenFieldConfig.builder()
				.availableTokens(createAvailableDefaultTokens())
				.initTokens(createDefaultTokens())
				.allowTokenAdd(false)
				.allowTokenAddNew(false)
				.allowTokenRemove(false)
				.build()));
		
		add(new Label("TokenField with add/addNew/remove and allows duplicates"));
		add(new AdvancedTokenField(AdvancedTokenFieldConfig.builder()
				.availableTokens(createAvailableDefaultTokens())
				.initTokens(createDefaultTokens())
				.allowDuplicates(true)
				.allowTokenAdd(true)
				.allowTokenAddNew(true)
				.allowTokenRemove(true)
				.build()));
	}
	
	private List<Token> createDefaultTokens() {
		return Lists.newArrayList(createToken("token1"),
				createToken("token2"),
				createToken("token3"),
				createToken("token4"),
				createToken("token5"));
	}
	
	private List<Token> createAvailableDefaultTokens() {
		return Lists.newArrayList(createToken("token1"),
				createToken("token2"),
				createToken("token3"),
				createToken("token4"),
				createToken("token5"),
				createToken("token6"),
				createToken("token7"),
				createToken("token8"),
				createToken("token9"));
	}
	
	private Token createToken(String value) {
		return Token.builder()
				.id(value)
				.value(value)
				.build();
	}
}