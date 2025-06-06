/*
 * Copyright 2002-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.security.oauth2.client.endpoint;

import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;

/**
 * An implementation of an {@link AbstractOAuth2AuthorizationGrantRequestEntityConverter}
 * that converts the provided {@link OAuth2ClientCredentialsGrantRequest} to a
 * {@link RequestEntity} representation of an OAuth 2.0 Access Token Request for the
 * Client Credentials Grant.
 *
 * @author Joe Grandja
 * @since 5.1
 * @see AbstractOAuth2AuthorizationGrantRequestEntityConverter
 * @see OAuth2ClientCredentialsGrantRequest
 * @see RequestEntity
 * @deprecated Use {@link DefaultOAuth2TokenRequestParametersConverter} instead
 */
@Deprecated(since = "6.4", forRemoval = true)
public class OAuth2ClientCredentialsGrantRequestEntityConverter
		extends AbstractOAuth2AuthorizationGrantRequestEntityConverter<OAuth2ClientCredentialsGrantRequest> {

	@Override
	protected MultiValueMap<String, String> createParameters(
			OAuth2ClientCredentialsGrantRequest clientCredentialsGrantRequest) {
		ClientRegistration clientRegistration = clientCredentialsGrantRequest.getClientRegistration();
		MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
		parameters.add(OAuth2ParameterNames.GRANT_TYPE, clientCredentialsGrantRequest.getGrantType().getValue());
		if (!CollectionUtils.isEmpty(clientRegistration.getScopes())) {
			parameters.add(OAuth2ParameterNames.SCOPE,
					StringUtils.collectionToDelimitedString(clientRegistration.getScopes(), " "));
		}
		if (ClientAuthenticationMethod.CLIENT_SECRET_POST.equals(clientRegistration.getClientAuthenticationMethod())) {
			parameters.add(OAuth2ParameterNames.CLIENT_ID, clientRegistration.getClientId());
			parameters.add(OAuth2ParameterNames.CLIENT_SECRET, clientRegistration.getClientSecret());
		}
		return parameters;
	}

}
