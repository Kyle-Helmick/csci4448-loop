package io.github.kyle_helmick.loop.respositories;

import io.github.kyle_helmick.loop.models.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends MongoRepository<Post, String> { }
