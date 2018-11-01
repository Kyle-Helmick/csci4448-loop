package io.github.kyle_helmick.loop.controllers;

import io.github.kyle_helmick.loop.models.User;
import io.github.kyle_helmick.loop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.session.data.mongo.MongoSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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
  public String index(Model model) {
    return "index.html";
  }

  /**
   * Apples.
   * @param principal apples
   * @return apples
   */
  @RequestMapping("/profile")
  public ModelAndView profile(Principal principal) {

    Map<String, Object> model = new HashMap<>();

    Pattern pattern = Pattern.compile("node_id=(.*?)[,]");
    Matcher m = pattern.matcher(principal.getName());

    if (m.find()) {
      User user = userService.getUser(m.group(1));
      model.put("handle", user.getHandle());
      model.put("profilePicture", user.getProfilePicture());
      model.put("location", user.getLocation());
      model.put("bio", user.getBio());
    }

    return new ModelAndView("profile", model);
  }
}
