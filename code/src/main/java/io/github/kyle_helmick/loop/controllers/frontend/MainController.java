package io.github.kyle_helmick.loop.controllers.frontend;

import io.github.kyle_helmick.loop.models.User;
import io.github.kyle_helmick.loop.services.PostService;
import io.github.kyle_helmick.loop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MainController {

  private final UserService userService;
  private final PostService postService;

  @Autowired
  public MainController(UserService userService, PostService postService) {
    this.userService = userService;
    this.postService = postService;
  }

  /**
   * This method will return the name of the mustache file to render for the login/default page.
   * @return a model and view indicating the page to render with supplied information
   */
  @RequestMapping("/")
  public ModelAndView index() {

    Map<String, Object> view = new HashMap<>();
    view.put("pageTitle", "Login");

    return new ModelAndView("index", view);
  }

  /**
   * This method will return the name of the mustache file to render for the profile page.
   * @param principal a principal that contains the users' information
   * @return a model and view indicating the page to render with supplied information
   */
  @RequestMapping("/profile")
  public ModelAndView profile(Principal principal) {

    Map<String, Object> view = userService.mapUserFromPrincipal(principal);

    view.put("pageTitle", "profile");

    return new ModelAndView("profile", view);
  }

  /**
   * This method will return the name of the mustache file to render for the feed page.
   * @param principal a principal that contains the users' information
   * @return a model and view indicating the page to render with supplied information
   */
  @RequestMapping("/feed")
  public ModelAndView feed(Principal principal) {

    Map<String, Object> view = userService.mapUserFromPrincipal(principal);

    view.put("pageTitle", "Feed");

    return new ModelAndView("feed", view);
  }

  /**
   * This method will fetch a user for display.
   * @param principal is the currently logged in users' principal.
   * @param userId is the id of the user to show
   * @return a ModelAndView that contains the details for a user and the user page to show.
   */
  @GetMapping(value = "/user/{userId}")
  public @ResponseBody ModelAndView getUser(Principal principal, @PathVariable String userId) {

    Map<String, Object> view = new HashMap<>();
    User user = userService.getUser(userId);

    if (user == null) {
      view.put("error", "User not found.");
      return new ModelAndView("error", view);
    }

    User loggedInUser = userService.getUser(principal);

    view = userService.mapUser(loggedInUser);
    Map<String, Object> pageUser = userService.mapUser(user);
    view.put("pageUser", pageUser);

    if (loggedInUser.getFollowing().contains(user.getId())) {
      view.put("alreadyFollowing", true);
    } else {
      view.put("alreadyFollowing", false);
    }

    if (loggedInUser.getId().equals(user.getId())) {
      view.put("isSelf", true);
    } else {
      view.put("isSelf", false);
    }

    view.put("pageTitle", "@" + user.getHandle());

    return new ModelAndView("user", view);
  }

  /**
   * This method provides a page for users to search with.
   * @param principal is the principal of the searching user.
   * @return a model and view with the appropriate data included
   */
  @GetMapping(value = "/search")
  public @ResponseBody ModelAndView search(Principal principal) {
    User loggedInUser = userService.getUser(principal);
    Map<String, Object> view = userService.mapUser(loggedInUser);
    view.put("pageTitle", "Search");
    return new ModelAndView("search", view);
  }

}