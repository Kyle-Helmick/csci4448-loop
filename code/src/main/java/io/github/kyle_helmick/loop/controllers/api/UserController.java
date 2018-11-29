package io.github.kyle_helmick.loop.controllers.api;

import io.github.kyle_helmick.loop.models.Message;
import io.github.kyle_helmick.loop.models.User;
import io.github.kyle_helmick.loop.models.UserUpdate;
import io.github.kyle_helmick.loop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/user")
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   * This method will return a message with either a fail or success and either
   *    a fail message or the user.
   * @param userId is the string of the userId
   * @return a message that tells the state of the request and gives appropriate data.
   */
  @GetMapping(value = "/{userId}")
  public @ResponseBody Message getUser(@PathVariable String userId) {
    User user = userService.getUser(userId);
    return user == null ? new Message.Builder().withFail().withData("User not found.").build() :
      new Message.Builder().withSuccess().withData(user).build();
  }

  /**
   * This method allows a user to follow or unfollow another user. (Pseudo State Design Pattern)
   * @param principal is the principal for the currently logged in user
   * @param userId is the id of the user to follow or unfollow
   * @return a message that tells if the operation was successful with associated data
   */
  @GetMapping(value = "/follow/{userId}")
  public @ResponseBody Message followUser(Principal principal, @PathVariable String userId) {
    User loggedInUser = userService.getUser(principal);
    User pageUser = userService.getUser(userId);

    String message;

    if (loggedInUser.getFollowing().contains(pageUser.getId())) {
      loggedInUser.unfollowId(pageUser.getId());
      pageUser.removeFollowerId(loggedInUser.getId());
      message = "Successfully unfollowed user";
    } else {
      loggedInUser.followId(pageUser.getId());
      pageUser.addFollowerId(loggedInUser.getId());
      message = "Successfully followed user";
    }

    userService.saveUser(loggedInUser);
    userService.saveUser(pageUser);

    return new Message.Builder().withSuccess().withData(message).build();
  }

  /**
   * The method updates a user with the information provided from the client.
   * @param principal a principal that contains the users' information
   * @param userUpdate is the possibly updated information from the user
   * @return a message that contains the state of the operation and an associated message.
   */
  @PostMapping("/update")
  public Message update(Principal principal,
                        @Valid @ModelAttribute UserUpdate userUpdate,
                        BindingResult result) {

    if (result.hasErrors()) {
      System.out.println(result.getAllErrors().toString());
      return new Message.Builder().withError().withData(result.getAllErrors().toString()).build();
    }

    Pattern pattern = Pattern.compile("node_id=(.*?)[,]");
    Matcher m = pattern.matcher(principal.getName());

    if (m.find()) {
      User user = userService.getUser(m.group(1));
      user.updateUser(userUpdate);
      userService.saveUser(user);
    } else {
      return new Message.Builder()
                        .withFail().withData("User wasn't found. Malformed principal?").build();
    }

    return new Message.Builder().withSuccess().withData("User updated successfully!").build();
  }

}
