package com.richstone.cargo.controllers.web;

import com.richstone.cargo.dto.*;
import com.richstone.cargo.model.Question;
import com.richstone.cargo.model.Topic;
import com.richstone.cargo.service.impl.QuestionServiceImpl;
import com.richstone.cargo.service.impl.TopicServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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


    @GetMapping("/{pageNo}")
    public String topicsList(@PathVariable(value = "pageNo") int pageNo, Model model) {
        int pageSize = 10;
        Page<TopicRequestDto> topics = topicService.getAllTopics(pageNo, pageSize);
        model.addAttribute("topics", topics);
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", topics.getTotalPages());
        model.addAttribute("pageSize", pageSize);
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
        return "redirect:/topics/1";
    }

    @PostMapping("/save")
    public String addTopic(@ModelAttribute("topic") TopicRequestDto topic) {
        topicService.addTopic(topic);
        return "redirect:/topics/1";
    }

    @GetMapping("/formForAddTopic")
    public String formForAddTopic(Model model) {
        Topic topic = new Topic();
        model.addAttribute("topic", topic);
        return "topic-form";
    }

    @PostMapping("/delete")
    public String deleteTopic(@RequestParam("topicId") Long id) {
        topicService.delete(id);
        return "redirect:/topics/1";
    }

    @PostMapping("/deleteQuestion")
    public String deleteQuestion(@RequestParam("questionId") Long id) {
        questionService.delete(id);
        return "redirect:/topics/1";
    }

}
