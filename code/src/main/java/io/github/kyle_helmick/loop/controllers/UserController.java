package io.github.kyle_helmick.loop.controllers;

import io.github.kyle_helmick.loop.models.User;
import io.github.kyle_helmick.loop.models.UserUpdate;
import io.github.kyle_helmick.loop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.naming.Binding;
import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping(value = "/users/{userId}")
  public @ResponseBody User getUser(@PathVariable String userId) {
    return userService.getUser(userId);
  }

  /**
   *
   * @param principal
   * @param userUpdate
   * @return
   */
  @PostMapping("/user/update")
  public boolean update(Principal principal,
                             @Valid @ModelAttribute UserUpdate userUpdate,
                             BindingResult result) {

    if (result.hasErrors()) {
      System.out.println(result.getAllErrors().toString());
      return false;
    }

    Map<String, Object> model = new HashMap<>();

    Pattern pattern = Pattern.compile("node_id=(.*?)[,]");
    Matcher m = pattern.matcher(principal.getName());

    if (m.find()) {
      User user = getUser(m.group(1));
      user.updateUser(userUpdate);
      userService.saveUser(user);
      model.put("handle", user.getHandle());
      model.put("profilePicture", user.getProfilePicture());
      model.put("location", user.getLocation());
      model.put("bio", user.getBio());
    }

    return true;
  }

}
