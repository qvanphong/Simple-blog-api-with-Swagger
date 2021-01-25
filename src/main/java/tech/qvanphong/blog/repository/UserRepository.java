package tech.qvanphong.blog.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import tech.qvanphong.blog.model.User;

public interface UserRepository extends PagingAndSortingRepository<User, Integer> {
}
