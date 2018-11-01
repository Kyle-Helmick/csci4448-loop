package io.github.kyle_helmick.loop.models;

import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserUpdate {

  @NotBlank
  @Size(min = 4, max = 24, message = "Handle must be between 4 and 24 characters")
  private String handle;

  @NotBlank
  @URL
  private String profilePicture;

  private String location;

  @Size(max = 500, message = "Bio can be up to 500 characters")
  private String bio;

  /**
   * This method is the constructor for a UserUpdate obj.
   * @param handle is the possibly new handle
   * @param profilePicture is the possibly new profilePicture
   * @param location is the possible new location
   * @param bio is the possibly new bio
   */
  public UserUpdate(@NotBlank @Size(min = 4, max = 24, message = "Handle must be between 4 and 24 characters") String handle,
                    @NotBlank @URL String profilePicture,
                    String location,
                    @Size(max = 500, message = "Bio can be up to 500 characters") String bio) {
    this.handle = handle;
    this.profilePicture = profilePicture;
    this.location = location;
    this.bio = bio;
  }

  public String getHandle() {
    return handle;
  }

  public void setHandle(String handle) {
    this.handle = handle;
  }

  public String getProfilePicture() {
    return profilePicture;
  }

  public void setProfilePicture(String profilePicture) {
    this.profilePicture = profilePicture;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }
}
