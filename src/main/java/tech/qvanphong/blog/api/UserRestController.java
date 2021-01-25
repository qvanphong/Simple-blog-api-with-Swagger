package tech.qvanphong.blog.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tech.qvanphong.blog.constant.ResponseMessage;
import tech.qvanphong.blog.exception.EntityNotFoundException;
import tech.qvanphong.blog.exception.MissingParamException;
import tech.qvanphong.blog.model.User;
import tech.qvanphong.blog.repository.UserRepository;

import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserRestController {

    private final UserRepository repository;

    @Autowired
    public UserRestController(UserRepository repository) {
        this.repository = repository;
    }

    @GetMapping()
    public Page<User> fetchAllUsers(Pageable pageable) {
        Page<User> users = repository.findAll(pageable);
        long totalElements = users.getTotalElements();
        if (totalElements > 0) return users;
        throw new EntityNotFoundException();
    }

    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED, reason = ResponseMessage.CREATE_SUCCESS)
    public void newUser(@RequestBody User user){
        repository.save(user);
    }

    @GetMapping("/{userId}")
    public User fetchUser(@PathVariable("userId") Integer userId) {
        Optional<User> optionalFindById = repository.findById(userId);
        return optionalFindById.orElseThrow(EntityNotFoundException::new);
    }

    @PutMapping("/{userId}")
    @ResponseStatus(value = HttpStatus.OK, reason = ResponseMessage.UPDATE_SUCCESS)
    public void updateUser(@RequestBody User user, @PathVariable("userId") Integer userId) {
        if (userId == null) {
            throw new MissingParamException("userId");
        }
        repository.findById(userId).map(user1 -> {
            user1.setUsername(user.getUsername());
            return repository.save(user1);
        }).orElseGet(() -> {
            user.setUserId(userId);
            return repository.save(user);
        });
    }

    @DeleteMapping("/{userId}")
    @ResponseStatus(value = HttpStatus.OK, reason = ResponseMessage.DELETE_SUCCESS)
    public void deleteUser(@PathVariable("userId") Integer userId){
        if (userId == null) {
            throw new MissingParamException("userId");
        }
        repository.deleteById(userId);
    }
}
