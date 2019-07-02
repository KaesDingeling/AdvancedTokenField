package com.fo0.advancedtokenfield.demo;

import java.util.List;
import java.util.stream.Collectors;

import com.fo0.advancedtokenfield.AdvancedTokenField;
import com.fo0.advancedtokenfield.data.enums.ETokenStyle;
import com.fo0.advancedtokenfield.data.model.AdvancedTokenFieldConfig;
import com.fo0.advancedtokenfield.data.model.Token;
import com.google.common.collect.Lists;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@PageTitle("Test Page")
@Push
@Route("")
public class MainPage extends VerticalLayout {
	private static final long serialVersionUID = 3377297262856851411L;

	private FormLayout formLayout;
	private Checkbox allowDuplicates;
	private Checkbox allowTokenRemove;
	private Checkbox allowTokenAdd;
	private Checkbox allowTokenAddNew;
	private Checkbox clearAfterAddToken;
	private Checkbox randomTestTokenStyle;
	private AdvancedTokenField availableTokens;
	private AdvancedTokenField initTokens;
	private Button generate;
	
	private AdvancedTokenField testTokenField;
	
	public MainPage() {
		super();
		
		formLayout = new FormLayout();
		
		allowDuplicates = new Checkbox("Allow duplicates", false);
		allowTokenRemove = new Checkbox("Allow remove", true);
		allowTokenAdd = new Checkbox("Allow add", true);
		allowTokenAddNew = new Checkbox("Allow add new", true);
		clearAfterAddToken = new Checkbox("Clear field after add token", true);
		randomTestTokenStyle = new Checkbox("Random test style for tokens", true);
		
		availableTokens = new AdvancedTokenField(AdvancedTokenFieldConfig.builder()
				.availableTokens(createAvailableDefaultTokens())
				.initTokens(createAvailableDefaultTokens())
				.allowTokenAdd(true)
				.allowTokenAddNew(true)
				.allowTokenRemove(true)
				.tokenNewItemInterceptor(i -> {
					if (randomTestTokenStyle.getValue()) {
						createTokenStyle(i);
					}
					
					return i;
				})
				.build());
		
		initTokens = new AdvancedTokenField(AdvancedTokenFieldConfig.builder()
				.availableTokens(createDefaultTokens())
				.initTokens(createDefaultTokens())
				.allowTokenAdd(true)
				.allowTokenAddNew(true)
				.allowTokenRemove(true)
				.tokenNewItemInterceptor(i -> {
					if (randomTestTokenStyle.getValue()) {
						createTokenStyle(i);
					}
					
					return i;
				})
				.build());
		
		generate = new Button("Generate");
		generate.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		generate.addClickListener(e -> generate());
		
		formLayout.add(allowDuplicates, allowTokenRemove, allowTokenAdd, allowTokenAddNew, clearAfterAddToken, randomTestTokenStyle);
		
		add(new Label("Configuration"), new HorizontalLayout(formLayout));
		
		add(new Label("Available tokens"), availableTokens);
		add(new Label("Init tokens"), initTokens);
		add(generate);
		
		add(new Label());
		add(new H3("Generated component"));
		
		generate();
	}
	
	private void generate() {
		if (testTokenField != null) {
			remove(testTokenField);
		}
		
		testTokenField = new AdvancedTokenField(AdvancedTokenFieldConfig.builder()
				.availableTokens(availableTokens.getAllTokens().stream().map(i -> i.clone()).collect(Collectors.toList()))
				.initTokens(initTokens.getAllTokens().stream().map(i -> i.clone()).collect(Collectors.toList()))
				.allowDuplicates(allowDuplicates.getValue())
				.allowTokenAdd(allowTokenAdd.getValue())
				.allowTokenAddNew(allowTokenAddNew.getValue())
				.allowTokenRemove(allowTokenRemove.getValue())
				.tokenNewItemInterceptor(i -> {
					if (randomTestTokenStyle.getValue()) {
						createTokenStyle(i);
					}
					
					return i;
				})
				.build());
		
		add(testTokenField);
	}
	
	private List<Token> createDefaultTokens() {
		return Lists.newArrayList(createToken("token1"),
				createToken("token2"),
				createToken("token3"),
				createToken("token4"),
				createToken("token6"));
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
	
	private int counter = 0;
	private ETokenStyle[] allStyles = ETokenStyle.values();
	
	private Token createTokenStyle(Token token) {
		if (counter == (allStyles.length + 1)) {
			counter = 0;
		}
		
		if (counter > 0) {
			token.setStyle(allStyles[counter - 1].getStyle());
		}
		
		counter++;
		
		return token;
	}
	
	private Token createToken(String value) {
		Token token = Token.builder().build();
		
		createTokenStyle(token);
		
		token.setIdAndValue(value);
		
		return token;
	}
}