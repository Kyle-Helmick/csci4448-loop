package io.github.kyle_helmick.loop.services;

import io.github.kyle_helmick.loop.models.Group;
import io.github.kyle_helmick.loop.respositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

  private final GroupRepository repository;

  @Autowired
  public GroupService(GroupRepository repository) {
    this.repository = repository;
  }

  public void registerGroup(String handle, String ownerId) {
    repository.save(new Group(handle, ownerId));
  }

  public Group getGroup(String groupId) {
    return repository.findById(groupId).orElse(null);
  }

  public void deleteGroup(String groupId) {
    repository.deleteById(groupId);
  }

}
