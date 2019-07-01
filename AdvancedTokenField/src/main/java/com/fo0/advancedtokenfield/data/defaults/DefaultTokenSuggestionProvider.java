package com.fo0.advancedtokenfield.data.defaults;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.fo0.advancedtokenfield.data.interfaces.providers.TokenSuggestionProvider;
import com.fo0.advancedtokenfield.data.model.AdvancedTokenFieldConfig;
import com.fo0.advancedtokenfield.data.model.Token;
import com.google.common.collect.Lists;

public class DefaultTokenSuggestionProvider implements TokenSuggestionProvider {
	@Override
	public List<String> findAvailableOptions(String searchValue, AdvancedTokenFieldConfig config) {
		List<String> availableOptions = null;
		
		if (CollectionUtils.isNotEmpty(config.getAvailableTokens())) {
			List<Token> availableTokenOptions = config.getAvailableTokens().stream().filter(i -> StringUtils.containsIgnoreCase(i.getValue(), searchValue)).collect(Collectors.toList());
			
			if (!config.isAllowDuplicates()) {
				List<Token> usedTokens = config.getField().getAllTokens();
				
				availableTokenOptions = availableTokenOptions.stream().filter(i -> !usedTokens.contains(i)).collect(Collectors.toList());
			}
			
			availableOptions = availableTokenOptions.stream().map(Token::getValue).collect(Collectors.toList());
		}
		
		if (availableOptions == null) {
			availableOptions = Lists.newArrayList();
		}
		
		return availableOptions;
	}

	@Override
	public Token findTokenByString(String searchValue, AdvancedTokenFieldConfig config) {
		Token token = null;
		
		if (CollectionUtils.isNotEmpty(config.getAvailableTokens())) {
			Optional<Token> optional = config.getAvailableTokens().stream().filter(i -> StringUtils.equals(i.getValue(), searchValue)).findFirst();
			
			if (optional.isPresent()) {
				token = optional.get();
			}
		}
		
		if (token == null && config.isAllowTokenAddNew() && StringUtils.isNotBlank(searchValue)) {
			token = Token.builder()
					.id(searchValue)
					.value(searchValue)
					.build();
			
			if (config.getTokenNewItemInterceptor() != null) {
				token = config.getTokenNewItemInterceptor().action(token);
			}
			
			if (token != null && config.getTokenNewItemListener() != null) {
				config.getTokenNewItemListener().action(token);
			}
		}
		
		return token;
	}
}