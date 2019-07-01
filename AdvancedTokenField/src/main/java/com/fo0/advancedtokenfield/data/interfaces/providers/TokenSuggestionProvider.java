package com.fo0.advancedtokenfield.data.interfaces.providers;

import java.util.List;

import com.fo0.advancedtokenfield.data.model.AdvancedTokenFieldConfig;
import com.fo0.advancedtokenfield.data.model.Token;

public interface TokenSuggestionProvider {
	public List<String> findAvailableOptions(String searchValue, AdvancedTokenFieldConfig config);
	public Token findTokenByString(String searchValue, AdvancedTokenFieldConfig config);
}