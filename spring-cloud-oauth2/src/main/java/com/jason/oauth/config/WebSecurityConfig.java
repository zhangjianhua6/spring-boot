package com.jason.oauth.config;

import com.jason.common.config.DefaultPasswordConfig;
import com.jason.oauth.handler.OauthLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.annotation.Resource;

/**
 * spring security配置
 * 在WebSecurityConfigurerAdapter不拦截oauth要开放的资源
 */
@Configuration
@Import(DefaultPasswordConfig.class)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


	@Autowired(required = false)
	private AuthenticationEntryPoint authenticationEntryPoint;

	@Resource
	private LogoutHandler oauthLogoutHandler;


	@Autowired
	private PasswordEncoder passwordEncoder;

	/**
	 * 这一步的配置是必不可少的，否则SpringBoot会自动配置一个AuthenticationManager,覆盖掉内存中的用户
	 * @return 认证管理对象
	 */
	@Bean
    @Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
					.anyRequest()
					//授权服务器关闭basic认证
                    .permitAll()
                    .and()
				.logout()
					.logoutUrl("/oauth/logout")
					.logoutSuccessHandler(new OauthLogoutSuccessHandler())
					.addLogoutHandler(oauthLogoutHandler)
					.clearAuthentication(true)
					.and()
                .formLogin()
                    .and()
                .csrf().disable()
				// 解决不允许显示在iframe的问题
				.headers().frameOptions().disable().cacheControl();

		// 基于密码 等模式可以无session,不支持授权码模式
		if (authenticationEntryPoint != null) {
			http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint);
			http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		} else {
			// 授权码模式单独处理，需要session的支持，此模式可以支持所有oauth2的认证
			http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
		}
	}

	/**
	 * 全局用户信息
	 */
	@Autowired
	public void globalUserDetails(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder);
	}

    //配置用户信息服务
    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("zhangsan").password("123456").authorities("p1").build());
        manager.createUser(User.withUsername("lisi").password("123456").authorities("p1").build());
        return manager;
    }

}
