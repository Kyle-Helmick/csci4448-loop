package io.github.kyle_helmick.loop.models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.ArrayList;
import java.util.Date;

public class Entity {

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

  public Entity(String id, String handle) {
    this.id = id;
    this.handle = handle;
    this.dateCreated = new Date();
    this.dateModified = this.dateCreated;
    this.postIds = new ArrayList<>();
    this.bio = "";
  }

  public Entity(String id, String handle, String bio) {
    this(id, handle);

    this.bio = bio;
  }

  public String getId() {
    return id;
  }

  public String getHandle() {
    return handle;
  }

  public void setHandle(String handle) {
    this.handle = handle;
  }

  public Date getDateCreated() {
    return dateCreated;
  }

  public Date getDateModified() {
    return dateModified;
  }

  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public ArrayList<String> getPostIds() {
    return postIds;
  }

  public void addPostId(String postId) {
    this.postIds.add(postId);
  }

  public void deletePostId(String postId) {
    this.postIds.remove(postId);
  }
}
