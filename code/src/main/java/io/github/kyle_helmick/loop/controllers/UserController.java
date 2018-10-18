package io.github.kyle_helmick.loop.controllers;

import io.github.kyle_helmick.loop.models.User;
import io.github.kyle_helmick.loop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @RequestMapping("/user")
  public @ResponseBody User identifyUser(Principal principal) {
    Pattern pattern = Pattern.compile("node_id=(.*?)[,]");
    Matcher m = pattern.matcher(principal.getName());
    if (m.find()) {
      return userService.getUser(m.group(1));
    }
    return null;
  }

  @GetMapping(value = "/user/{userId}")
  public @ResponseBody User getUser(@PathVariable String userId) {
    return userService.getUser(userId);
  }

}
