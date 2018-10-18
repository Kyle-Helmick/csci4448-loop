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

  public void registerNewUser(Map<String, Object> map) {
    String nodeId = (String) map.getOrDefault("node_id", "");
    if (!repository.findById(nodeId).isPresent()) {
      String handle = (String) map.getOrDefault("login", "");
      String location = (String) map.getOrDefault("location", "");
      String profilePicture = (String) map.getOrDefault("avatar_url", "");
      String bio = (String) map.getOrDefault("bio", "");
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
