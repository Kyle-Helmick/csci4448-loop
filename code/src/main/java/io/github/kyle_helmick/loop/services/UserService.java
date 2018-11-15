package io.github.kyle_helmick.loop.services;

import io.github.kyle_helmick.loop.models.User;
import io.github.kyle_helmick.loop.respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

  private final UserRepository repository;

  @Autowired
  public UserService(UserRepository repository) {
    this.repository = repository;
  }

  /**
   * This method takes the map of attributes returned from a successful OAuth authentication.
   * @param map the map of attributes that make the Github OAuth object
   */
  public void registerNewUser(Map<String, Object> map) {
    String nodeId = map.get("node_id") == null ? "" : (String) map.get("node_id");
    if (!repository.findById(nodeId).isPresent()) {
      String handle = map.get("login") == null ? "" : (String) map.get("login");
      String location = map.get("location") == null ? "" : (String) map.get("location");
      String profilePicture = map.get("avatar_url") == null ? "" : (String) map.get("avatar_url");
      String bio = map.get("bio") == null ? "" : (String) map.get("bio");
      saveUser(new User(nodeId, handle, location, profilePicture, bio));
    }
  }

  public User getUser(String nodeId) {
    return repository.findById(nodeId).orElse(null);
  }

  public User getUser(Principal principal) {
    return getUser((String) mapUser(principal).get("nodeId"));
  }

  public void saveUser(User user) {
    repository.save(user);
  }

  public Map<String, Object> mapUser(Principal principal) {

    Map<String, Object> userMap = new HashMap<>();

    Pattern pattern = Pattern.compile("node_id=(.*?)[,]");
    Matcher m = pattern.matcher(principal.getName());

    if (m.find()) {
      User user = this.getUser(m.group(1));
      userMap.put("nodeId", user.getId());
      userMap.put("handle", user.getHandle());
      userMap.put("profilePicture", user.getProfilePicture());
      userMap.put("location", user.getLocation());
      userMap.put("bio", user.getBio());
      userMap.put("followers", user.getFollowers().size());
      userMap.put("following", user.getFollowing().size());
      userMap.put("posts", user.getPostIds().size());
    }

    return userMap;
  }
}
