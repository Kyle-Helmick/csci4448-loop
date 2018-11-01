package io.github.kyle_helmick.loop.models;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.UUID;

@Document(collection = "groups")
public class Group extends Entity {

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

  private ArrayList<String> memberIds;
  private String ownerId;

  /**
   * This method is the default constructor to make a Group object.
   * @param handle is the desired handle for the group
   * @param ownerId is the id attribute of the User object that is the owner/creator of the group
   */
  public Group(String handle, String ownerId) {
    super(UUID.randomUUID().toString(), handle);

    this.memberIds = new ArrayList<>();
    this.ownerId = ownerId;
  }

  public ArrayList<String> getMemberIds() {
    return memberIds;
  }

  public void addMemberId(String id) {
    this.memberIds.add(id);
  }

  public void deleteMemberId(String id) {
    this.memberIds.remove(id);
  }

  public String getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(String ownerId) {
    this.ownerId = ownerId;
  }
}
