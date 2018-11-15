package io.github.kyle_helmick.loop.services;

import io.github.kyle_helmick.loop.models.Post;
import io.github.kyle_helmick.loop.models.User;
import io.github.kyle_helmick.loop.respositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

  private final PostRepository repository;
  private final MongoOperations mongoOperations;

  @Autowired
  public PostService(PostRepository repository, MongoOperations mongoOperations) {
    this.repository = repository;
    this.mongoOperations = mongoOperations;
  }

  public void makeNewPost(String userId, String content, String groupId) {
    repository.save(new Post(content, userId, groupId));
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

}
