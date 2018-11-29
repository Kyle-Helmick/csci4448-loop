package io.github.kyle_helmick.loop.controllers.api;

import io.github.kyle_helmick.loop.models.Message;
import io.github.kyle_helmick.loop.models.Post;
import io.github.kyle_helmick.loop.models.User;
import io.github.kyle_helmick.loop.services.PostService;
import io.github.kyle_helmick.loop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class SearchController {

  private final PostService postService;
  private final UserService userService;

  @Autowired
  SearchController(PostService postService, UserService userService) {
    this.postService = postService;
    this.userService = userService;
  }

  /**
   * This method lets users search all posts and users with a query.
   * @param query is the map of "query" to the queryString
   * @return a message with appropriate data
   */
  @PostMapping(value = "/api/search")
  public @ResponseBody Message search(@RequestBody Map<String, Object> query) {
    Query handleQuery = new Query();
    Query postQuery = new Query();

    String queryString = query.get("query").toString();
    queryString = queryString.replace("@", "");

    handleQuery.addCriteria(Criteria.where("handle").regex(queryString, "i"));
    postQuery.addCriteria(Criteria.where("content").regex(queryString, "i"));

    handleQuery.with(new Sort(Sort.Direction.DESC, "dateCreated"));
    postQuery.with(new Sort(Sort.Direction.DESC, "dateCreated"));

    List<User> users = userService.findUsers(handleQuery);
    List<Post> posts = postService.findPosts(postQuery);

    Map<String, Object> results = new HashMap<>();

    results.put("users", userService.mapUsers(users));
    results.put("posts", postService.mapPosts(posts));

    return new Message.Builder().withSuccess().withData(results).build();
  }

}
