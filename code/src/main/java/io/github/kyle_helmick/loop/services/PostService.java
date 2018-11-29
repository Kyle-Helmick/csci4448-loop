package io.github.kyle_helmick.loop.services;

import io.github.kyle_helmick.loop.models.Post;
import io.github.kyle_helmick.loop.models.User;
import io.github.kyle_helmick.loop.respositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PostService {

  private final PostRepository repository;
  private final MongoOperations mongoOperations;
  private final UserService userService;

  /**
   * This class provides a way to take action on the post collection in a friendlier way.
   * @param repository is the repository instance for Posts
   * @param mongoOperations is a class that helps create queries
   * @param userService is the service that helps take action on the user collection
   */
  @Autowired
  public PostService(PostRepository repository, MongoOperations mongoOperations,
                     UserService userService) {
    this.repository = repository;
    this.mongoOperations = mongoOperations;
    this.userService = userService;
  }

  /**
   * This method will save a new post in the database.
   * @param userId is the userId (at the core the nodeId from github)
   * @param content is the ascii content that the user wishes to post
   */
  public void makeNewPost(String userId, String content) {
    repository.save(new Post.Builder()
                      .withOwnerId(userId)
                      .withContent(content)
                      .build());
  }

  public Post getPost(String postId) {
    return repository.findById(postId).orElse(null);
  }

  public List<Post> findPosts(Query query) {
    return mongoOperations.find(query, Post.class);
  }

  public void deletePost(String postId) {
    repository.deleteById(postId);
  }

  /**
   * This method will map a single post to it's JSON representation.
   * @param post the post to be converted
   * @return a converted post
   */
  public Map<String, Object> mapPost(Post post) {
    User user = userService.getUser(post.getOwnerId());
    Map<String, Object> mappedPost = new HashMap<>();
    mappedPost.put("id", post.getId());
    mappedPost.put("handle", user.getHandle());
    mappedPost.put("userId", post.getOwnerId());
    mappedPost.put("date", post.getDateModified());
    mappedPost.put("content", post.getContent());
    return mappedPost;
  }

  /**
   * This function will map a list of posts to JSON representations.
   * @param posts the list of posts to be converted
   * @return a list of converted posts
   */
  public List<Map<String, Object>> mapPosts(List<Post> posts) {
    List<Map<String, Object>> mappedPosts = new ArrayList<>();
    for (Post post : posts) {
      mappedPosts.add(mapPost(post));
    }
    return mappedPosts;
  }

}
