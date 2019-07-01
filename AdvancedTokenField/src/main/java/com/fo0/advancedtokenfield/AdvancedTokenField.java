package com.fo0.advancedtokenfield;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import com.fo0.advancedtokenfield.components.TokenLayout;
import com.fo0.advancedtokenfield.data.defaults.DefaultTokenSuggestionProvider;
import com.fo0.advancedtokenfield.data.model.AdvancedTokenFieldConfig;
import com.fo0.advancedtokenfield.data.model.Token;
import com.fo0.advancedtokenfield.utils.CONSTANTS_AdvancedTokenField;
import com.google.common.collect.Lists;
import com.vaadin.componentfactory.Autocomplete;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import lombok.Getter;

@HtmlImport("frontend://AdvancedTokenField/styles.html")
public class AdvancedTokenField extends HorizontalLayout {
	private static final long serialVersionUID = 8139678186130686248L;

	@Getter
	private HorizontalLayout tokens;
	@Getter
	private Autocomplete inputField;

	@Getter
	private AdvancedTokenFieldConfig config;

	/**
	 * Should not be used
	 */
	@Deprecated
	public AdvancedTokenField() {
		this(null);
	}
	
	public AdvancedTokenField(AdvancedTokenFieldConfig config) {
		super();
		
		this.config = config;
		
		init();
	}
	
	private void init() {
		getClassNames().add(CONSTANTS_AdvancedTokenField.ROOT_STYLE);
		
		setSpacing(false);
		
		if (config == null) {
			config = AdvancedTokenFieldConfig.builder().build();
		}
		
		config.setField(this);
		
		if (config.getSuggestionProvider() == null) {
			config.setSuggestionProvider(new DefaultTokenSuggestionProvider());
		}
		
		tokens = new HorizontalLayout();
		tokens.getClassNames().add(CONSTANTS_AdvancedTokenField.TOKENS_STYLE);
		
		add(tokens);
		expand(tokens);
		
		if (config.isAllowTokenAdd()) {
			inputField = new Autocomplete();
			inputField.addChangeListener(e -> setToInputField(e.getValue()));
			inputField.addAutocompleteValueAppliedListener(e -> {
				Token token = config.getSuggestionProvider().findTokenByString(e.getValue(), config);
				
				if (token != null) {
					add(token);
					
					if (config.isClearAfterAddToken()) {
						e.getSource().getElement().callFunction("clear");
					}
				}
			});
			
			if (StringUtils.isNotBlank(config.getPlaceholder())) {
				inputField.setPlaceholder(config.getPlaceholder());
			}
			
			if (config.getMaxVisibleSuggestion() > 0) {
				inputField.setLimit(config.getMaxVisibleSuggestion());
			}
			
			setToInputField(inputField.getValue());
			
			add(inputField);
		}
		
		if (CollectionUtils.isNotEmpty(config.getInitTokens())) {
			for (Token token : config.getInitTokens()) {
				add(token);
			}
		}
	}
	
	private void setToInputField(String searchValue) {
		if (inputField != null) {
			inputField.setOptions(config.getSuggestionProvider().findAvailableOptions(searchValue, config));
		}
	}
	
	public void add(Token... tokens) {
		if (ArrayUtils.isNotEmpty(tokens)) {
			for (Token token : tokens) {
				if (token.getLayout() == null) {
					token.create(config);
				}
				
				if (config.getTokenAddInterceptor() != null) {
					token = config.getTokenAddInterceptor().action(token);
				}
				
				if (token != null) {
					this.tokens.add(token.getLayout());
					
					if (config.getTokenAddListener() != null) {
						config.getTokenAddListener().action(token);
					}
				}
			}
		}
	}
	
	public void add(TokenLayout... tokens) {
		if (ArrayUtils.isNotEmpty(tokens)) {
			for (TokenLayout token : tokens) {
				add(token.getData());
			}
		}
	}
	
	public List<TokenLayout> getAllTokenLayouts() {
		List<TokenLayout> list = Lists.newArrayList();
		for (int i = 0; i < tokens.getComponentCount(); i++) {
			if (tokens.getComponentAt(i) instanceof TokenLayout) {
				list.add((TokenLayout) tokens.getComponentAt(i));
			}
		}
		return list;
	}
	
	public List<Token> getAllTokens() {
		return getAllTokenLayouts().stream().map(TokenLayout::getData).collect(Collectors.toList());
	}
	
	public void remove(Token... tokens) {
		if (ArrayUtils.isNotEmpty(tokens)) {
			for (Token token : tokens) {
				remove(token.getLayout());
			}
		}
	}
	
	public void remove(TokenLayout... tokens) {
		if (ArrayUtils.isNotEmpty(tokens)) {
			for (TokenLayout token : tokens) {
				Token tokenData = token.getData();
				
				if (config.getTokenRemoveInterceptor() != null) {
					tokenData = config.getTokenRemoveInterceptor().action(tokenData);
				}
				
				if (tokenData != null) {
					this.tokens.remove(token);
					
					if (config.getTokenRemoveListener() != null) {
						config.getTokenRemoveListener().action(tokenData);
					}
				}
			}
		}
	}
	
	
	
	
	
	
	
	/*
	

	protected void copyInputfieldTokensToInitTokens() {
		initTokensOfField.clear();
		initTokensOfField.addAll(tokensOfField);
	}

	public void setQuerySuggestionInputMinLength(int minLength) {
		this.querySuggestionInputMinLength = minLength;
		suggestionProvider.setQuerySuggestionInputMinLength(querySuggestionInputMinLength);
	}

	public void overwriteEmptyTokenValue(String emptyTokenValue) {
		this.EMPTY_TOKEN = emptyTokenValue;
	}

	public void addToken(Token token) {
		// -1 because always put as last element
		addToken(token, -1);
	}

	public void addTokens(List<Token> token) {
		token.stream().forEach(e -> addToken(e));
	}

	public void addTokensToInputField(List<Token> tokens) {
		if (tokens == null || tokens.isEmpty())
			return;

		addTokensToInputField(tokens.stream().toArray(Token[]::new));
	}

	public void addTokensToInputField(Token... tokens) {
		List<Token> list = Stream.of(tokens).distinct().filter(e -> !tokensOfField.contains(e))
				.collect(Collectors.toList());
		if (list == null || list.isEmpty())
			return;

		tokensOfField.addAll(list);

		copyInputfieldTokensToInitTokens();
	}

	public void addTokenToInputField(Token token) {
		addTokensToInputField(token);
	}

	public void clearTokens() {
		List<Component> componentsToRemove = new ArrayList<Component>();

		IntStream.range(0, getComponentCount()).forEach(e -> {
			if (getComponent(e) instanceof CssLayout) {
				componentsToRemove.add(getComponent(e));
			}
		});

		componentsToRemove.stream().forEach(e -> {
			removeComponent(e);
		});
	}

	public void clearAll() {
		clearTokens();
		tokensOfField.clear();
	}

	private void initt() {
		addStyleName(BASE_STYLE);

		suggestionProvider = new AltTokenSuggestionProvider(tokensOfField, querySuggestionInputMinLength);
		inputField = new SearchDropDown<Token>(suggestionProvider);
		inputField.addStyleName(ValoTheme.TEXTFIELD_BORDERLESS);
		inputField.addSearchListener(search -> {
			String input = search.getSource().getValue().trim();
			if (StringUtils.isEmpty(input)) {
				return;
			}

			if (!allowNewTokens) {
				Token token = tokensOfField.stream().filter(e -> e.getValue().equals(input)).findFirst().orElse(null);

				if (token != null)
					addToken(token);
			} else {
				Token token = tokensOfField.stream().filter(e -> e.getValue().equals(input)).findFirst().orElse(null);

				if (token != null) {
					addToken(token);
				} else {
					token = tokenNewItemInterceptor.action(Token.builder().idAndValue(input).build());
					addToken(token);
				}
			}
		});

		addComponent(inputField);

		tokenAddInterceptor = new TokenAddInterceptor() {

			@Override
			public Token action(Token token) {
				return token;
			}
		};

		tokenRemoveInterceptor = new TokenRemoveInterceptor() {

			@Override
			public Token action(Token event) {
				return event;
			}
		};

		tokenNewItemInterceptor = new TokenNewItemInterceptor() {

			@Override
			public Token action(Token token) {
				return token;
			}
		};

		copyInputfieldTokensToInitTokens();
	}

	@Override
	public Registration addComponentAttachListener(ComponentAttachListener listener) {
		if (TOKENFIELD_CONSTANTS.DEBUG)
			System.out.println("add detecting class attach");
		return super.addComponentAttachListener(listener);
	}

	@Override
	public void removeComponent(Component c) {
		if (TOKENFIELD_CONSTANTS.DEBUG)
			System.out.println("remove detecting class: " + c.getClass());
		if (c instanceof TokenLayout) {
			// detect the drag and drop from layout
			removeToken(((TokenLayout) c).getToken());
			return;
		}
		super.removeComponent(c);
	}

	@Override
	public void addComponentAsFirst(Component c) {
		if (TOKENFIELD_CONSTANTS.DEBUG)
			System.out.println("add detecting class: " + c.getClass());
		if (c instanceof TokenLayout) {
			// detect the drag and drop from layout
			addToken(((TokenLayout) c).getToken(), getComponentCount());
			return;
		}

		super.addComponent(c);
	}

	@Override
	public void addComponent(Component c) {
		if (c instanceof TokenLayout) {
			// detect the drag and drop from layout
			addToken(((TokenLayout) c).getToken(), -1);
			return;
		}

		super.addComponent(c);
	}

	@Override
	public void addComponent(Component c, int index) {
		if (c instanceof TokenLayout) {
			// detect the drag and drop from layout
			addToken(((TokenLayout) c).getToken(), index);
			return;
		}

		super.addComponent(c, index);
	}

	public void removeToken(Token token) {
		Token tokenData = tokenRemoveInterceptor.action(token);

		if (tokenData == null) {
			// prevent remove if interceptor not allow
			return;
		}

		// search in layout and remove if found
		TokenLayout tl = null;
		for (Iterator<Component> iterator = iterator(); iterator.hasNext();) {
			Component component = (Component) iterator.next();
			if (component instanceof TokenLayout) {
				if (((TokenLayout) component).getToken().equals(token)) {
					tl = (TokenLayout) component;
					break;
				}
			}
		}

		if (tl != null && tl.getToken() != null && tl.getToken().equals(tokenData)) {
			super.removeComponent(tl);
		}

		if (tokenRemoveListener != null && tl != null) {
			tokenRemoveListener.action(tl);
		}

		if (removeInitTokens || !initTokensOfField.contains(tokenData)) {
			if (TOKENFIELD_CONSTANTS.DEBUG) {
				System.out.println("remove init tokens: " + removeInitTokens);

				System.out.println("in  init token: " + initTokensOfField.stream().anyMatch(e -> e.equals(tokenData)));
				System.out.println("removing token: " + tokenData);
			}
			tokensOfField.remove(tokenData);
		}
	}

	public void addToken(Token token, int idx) {
		Token tokenData = tokenAddInterceptor.action(token);
		if (tokenData == null) {
			// filter empty tokens
			return;
		}

		TokenLayout tokenLayout = new TokenLayout(tokenData, EMPTY_TOKEN, tokenClickListener, tokenCloseButton);

		if (tokenCloseButton)
			tokenLayout.getBtn().addClickListener(e -> {
				removeToken(tokenLayout.getToken());
			});

		addTokenToInputField(tokenData);

		if (idx > -1) {
			super.addComponent(tokenLayout, idx);
		} else {
			super.addComponent(tokenLayout, getComponentCount() - 1);

		}

		if (tokenAddListener != null)
			tokenAddListener.action(tokenData);

		inputField.clear();
	}

	*/
}