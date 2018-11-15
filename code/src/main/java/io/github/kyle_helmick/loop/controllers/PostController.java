package io.github.kyle_helmick.loop.controllers;

import io.github.kyle_helmick.loop.models.Post;
import io.github.kyle_helmick.loop.models.User;
import io.github.kyle_helmick.loop.services.PostService;
import io.github.kyle_helmick.loop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
public class PostController {

  private final PostService postService;
  private final UserService userService;

  @Autowired
  public PostController(PostService postService, UserService userService) {
    this.postService = postService;
    this.userService = userService;
  }

  @GetMapping(value = "/feed")
  public @ResponseBody List<Map<String, Object>> getFeed(Principal principal) {
    User user = userService.getUser(principal);
    List<String> followingIds = user.getFollowing();
    followingIds.add(user.getId());
    Query query = new Query();
    query.addCriteria(Criteria.where("ownerId").in(followingIds));
    query.with(new Sort(Sort.Direction.DESC, "dateCreated"));
    return mapPostList(postService.findPosts(query));
  }

  @GetMapping(value = "/{postId}")
  public @ResponseBody Post getPost(@PathVariable String postId) {
    return postService.getPost(postId);
  }

  @PostMapping(value = "/new")
  public @ResponseBody Map<String, Object> makePost(@RequestBody Map<String, Object> postContent, Principal principal) {
    String postBody = (String) postContent.get("post");
    User user = userService.getUser(principal);
    postService.makeNewPost(user.getId(), postBody, "");
    return Collections.emptyMap();
  }

  private Map<String, Object> mapPost(Post post) {
    Map<String, Object> map = new HashMap<>();
    map.put("user", userService.getUser(post.getOwnerId()).getHandle());
    map.put("content", post.getContent());
    // TODO: this will look a lot like the user field ... map.put("group", )
    map.put("postTime", post.getDateCreated());
    return map;
  }

  private List<Map<String, Object>> mapPostList(List<Post> posts) {
    List<Map<String, Object>> mappedPosts = new ArrayList<>();
    for (Post post : posts) {
      mappedPosts.add(mapPost(post));
    }
    return mappedPosts;
  }

}
