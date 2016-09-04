package com.adoulfakkar.quizzApp.webapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.adoulfakkar.quizzApp.db.model.Answer;
import com.adoulfakkar.quizzApp.db.model.AnsweredQuestion;
import com.adoulfakkar.quizzApp.db.model.Question;
import com.adoulfakkar.quizzApp.db.model.User;
import com.adoulfakkar.quizzApp.service.api.QuestionService;
import com.adoulfakkar.quizzApp.service.api.UserService;
import com.adoulfakkar.quizzApp.webapp.vo.AnswerVO;
import com.adoulfakkar.quizzApp.webapp.vo.QuestionResponse;
import com.adoulfakkar.quizzApp.webapp.vo.ScoreResult;


@Controller
@RequestMapping("question")
@Transactional(readOnly=false)
public class QuestionController extends AbstractController {

	@Autowired
	private QuestionService questionService;

	@Autowired
	private UserService userService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	@ResponseBody
	public Question get(@PathVariable Integer id) {
		return questionService.find(id);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value="/", method=RequestMethod.GET)
	@ResponseBody
	public QuestionResponse getAll(@RequestParam(required=false) Integer from, @RequestParam(required=false) Integer size) {
		QuestionResponse qRes = new QuestionResponse();
		if (from == null && size == null) {
			List<Question> questions = questionService.getAll();
			qRes.setFrom(0);
			qRes.setCount(new Long (questions.size()));
			qRes.setList(questions);
		}
		else {
			if (from == null)
				from = 0;
			if (size == null)
				size = 10;
			qRes.setFrom(from);
			qRes.setCount(questionService.getAllCount ());
			qRes.setList(questionService.getPaged (from, size));
		}
		return qRes;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value="{id}", method=RequestMethod.DELETE)
	@ResponseBody
	public void delete(@PathVariable Integer id) {
		Question question = questionService.find(id);
		questionService.delete(question);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value="/", method=RequestMethod.PUT)
	@ResponseBody
	public Question update(@RequestBody Question question) {
		if (question.getAnswers() != null) {
			for (Answer answer : question.getAnswers()) {
				answer.setQuestion(question);
			}
		}
		question = questionService.update(question);
		return question;
	}

	@PreAuthorize("hasRole('ADMIN')")
	@RequestMapping(value="/", method=RequestMethod.POST)
	@ResponseBody
	public Question insert(@RequestBody Question question) {
		if (question.getAnswers() != null) {
			for (Answer answer : question.getAnswers()) {
				answer.setQuestion(question);
			}
		}
		questionService.insert(question);
		return question;
	}
	
	@RequestMapping(value="/quiz", method=RequestMethod.GET)
	@ResponseBody
	public List<Question> getQuestion() {
		User user = userService.find(getUser().getId());
		return questionService.getQuestionForQuiz(user);
	}
	
	@RequestMapping(value="/answer", method=RequestMethod.POST)
	@ResponseBody
	public ScoreResult getQuestion(@RequestBody List<AnswerVO> answers) {
		User user = userService.find(getUser().getId());
		Integer score = 0;
		for (AnswerVO answerVo : answers) {
			Question question = questionService.find(answerVo.getQuestionId());
			AnsweredQuestion answeredQuestion = new AnsweredQuestion();
			answeredQuestion.setUser(user);
			answeredQuestion.setQuestion(question);
			questionService.saveAnswer (answeredQuestion);
			
			score += answerVo.getScore();
		}
		user.setScore(user.getScore() + score);
		user.getShop().setScore(user.getShop().getScore() + score);
		userService.update(user);
		
		ScoreResult scoreResult = new ScoreResult();
		scoreResult.setUserScores(userService.getScores(user));
		scoreResult.setShopScores(userService.getScoreShop(user));
		
		return scoreResult;
	}

	@RequestMapping(value="/inject", method=RequestMethod.GET)
	@ResponseBody
	public List<Question> inject() {
		List<Question> res = new ArrayList<Question> ();
		
		// type text
		Question q = new Question();
		q.setType(0);
		q.setIsGood(null);
		q.setTitle("Mariusz ressemble à ?");
		
		List<Answer> answers = new ArrayList<Answer>();
		Answer answer = new Answer();
		answer.setGood(true);
		answer.setImageUrl(null);
		answer.setText("Gru");
		answer.setQuestion(q);
		answers.add(answer);
		
		answer = new Answer();
		answer.setGood(false);
		answer.setImageUrl(null);
		answer.setText("Mac gyver");
		answer.setQuestion(q);
		answers.add(answer);
		
		answer = new Answer();
		answer.setGood(false);
		answer.setImageUrl(null);
		answer.setText("Thomas");
		answer.setQuestion(q);
		answers.add(answer);
		
		answer = new Answer();
		answer.setGood(true);
		answer.setImageUrl(null);
		answer.setText("Ta mère");
		answer.setQuestion(q);
		answers.add(answer);
		
		q.setAnswers(answers);
		questionService.insert(q);
		res.add(q);
		
		q = new Question();
		q.setType(1);
		q.setIsGood(true);
		q.setTitle("Vai / Faux, Mariusz ressemble à Gru ?");
		q.setAnswers(null);
		questionService.insert(q);
		res.add(q);
		
		q = new Question();
		q.setType(2);
		q.setIsGood(null);
		q.setTitle("A qui ressemble Mariusz ?");
		
		answers = new ArrayList<Answer> ();
		
		answer = new Answer ();
		answer.setGood(false);
		answer.setText(null);
		answer.setImageUrl("/images/macgyver.jpg");
		answer.setQuestion(q);
		answers.add(answer);
		
		answer = new Answer ();
		answer.setGood(false);
		answer.setText(null);
		answer.setImageUrl("/images/gru.jpg");
		answer.setQuestion(q);
		answers.add(answer);
		
		answer = new Answer ();
		answer.setGood(false);
		answer.setText(null);
		answer.setImageUrl("/images/tgayet.jpg");
		answer.setQuestion(q);
		answers.add(answer);
		
		answer = new Answer ();
		answer.setGood(false);
		answer.setText(null);
		answer.setImageUrl("/images/maman.jpg");
		answer.setQuestion(q);
		answers.add(answer);
		
		q.setAnswers(answers);
		questionService.insert(q);
		res.add(q);
		
		q = new Question();
		q.setType(3);
		q.setIsGood(null);
		q.setTitle("Remettre les lettres dans le bon ordre");
		answer = new Answer ();
		answer.setGood(true);
		answer.setImageUrl(null);
		answer.setQuestion(q);
		answer.setText("mac gyver");
		
		answers = new ArrayList<Answer> ();
		answers.add(answer);
		q.setAnswers(answers);

		questionService.insert(q);
		res.add(q);
		
		return res;
	}
}
