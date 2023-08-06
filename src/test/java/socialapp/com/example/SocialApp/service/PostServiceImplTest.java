package socialapp.com.example.SocialApp.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import socialapp.com.example.SocialApp.entity.Comment;
import socialapp.com.example.SocialApp.entity.Post;
import socialapp.com.example.SocialApp.entity.SocialMediaUser;
import socialapp.com.example.SocialApp.repository.CommentRepository;
import socialapp.com.example.SocialApp.repository.PostRepository;
import socialapp.com.example.SocialApp.repository.SocialMediaUserRepo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class PostServiceImplTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostServiceImpl postService;

    @Mock
    SocialMediaUserRepo userRepository;

    @Mock
    CommentRepository commentRepository;

    @Mock
    ModelMapper mapper;


    @BeforeEach

    void up(){
       SocialMediaUser user = new SocialMediaUser();
       // user.setFirstName("Jane");
      //  user.setLastName("James");
        user.setEmail("jane@email.com");
        user.setUserName("jj");

        userRepository.save(user);


    }

    @Test
    void savedUser(){
        assertThat(userRepository).isNotNull();
       SocialMediaUser user = new SocialMediaUser();
        user.setUserName("Jane");
        log.info("Saved username -> {}",user.getUserName());
    }


    @Test
    void savePost() {
        Post post = new Post();
        post.setId(1L);
        post.setPostUser("Jane");
        post.setContent("Testing post");

        postRepository.save(post);

        assertThat(postRepository).isNotNull();
        log.info("Post content -> {}",post.getContent());
    }

    @Test
    void findAllPosts() {
        Post post = new Post();
        post.setId(1L);
        post.setPostUser("Jane");
        post.setContent("Testing post");

        postRepository.save(post);

        assertThat(postRepository).isNotNull();
        log.info("All posts ->{}",post.getId());
    }

    @Test
    void findById() {
        Post post = new Post();
        post.setId(1L);
        post.setPostUser("Jane");
        post.setContent("Testing post");

        postRepository.save(post);

        assertThat(postRepository).isNotNull();
        log.info("All posts ->{}",post.getId());
    }

    @Test
    void deletePostById() {
        Post post = new Post();
        post.setId(1L);
        post.setPostUser("Jane");
        post.setContent("Testing post");

        postRepository.save(post);
        postRepository.deleteById(1L);
        //assertThat(postRepository).isNull();
        //log.info();
    }

    @Test
    void addCommentToPost() {
        Post post = new Post();
        post.setId(1L);
        post.setPostUser("Jane");
        post.setContent("Testing post");

        postRepository.save(post);
        Comment comment = new Comment();
        comment.setContent("Good");
      commentRepository.save(comment);
      assertThat(commentRepository).isNotNull();

    }


}