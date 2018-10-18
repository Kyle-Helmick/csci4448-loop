package io.github.kyle_helmick.loop.extractors;

import io.github.kyle_helmick.loop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GithubPrincipalExtractor implements PrincipalExtractor {

  private final UserService userService;

  @Autowired
  public GithubPrincipalExtractor(UserService userService) {
    this.userService = userService;
  }

  @Override
  public Object extractPrincipal(Map<String, Object> map) {
    userService.registerNewUser(map);
    return map;
  }

}
