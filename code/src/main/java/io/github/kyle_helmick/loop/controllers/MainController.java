package io.github.kyle_helmick.loop.controllers;

import io.github.kyle_helmick.loop.models.User;
import io.github.kyle_helmick.loop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class MainController {

  private final UserService userService;

  @Autowired
  public MainController(UserService userService) {
    this.userService = userService;
  }

  @RequestMapping("/")
  public ModelAndView index() {

    Map<String, Object> view = new HashMap<>();
    view.put("pageTitle", "Login");

    return new ModelAndView("index", view);
  }

  @RequestMapping("/profile")
  public ModelAndView profile(Principal principal) {

    Map<String, Object> view = mapUser(principal);

    view.put("pageTitle", "profile");

    return new ModelAndView("profile", view);
  }

  @RequestMapping("/feed")
  public ModelAndView feed(Principal principal) {

    Map<String, Object> view = mapUser(principal);

    view.put("pageTitle", "Feed");

    return new ModelAndView("feed", view);
  }

  private Map<String, Object> mapUser(Principal principal) {

    Map<String, Object> userView = new HashMap<>();

    Pattern pattern = Pattern.compile("node_id=(.*?)[,]");
    Matcher m = pattern.matcher(principal.getName());

    if (m.find()) {
      User user = userService.getUser(m.group(1));
      userView.put("handle", user.getHandle());
      userView.put("profilePicture", user.getProfilePicture());
      userView.put("location", user.getLocation());
      userView.put("bio", user.getBio());
      userView.put("followers", user.getFollowers().size());
      userView.put("following", user.getFollowing().size());
      userView.put("posts", user.getPostIds().size());
    }

    return userView;
  }

}