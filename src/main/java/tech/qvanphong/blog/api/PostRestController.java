package tech.qvanphong.blog.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import tech.qvanphong.blog.constant.ResponseMessage;
import tech.qvanphong.blog.exception.EntityNotFoundException;
import tech.qvanphong.blog.exception.MissingParamException;
import tech.qvanphong.blog.model.Post;
import tech.qvanphong.blog.repository.PostRepository;

import java.util.Date;


@RestController
@RequestMapping("posts")
public class PostRestController {
    private final PostRepository repository;


    @Autowired
    public PostRestController(PostRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Page<Post> fetchAllPost(Pageable pageable){
        Page<Post> posts = repository.findAll(pageable);
        if (posts.getTotalElements() > 0) return posts;
        throw new EntityNotFoundException();
    }

    @GetMapping("/{postId}")
    public Post fetchPost(@PathVariable("postId") Integer postId){
        return repository.findById(postId).orElseThrow(EntityNotFoundException::new);
    }

    @PostMapping("")
    @ResponseStatus(value = HttpStatus.CREATED, reason = ResponseMessage.CREATE_SUCCESS)
    public void newPost(@RequestBody Post post) {
        post.setDate(new Date().toString());
        repository.save(post);
    }

    @PutMapping("/{postId}")
    @ResponseStatus(value = HttpStatus.OK, reason = ResponseMessage.UPDATE_SUCCESS)
    public void updatePost(@RequestBody Post post, @PathVariable("postId") Integer postId) {
        if (postId == null) {
            throw new MissingParamException("postId");
        }
        repository.findById(postId).map(post1 -> {
            post1.setUsername(post.getUsername());
            return repository.save(post1);
        }).orElseGet(() -> {
            post.setPostId(postId);
            return repository.save(post);
        });
    }

    @DeleteMapping("/{postId}")
    @ResponseStatus(value = HttpStatus.OK, reason = ResponseMessage.DELETE_SUCCESS)
    public void deletePost(@PathVariable("postId") Integer postId){
        if (postId == null) {
            throw new MissingParamException("postId");
        }
        repository.deleteById(postId);
    }

}
