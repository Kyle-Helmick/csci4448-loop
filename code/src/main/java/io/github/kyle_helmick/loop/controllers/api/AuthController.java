package io.github.kyle_helmick.loop.controllers.api;

import io.github.kyle_helmick.loop.models.Message;
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
   * @return the a response Message with the user or a fail with error message
   */
  @RequestMapping("/authenticate")
  public @ResponseBody Message identifyUser(Principal principal) {
    Pattern pattern = Pattern.compile("node_id=(.*?)[,]");
    Matcher m = pattern.matcher(principal.getName());
    if (m.find()) {
      User user = userService.getUser(m.group(1));
      return new Message.Builder().withSuccess().withData(user).build();
    }
    return new Message.Builder().withFail().withData("User not found").build();
  }
}
