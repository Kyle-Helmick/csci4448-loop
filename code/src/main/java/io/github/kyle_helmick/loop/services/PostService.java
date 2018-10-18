package io.github.kyle_helmick.loop.services;

import io.github.kyle_helmick.loop.models.Post;
import io.github.kyle_helmick.loop.respositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

  private final PostRepository repository;

  @Autowired
  public PostService(PostRepository repository) {
    this.repository = repository;
  }

  public void makeNewPost(String userId, String content, String groupId) {
    repository.save(new Post(userId, content, groupId));
  }

  public Post getPost(String postId) {
    return repository.findById(postId).orElse(null);
  }

  public void deletePost(String postId) {
    repository.deleteById(postId);
  }

}
