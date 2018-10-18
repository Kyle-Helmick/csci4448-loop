package io.github.kyle_helmick.loop.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.servlet.Filter;

@Configuration
@EnableOAuth2Client
//@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final OAuth2ClientContext oauth2ClientContext;

  private final Filter ssoFilter;

  @Autowired
  public SecurityConfig(OAuth2ClientContext oauth2ClientContext, @Qualifier("githubFilter") Filter ssoFilter) {
    this.oauth2ClientContext = oauth2ClientContext;
    this.ssoFilter = ssoFilter;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .antMatcher("/**").authorizeRequests()
      .antMatchers("/", "/login**", "/webjars/**", "/error**", "/css/**", "/js/**", "/images/**").permitAll()
      .anyRequest().authenticated()
      .and().logout().logoutSuccessUrl("/").permitAll()
      .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
      .and().addFilterBefore(ssoFilter, BasicAuthenticationFilter.class);
  }

}
