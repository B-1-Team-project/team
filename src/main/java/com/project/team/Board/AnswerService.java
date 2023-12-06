package com.project.team.Board;

import com.project.team.DataNotFoundException;
import com.project.team.User.SiteUser;
import com.project.team.User.SiteUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final SiteUserService siteUserService;


    public void create(Post post, String content,SiteUser user){
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setPost(post);
        answer.setUser(user);
        answer.setCreateDate(LocalDateTime.now());
        this.answerRepository.save(answer);
    }

    public Answer getAnswer(Integer id){
        Optional<Answer> answer = this.answerRepository.findById(id);
        if(answer.isPresent()){
            return answer.get();
        } else {
            throw new DataNotFoundException("answer not found");
        }
    }

}
