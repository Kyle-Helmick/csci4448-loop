package io.github.kyle_helmick.loop.configs;

import io.github.kyle_helmick.loop.extractors.GithubPrincipalExtractor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

import javax.servlet.Filter;

@Configuration
public class SsoFilter {

  private final GithubPrincipalExtractor principalExtractor;

  @Autowired
  public SsoFilter(GithubPrincipalExtractor principalExtractor) {
    this.principalExtractor = principalExtractor;
  }

  @Bean(name = "githubFilter")
  public Filter ssoFilter(OAuth2ClientContext oauth2ClientContext) {
    OAuth2ClientAuthenticationProcessingFilter githubFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/github");
    OAuth2RestTemplate githubTemplate = new OAuth2RestTemplate(github(), oauth2ClientContext);
    githubFilter.setRestTemplate(githubTemplate);
    UserInfoTokenServices tokenServices = new UserInfoTokenServices(githubResource().getUserInfoUri(), github().getClientId());
    tokenServices.setRestTemplate(githubTemplate);
    tokenServices.setPrincipalExtractor(principalExtractor);
    githubFilter.setTokenServices(tokenServices);
    return githubFilter;
  }

  @Bean
  public FilterRegistrationBean oauth2ClientFilterRegistration(
    OAuth2ClientContextFilter filter) {
    FilterRegistrationBean registration = new FilterRegistrationBean();
    registration.setFilter(filter);
    registration.setOrder(-100);
    return registration;
  }

  @Bean
  @ConfigurationProperties("github.client")
  public AuthorizationCodeResourceDetails github() {
    return new AuthorizationCodeResourceDetails();
  }

  @Bean
  @ConfigurationProperties("github.resource")
  public ResourceServerProperties githubResource() {
    return new ResourceServerProperties();
  }

}
