package usability.scale.system.spring.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;
import usability.scale.system.calculator.AnswerChoice;
import usability.scale.system.calculator.Questions;
import usability.scale.system.calculator.ScoreCalculator;
import usability.scale.system.calculator.dto.Answers;
import usability.scale.system.spring.SusService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class SusController {

    @Autowired
    SusService susService;

    @GetMapping({"/", "/index"})
    String index(@ModelAttribute Answers answers, Model model, HttpServletRequest request) {
        model.addAttribute("questions", Questions.ALL);
        model.addAttribute("choices", AnswerChoice.values());

        HttpSession session = request.getSession();
        if (!session.isNew()) {
            request.changeSessionId();
        }

        return "index";
    }

    @PostMapping("/submit")
    String submit(@ModelAttribute Answers answers, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid session, or session has submitted an answer before.Please visit the main page to create a new valid session");
        } else {
            //a session can submit only once
            session.invalidate();
        }

        double result = new ScoreCalculator().calculateScore(answers);
        susService.addScore(result);

        model.addAttribute("score", result);
        return "score";
    }

    @GetMapping("/statistics")
    String globalStatistics(Model model) {
        model.addAttribute("allScores", susService.getAllScores());
        model.addAttribute("allScoresAverage", susService.getAllTimeAverage());
        model.addAttribute("hourlyScore", susService.getHourlyScore());
        model.addAttribute("dailyScore", susService.getDailyScore());
        model.addAttribute("weeklyScore", susService.getWeeklyScore());
        model.addAttribute("monthlyScore", susService.getMonthlyScore());
        return "statistics";
    }

    @ExceptionHandler(value = ResponseStatusException.class)
    String handleError(ResponseStatusException exception, Model model, HttpServletResponse response) {
        model.addAttribute("errorMessage", exception.getMessage());
        model.addAttribute("statusCode", exception.getRawStatusCode());
        response.setStatus(exception.getRawStatusCode());
        return "custom-error";
    }

}
