package io.github.kyle_helmick.loop.services;

import io.github.kyle_helmick.loop.models.User;
import io.github.kyle_helmick.loop.respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

  private final UserRepository repository;
  private final MongoOperations mongoOperations;

  @Autowired
  public UserService(UserRepository repository, MongoOperations mongoOperations) {
    this.repository = repository;
    this.mongoOperations = mongoOperations;
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
      saveUser(new User.Builder()
                .withId(nodeId)
                .withHandle(handle)
                .atLocation(location)
                .withProfilePicture(profilePicture)
                .withBio(bio).build());
    }
  }

  public User getUser(String nodeId) {
    return repository.findById(nodeId).orElse(null);
  }

  public User getUser(Principal principal) {
    return getUser((String) mapUserFromPrincipal(principal).get("nodeId"));
  }

  public void saveUser(User user) {
    repository.save(user);
  }

  /**
   * This method extracts information from the principal and puts it into a String Object Map.
   * @param principal a principal that contains the users' information
   * @return a String, Object Map representation of the user's principal object
   */
  public Map<String, Object> mapUserFromPrincipal(Principal principal) {

    Map<String, Object> userMap = new HashMap<>();

    Pattern pattern = Pattern.compile("node_id=(.*?)[,]");
    Matcher m = pattern.matcher(principal.getName());

    if (m.find()) {
      User user = this.getUser(m.group(1));
      return mapUser(user);
    } else {
      return userMap;
    }

  }

  public List<User> findUsers(Query query) {
    return mongoOperations.find(query, User.class);
  }


  /**
   * This method will create a Map of String to Object version of the User.
   * @param user the user object to map
   * @return a map of the user object
   */
  public Map<String, Object> mapUser(User user) {
    Map<String, Object> userMap = new HashMap<>();
    userMap.put("nodeId", user.getId());
    userMap.put("handle", user.getHandle());
    userMap.put("profilePicture", user.getProfilePicture());
    userMap.put("location", user.getLocation());
    userMap.put("bio", user.getBio());
    userMap.put("followers", user.getFollowers().size());
    userMap.put("following", user.getFollowing().size());
    userMap.put("posts", user.getPostIds().size());
    return userMap;
  }

  /**
   * This function changes users into map representations.
   * @param users is a list of users to map
   * @return a list of mapped users
   */
  public List<Map<String, Object>> mapUsers(List<User> users) {
    List<Map<String, Object>> mappedUsers = new ArrayList<>();
    for (User user : users) {
      mappedUsers.add(mapUser(user));
    }
    return mappedUsers;
  }
}
