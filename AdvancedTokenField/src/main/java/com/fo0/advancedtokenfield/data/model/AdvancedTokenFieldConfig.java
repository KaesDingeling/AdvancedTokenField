package com.fo0.advancedtokenfield.data.model;

import java.util.List;

import com.fo0.advancedtokenfield.AdvancedTokenField;
import com.fo0.advancedtokenfield.data.interfaces.interceptor.TokenAddInterceptor;
import com.fo0.advancedtokenfield.data.interfaces.interceptor.TokenClickInterceptor;
import com.fo0.advancedtokenfield.data.interfaces.interceptor.TokenNewItemInterceptor;
import com.fo0.advancedtokenfield.data.interfaces.interceptor.TokenRemoveInterceptor;
import com.fo0.advancedtokenfield.data.interfaces.listeners.TokenAddListener;
import com.fo0.advancedtokenfield.data.interfaces.listeners.TokenClickListener;
import com.fo0.advancedtokenfield.data.interfaces.listeners.TokenNewItemListener;
import com.fo0.advancedtokenfield.data.interfaces.listeners.TokenRemoveListener;
import com.fo0.advancedtokenfield.data.interfaces.providers.TokenSuggestionProvider;
import com.google.common.collect.Lists;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdvancedTokenFieldConfig {
	@Builder.Default
	private List<Token> availableTokens = Lists.newArrayList();
	private List<Token> initTokens;

	/**
	 * Interceptors
	 */
	private TokenRemoveInterceptor tokenRemoveInterceptor;
	private TokenNewItemInterceptor tokenNewItemInterceptor;
	private TokenAddInterceptor tokenAddInterceptor;
	private TokenClickInterceptor tokenClickInterceptor;
	
	/**
	 * Suggestion Generator
	 */
	private TokenSuggestionProvider suggestionProvider;
	
	/**
	 * Internal
	 */
	private AdvancedTokenField field;

	/**
	 * Listener
	 */
	private TokenRemoveListener tokenRemoveListener;
	private TokenNewItemListener tokenNewItemListener;
	private TokenAddListener tokenAddListener;
	private TokenClickListener tokenClickListener;

	@Builder.Default
	private boolean allowDuplicates = false;
	@Builder.Default
	private boolean allowTokenRemove = false;
	@Builder.Default
	private boolean allowTokenAdd = false;
	@Builder.Default
	private boolean allowTokenAddNew = false;
	@Builder.Default
	private boolean clearAfterAddToken = true;
	
	@Builder.Default
	private String placeholder = "Add Tokens";
	
	/**
	 * <= 0 is no limit
	 */
	@Builder.Default
	private int maxVisibleSuggestion = 5;
}