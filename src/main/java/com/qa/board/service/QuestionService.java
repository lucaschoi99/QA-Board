package com.qa.board.service;

import com.qa.board.domain.*;
import com.qa.board.exception.DataNotFoundException;
import com.qa.board.form.QuestionEdit;
import com.qa.board.repository.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final QuestionUserRepository questionUserRepository;
    private final QuestionCountRepository questionCountRepository;
    private final QuestionAlertRepository questionAlertRepository;
    private final UserRepository userRepository;
    private final EntityManager em;

    public Page<Question> findQuestions(int page, int pageSize, String keyword) {
        PageRequest pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Order.desc("createdDate")));
        return search(keyword, pageable);
    }

    public Question getQuestion(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("존재하지 않는 질문입니다."));
    }

    public void createQuestion(String title, String content, SiteUser author) {
        questionRepository.save(Question.builder()
                .title(title)
                .content(content)
                .author(author)
                .build());
    }

    public void edit(Question question, QuestionEdit questionEdit) {
        question.edit(questionEdit.getTitle(), questionEdit.getContent());
        questionRepository.save(question);
    }

    public void delete(Question question) {
        questionRepository.delete(question);
    }

    @Transactional
    public void addLikes(Question question, SiteUser user) {
        QuestionLikes found = questionUserRepository.findByQuestionAndUser(question, user);
        if (found == null) {
            QuestionLikes questionLikes = QuestionLikes.create(question, user);
            questionUserRepository.save(questionLikes);

            // 좋아요 알림
            if (user != question.getAuthor()) {
                QuestionAlert alert = QuestionAlert.createMessage(user, question.getAuthor(), question.getTitle());
                questionAlertRepository.save(alert);
            }
        } else {
            questionUserRepository.delete(found);
        }
    }

    @Transactional
    public void addCounts(Question question, String username) {
        SiteUser user = userRepository.findByUsername(username)
                .orElse(null);
        if (questionCountRepository.findByQuestionCountAndUserCount(question, user) == null) {
            QuestionCount questionCount = QuestionCount.create(question, user);
            questionCountRepository.save(questionCount);
        }
    }


    public Page<Question> search(String keyword, Pageable pageable) {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);
        QQuestion qQuestion = QQuestion.question;

        List<Question> searched = jpaQueryFactory.selectFrom(qQuestion)
                .where(qQuestion.title.containsIgnoreCase(keyword)
                        .or(qQuestion.content.containsIgnoreCase(keyword))
                        .or(qQuestion.author.username.containsIgnoreCase(keyword))
                )
                .orderBy(qQuestion.createdDate.desc())
                .fetch();

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), searched.size());

        return new PageImpl<>(searched.subList(start, end), pageable, searched.size());
    }

}
