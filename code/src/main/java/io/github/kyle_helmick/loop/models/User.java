package io.github.kyle_helmick.loop.models;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document(collection = "users")
public class User extends Entity {

  //  @Id
  //  private final String id;
  //
  //  @Indexed
  //  @CreatedDate
  //  private final Date dateCreated;
  //
  //  @LastModifiedDate
  //  private Date dateModified;
  //
  //  private String bio;
  //  private String handle;
  //  private ArrayList<String> postIds;

  private String location;
  private String profilePicture;
  private ArrayList<String> followers;
  private ArrayList<String> following;

  /**
   * This method is the default constructor for a User.
   * @param id is the node_id from the github oauth map
   * @param handle is the desired handle for the user object
   * @param location is the location for the user object
   * @param profilePicture is the url for the profilePicture for the user object
   * @param bio is the desired bio for the user object
   */
  public User(String id, String handle, String location, String profilePicture, String bio) {
    super(id, handle, bio);

    this.location = location;
    this.profilePicture = profilePicture;
    this.following = new ArrayList<>();
    this.followers = new ArrayList<>();

  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getProfilePicture() {
    return profilePicture;
  }

  public void setProfilePicture(String profilePicture) {
    this.profilePicture = profilePicture;
  }

  public void addFollowerId(String id) {
    this.followers.add(id);
  }

  public void removeFollowerId(String id) {
    this.followers.remove(id);
  }

  public ArrayList<String> getFollowing() {
    return this.following;
  }

  public void followId(String id) {
    this.following.add(id);
  }

  public void unfollowId(String id) {
    this.following.remove(id);
  }

  public ArrayList<String> getFollowers() {
    return this.followers;
  }

  /**
   * This method takes a user update obj and updates the users' attributes.
   * @param update is the updates (or same values) to update a user with
   */
  public void updateUser(UserUpdate update) {
    this.setHandle(update.getHandle());
    this.setLocation(update.getLocation());
    this.setProfilePicture(update.getProfilePicture());
    this.setBio(update.getBio());
  }
}
