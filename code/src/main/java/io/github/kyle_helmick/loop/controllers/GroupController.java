package io.github.kyle_helmick.loop.controllers;

import io.github.kyle_helmick.loop.models.Group;
import io.github.kyle_helmick.loop.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GroupController {

  private final GroupService groupService;

  @Autowired
  public GroupController(GroupService groupService) {
    this.groupService = groupService;
  }

  @GetMapping(value = "group/{groupId}")
  public @ResponseBody Group getGroup(@PathVariable String groupId) {
    return groupService.getGroup(groupId);
  }
}
