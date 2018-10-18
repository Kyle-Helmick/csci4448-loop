package io.github.kyle_helmick.loop.models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.UUID;

@Document(collection = "posts")
public class Post {

  @Id
  private final String id;

  @Indexed
  @CreatedDate
  private final Date dateCreated;

  @LastModifiedDate
  private Date dateModified;

  private String content;
  private final String ownerId;
  private final String groupId;

  public Post(String content, String ownerId, String groupId) {
    this.id = UUID.randomUUID().toString();
    this.content = content;
    this.ownerId = ownerId;
    this.dateCreated = new Date();
    this.dateModified = this.dateCreated;
    this.groupId = groupId;
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

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getOwnerId() {
    return ownerId;
  }

  public String getGroupId() {
    return groupId;
  }
}
