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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
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

  /**
   * This method will return a message object that contains posts for the user.
   * @param principal a principal that contains the users' information
   * @return a success message with posts that are found for the users' feed.
   *     This list may be empty.
   */
  @GetMapping(value = "/feed")
  public @ResponseBody Message getFeed(Principal principal) {
    User user = userService.getUser(principal);

    List<String> followingIds = user.getFollowing();
    followingIds.add(user.getId());
    List<Post> posts = getPostsByIds(followingIds);
    List<Map<String, Object>> mappedPosts = mapPostList(posts);

    return new Message.Builder().withSuccess().withData(mappedPosts).build();
  }

  /**
   * This method will return a message with the post and state of the lookup.
   * @param postId is the string postId to search the database by.
   * @return A message that is either success or fail with either the post or error message.
   */
  @GetMapping(value = "/post/{postId}")
  public @ResponseBody Message getPost(@PathVariable String postId) {
    Post post = postService.getPost(postId);
    return post == null ? new Message.Builder().withFail().withData("Post not found.").build() :
      new Message.Builder().withSuccess().withData(mapPost(post)).build();
  }

  /**
   * This method will fetch posts for a specific user.
   * TODO: This needs to be paginated
   * @param userId is the id of the user to get posts from
   * @return a list of posts from the user
   */
  @GetMapping(value = "/user/{userId}")
  public @ResponseBody Message getPosts(@PathVariable String userId) {
    List<String> id = new ArrayList<>();
    id.add(userId);
    List<Post> posts = getPostsByIds(id);
    List<Map<String, Object>> mappedPosts = mapPostList(posts);
    return new Message.Builder().withSuccess().withData(mappedPosts).build();
  }

  /**
   * This method allows the creation of a new post.
   * @param postContent The JSON object that contains the post content.
   * @param principal a principal that contains the users' information
   * @return a message that shows success of creation.
   */
  @PostMapping(value = "/new")
  public @ResponseBody Message makePost(@RequestBody Map<String, Object> postContent,
                                                     Principal principal) {
    String postBody = (String) postContent.get("post");
    User user = userService.getUser(principal);
    postService.makeNewPost(user.getId(), postBody);
    return new Message.Builder().withSuccess().withData("Post made!").build();
  }

  private Map<String, Object> mapPost(Post post) {
    Map<String, Object> map = new HashMap<>();
    map.put("user", userService.getUser(post.getOwnerId()).getHandle());
    map.put("content", post.getContent());
    map.put("group", post);
    map.put("postTime", post.getDateModified());
    map.put("userId", post.getOwnerId());
    return map;
  }

  private List<Map<String, Object>> mapPostList(List<Post> posts) {
    List<Map<String, Object>> mappedPosts = new ArrayList<>();
    for (Post post : posts) {
      if (post != null) {
        mappedPosts.add(mapPost(post));
      }
    }
    return mappedPosts;
  }

  private List<Post> getPostsByIds(List<String> ids) {
    Query query = new Query();
    query.addCriteria(Criteria.where("ownerId").in(ids));
    query.with(new Sort(Sort.Direction.DESC, "dateCreated"));
    return postService.findPosts(query);
  }

}
