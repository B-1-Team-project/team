package com.project.team.Board;

import com.project.team.DataNotFoundException;
import com.project.team.User.SiteUser;
import com.project.team.User.SiteUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final SiteUserService siteUserService;

    public List<Post> postList(){
        return this.postRepository.findAll();
    }

    public void createPost(String title, String content, String user){
        Post post = new Post();
        SiteUser author = this.siteUserService.getUser(user);
        post.setTitle(title);
        post.setContent(content);
        post.setUser(author);
        post.setCreateDate(LocalDateTime.now());
        this.postRepository.save(post);
    }

    public Post getPost(Integer id){
        Optional<Post> post = this.postRepository.findById(id);
        if(post.isPresent()){
            return post.get();
        } else {
            throw new DataNotFoundException("post not found");
        }
    }

    public void modify(Post post,String title, String content){
        post.setTitle(title);
        post.setContent(content);
        this.postRepository.save(post);
    }

    public void delete(Post post){
        this.postRepository.delete(post);
    }

    public Page<Post> getList(int page){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page,10,Sort.by(sorts));
        return this.postRepository.findAll(pageable);
    }
}