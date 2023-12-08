package com.project.team.Board.Answer;

import com.project.team.Board.Post.Post;
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


    public Answer createAnswer(Post post, String content, SiteUser user){
        Answer answer = new Answer();
        answer.setContent(content);
        answer.setPost(post);
        answer.setUser(user);
        answer.setCreateDate(LocalDateTime.now());
        return this.answerRepository.save(answer);
    }

    public Answer getAnswer(Integer id) {
        return this.answerRepository.findById(id).get();
    }

    public void modifyAnswer(Answer answer, String content) {
        answer.setContent(content);
        answer.setModifyDate(LocalDateTime.now());
        this.answerRepository.save(answer);
    }

    public void deleteAnswer(Answer answer) {
        this.answerRepository.delete(answer);
    }
}
