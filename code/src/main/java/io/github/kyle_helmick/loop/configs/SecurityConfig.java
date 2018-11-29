package io.github.kyle_helmick.loop.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import javax.servlet.Filter;

@Configuration
@EnableOAuth2Client
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final Filter ssoFilter;

  @Autowired
  public SecurityConfig(@Qualifier("githubFilter") Filter ssoFilter) {
    this.ssoFilter = ssoFilter;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .authorizeRequests()
      .antMatchers("/", "/login**",
                   "/webjars/**", "/error**",
                   "/css/**", "/js/**",
                   "/images/**", "/favicon.ico")
      .permitAll().anyRequest().authenticated()
      .and().headers().frameOptions().sameOrigin()
      .and().logout().logoutSuccessUrl("/").permitAll()
      .and().csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
      .and().addFilterBefore(ssoFilter, BasicAuthenticationFilter.class);
  }

}
