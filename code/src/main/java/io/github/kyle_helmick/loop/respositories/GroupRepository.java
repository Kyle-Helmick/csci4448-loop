package io.github.kyle_helmick.loop.respositories;

import io.github.kyle_helmick.loop.models.Group;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends MongoRepository<Group, String> { }
