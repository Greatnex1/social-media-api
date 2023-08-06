package socialapp.com.example.SocialApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import socialapp.com.example.SocialApp.dtos.CommentDto;
import socialapp.com.example.SocialApp.dtos.PostDto;
import socialapp.com.example.SocialApp.entity.Post;
import socialapp.com.example.SocialApp.service.PostService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/v1")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/post")
    public ResponseEntity<?> createPost(@RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.savePost(postDto), HttpStatus.CREATED);
    }

    @PatchMapping("/post/update")
    public ResponseEntity<Post> editPost(@Valid PostDto postDto){

        return ResponseEntity.ok(postService.updatePost(postDto));
    }
    @GetMapping("/post/{post_id}")
    public ResponseEntity<?> findPostById(@PathVariable @NotBlank(message = "require valid parameter") Long post_id) {
        try {
            return ResponseEntity.ok(postService.findById(post_id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @DeleteMapping("/post/delete/{post_id}")
    public ResponseEntity<?> deletePost(@PathVariable("post_id")Long post_id){
        postService.deletePostById(post_id);
        return new ResponseEntity<>("Successfully delete post", HttpStatus.OK);
    }

    @PutMapping("/posts/comment")
    public ResponseEntity<?> addCommentToPost(@RequestBody @Valid CommentDto commentDto){
        return ResponseEntity.ok(postService.addCommentToPost(commentDto));
    }
    @PutMapping("/like-post")
    public ResponseEntity<?> likePost(@RequestParam Long post_id, @RequestParam String userName){
        return ResponseEntity.ok(postService.likePost(post_id, userName));
    }
    @PutMapping("/unlike-post")
    public ResponseEntity<?> unlikePost(@RequestParam Long post_id, @RequestParam String userName) {
        return ResponseEntity.ok(postService.unLikePost(post_id, userName));

    }
    @DeleteMapping("/posts/delete")
    public ResponseEntity<?> deleteAllPost(){
        postService.deleteAllPost();
        return ResponseEntity.noContent().build();
    }
}
