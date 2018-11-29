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

  /**
   * This is the constructor to make a Post.
   * @param content is the content of the post
   * @param ownerId is the id of the User object that is the owner of the post.
   */
  public Post(String content, String ownerId) {
    this.id = UUID.randomUUID().toString();
    this.content = content;
    this.ownerId = ownerId;
    this.dateCreated = new Date();
    this.dateModified = this.dateCreated;
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

  public static class Builder {
    private String content;
    private String ownerId;

    public Builder() {}

    /**
     * This function sets the content for the Post and cleans it just in case.
     * @param content is the text for the content
     * @return the updated instance of the builder
     */
    public Builder withContent(String content) {
      content = content.replace("<", "&lt;");
      content = content.replace(">", "&gt;");
      this.content = content;
      return this;
    }

    public Builder withOwnerId(String ownerId) {
      this.ownerId = ownerId;
      return this;
    }

    public Post build() {
      return new Post(this.content, this.ownerId);
    }
  }
}
