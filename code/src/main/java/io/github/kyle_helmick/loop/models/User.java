package io.github.kyle_helmick.loop.models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;

@Document(collection = "users")
public class User {

  @Id
  private final String id;

  @Indexed
  @CreatedDate
  private final Date dateCreated;

  @LastModifiedDate
  private Date dateModified;

  private String bio;
  private String handle;
  private ArrayList<String> postIds;

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
    this.id = id;
    this.handle = handle;
    this.dateCreated = new Date();
    this.dateModified = this.dateCreated;
    this.postIds = new ArrayList<>();
    this.bio = bio;
    this.location = location;
    this.profilePicture = profilePicture;
    this.following = new ArrayList<>();
    this.followers = new ArrayList<>();
  }


  public String getId() {
    return id;
  }

  public Date getDateCreated() {
    return dateCreated;
  }

  public Date getDateModified() {
    return dateModified;
  }

  public void setDateModified(Date dateModified) {
    this.dateModified = dateModified;
  }

  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public String getHandle() {
    return handle;
  }

  public void setHandle(String handle) {
    this.handle = handle;
  }

  public ArrayList<String> getPostIds() {
    return postIds;
  }

  public void setPostIds(ArrayList<String> postIds) {
    this.postIds = postIds;
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

  public ArrayList<String> getFollowers() {
    return followers;
  }

  public ArrayList<String> getFollowing() {
    return following;
  }

  public void addFollowerId(String id) {
    this.followers.add(id);
  }

  public void removeFollowerId(String id) {
    this.followers.remove(id);
  }

  public void followId(String id) {
    this.following.add(id);
  }

  public void unfollowId(String id) {
    this.following.remove(id);
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

  public static class Builder {

    private String id = "";
    private String bio = "";
    private String handle = "";
    private String location = "";
    private String profilePicture = "";

    public Builder() { }

    public Builder withId(String id) {
      this.id = id;
      return this;
    }

    public Builder withBio(String bio) {
      this.bio = bio;
      return this;
    }

    public Builder withHandle(String handle) {
      this.handle = handle;
      return this;
    }

    public Builder atLocation(String location) {
      this.location = location;
      return this;
    }

    public Builder withProfilePicture(String profilePicture) {
      this.profilePicture = profilePicture;
      return this;
    }

    public User build() {
      return new User(this.id, this.handle, this.location, this.profilePicture, this.bio);
    }
  }
}
