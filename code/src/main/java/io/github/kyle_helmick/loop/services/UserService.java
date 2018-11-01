package io.github.kyle_helmick.loop.services;

import io.github.kyle_helmick.loop.models.User;
import io.github.kyle_helmick.loop.respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

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

  public void saveUser(User user) {
    repository.save(user);
  }

}
