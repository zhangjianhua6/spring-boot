package com.jason.oauth.config;

import com.jason.oauth.service.RedisAuthorizationCodeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.implicit.ImplicitTokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * token授权模式配置类
 */
@Configuration
public class TokenGranterConfig {
    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenStore tokenStore;

    @Autowired(required = false)
    private TokenEnhancer tokenEnhancer;

    @Autowired
    private RedisAuthorizationCodeServices authorizationCodeServices;

    private boolean reuseRefreshToken = true;

    private AuthorizationServerTokenServices tokenServices;

    private TokenGranter tokenGranter;

    /**
     * 授权模式
     */
    @Bean
    public TokenGranter tokenGranter() {
        if (tokenGranter == null) {
            tokenGranter = new TokenGranter() {
                private CompositeTokenGranter delegate;

                @Override
                public OAuth2AccessToken grant(String grantType, TokenRequest tokenRequest) {
                    if (delegate == null) {
                        delegate = new CompositeTokenGranter(getAllTokenGranters());
                    }
                    return delegate.grant(grantType, tokenRequest);
                }
            };
        }
        return tokenGranter;
    }

    /**
     * 所有授权模式：默认的5种模式 + 自定义的模式
     */
    private List<TokenGranter> getAllTokenGranters() {
        AuthorizationServerTokenServices tokenServices = tokenServices();
        OAuth2RequestFactory requestFactory = requestFactory();
        //获取默认的授权模式
        List<TokenGranter> tokenGranters = getDefaultTokenGranters(tokenServices, authorizationCodeServices, requestFactory);
        return tokenGranters;
    }

    /**
     * 默认的授权模式
     */
    private List<TokenGranter> getDefaultTokenGranters(AuthorizationServerTokenServices tokenServices
            , AuthorizationCodeServices authorizationCodeServices, OAuth2RequestFactory requestFactory) {
        List<TokenGranter> tokenGranters = new ArrayList<>();
        // 添加授权码模式
        tokenGranters.add(new AuthorizationCodeTokenGranter(tokenServices, authorizationCodeServices, clientDetailsService, requestFactory));
        // 添加刷新令牌的模式
        tokenGranters.add(new RefreshTokenGranter(tokenServices, clientDetailsService, requestFactory));
        // 添加隐士授权模式
        tokenGranters.add(new ImplicitTokenGranter(tokenServices, clientDetailsService, requestFactory));
        // 添加客户端模式
        tokenGranters.add(new ClientCredentialsTokenGranter(tokenServices, clientDetailsService, requestFactory));
        if (authenticationManager != null) {
            // 添加密码模式
            tokenGranters.add(new ResourceOwnerPasswordTokenGranter(authenticationManager, tokenServices, clientDetailsService, requestFactory));
        }
        return tokenGranters;
    }

    @Bean
    public AuthorizationServerTokenServices tokenServices() {
        if (tokenServices != null) {
            return tokenServices;
        }
        this.tokenServices = createDefaultTokenServices();
        return tokenServices;
    }

    private OAuth2RequestFactory requestFactory() {
        return new DefaultOAuth2RequestFactory(clientDetailsService);
    }

    private DefaultTokenServices createDefaultTokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore);
        tokenServices.setSupportRefreshToken(reuseRefreshToken);
        tokenServices.setReuseRefreshToken(reuseRefreshToken);
        tokenServices.setClientDetailsService(clientDetailsService);
        tokenServices.setTokenEnhancer(tokenEnhancer);
        addUserDetailsService(tokenServices, this.userDetailsService);
        return tokenServices;
    }

    private void addUserDetailsService(DefaultTokenServices tokenServices, UserDetailsService userDetailsService) {
        if (userDetailsService != null) {
            PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
            provider.setPreAuthenticatedUserDetailsService(new UserDetailsByNameServiceWrapper<>(userDetailsService));
            tokenServices.setAuthenticationManager(new ProviderManager(Collections.singletonList(provider)));
        }
    }
}
