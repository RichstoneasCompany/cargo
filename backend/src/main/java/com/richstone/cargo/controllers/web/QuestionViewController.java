package com.richstone.cargo.controllers.web;

import com.richstone.cargo.dto.*;
import com.richstone.cargo.mapper.TopicMapper;
import com.richstone.cargo.model.Question;
import com.richstone.cargo.model.Topic;
import com.richstone.cargo.model.User;
import com.richstone.cargo.repository.QuestionRepository;
import com.richstone.cargo.repository.TopicRepository;
import com.richstone.cargo.service.impl.QuestionServiceImpl;
import com.richstone.cargo.service.impl.TopicServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/topics")
public class QuestionViewController {
    private final TopicServiceImpl topicService;
    private final QuestionServiceImpl questionService;


    @GetMapping
    public String topicsList(Model model) {
        List<TopicRequestDto> topics = topicService.getAllTopics();
        model.addAttribute("topics", topics);
        return "topic-page";
    }

    @GetMapping("/{topicId}/questions")
    public String showQuestionsForTopic(@PathVariable Long topicId, Model model) {
        List<QuestionDto> questions = questionService.getAllQuestions(topicId);
        model.addAttribute("questions", questions);
        model.addAttribute("topicId", topicId);
        model.addAttribute("question", new QuestionAddDto());
        return "question-page";
    }
    @GetMapping("/formForAddQuestion")
    public String formForAddQuestion(Model model) {
        Question question = new Question();
        model.addAttribute("question", question);
        return "topic-list";
    }

    @PostMapping("/addQuestion")
    public String addQuestion(@ModelAttribute("question") QuestionAddDto questionDto) {
        questionService.addQuestionToExistingTopic(questionDto);
        return "redirect:/topics";
    }

    @PostMapping("/save")
    public String addDispatcher(@ModelAttribute("topic") TopicRequestDto topic) {
       topicService.addTopic(topic);
        return "redirect:/topics";
    }

    @GetMapping("/formForAddTopic")
    public String formForAddTopic(Model model) {
        Topic topic = new Topic();
        model.addAttribute("topic", topic);
        return "topic-form";
    }
    @PostMapping("/delete")
    public String deleteDispatcher(@RequestParam("topicId") Long id) {
        topicService.delete(id);
        return "redirect:/topics";
    }

}
