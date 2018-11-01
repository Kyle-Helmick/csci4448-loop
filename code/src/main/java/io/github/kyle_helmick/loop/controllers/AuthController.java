package io.github.kyle_helmick.loop.controllers;

import io.github.kyle_helmick.loop.models.User;
import io.github.kyle_helmick.loop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class AuthController {

  private final UserService userService;

  @Autowired
  public AuthController(UserService userService) {
    this.userService = userService;
  }

  /**
   * This route is an easy way to authenticate the user and return their identifier to them.
   * Probably isn't smart/safe
   * @param principal is the github information passed from GitHub OAuth2
   * @return the User if the user is found in the database (they should be at this point)
   */
  @RequestMapping("/authenticate")
  public @ResponseBody User identifyUser(Principal principal) {
    Pattern pattern = Pattern.compile("node_id=(.*?)[,]");
    Matcher m = pattern.matcher(principal.getName());
    if (m.find()) {
      return userService.getUser(m.group(1));
    }
    return null;
  }
}
