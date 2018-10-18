package io.github.kyle_helmick.loop.controllers;

import io.github.kyle_helmick.loop.models.Post;
import io.github.kyle_helmick.loop.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

  private final PostService postService;

  @Autowired
  public PostController(PostService postService) {
    this.postService = postService;
  }

  @GetMapping(value = "/post/{postId}")
  public @ResponseBody Post getPost(@PathVariable String postId) {
    return postService.getPost(postId);
  }
}
