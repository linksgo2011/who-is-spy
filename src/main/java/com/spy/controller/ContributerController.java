package com.spy.controller;

import com.spy.model.Word;
import com.spy.model.WordFromContributer;
import com.spy.model.dao.WordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Created by bingwang on 11/28/17.
 */
@Controller
public class ContributerController {

    WordDao wordDao;

    @Autowired
    public ContributerController(WordDao wordDao) {
        this.wordDao = wordDao;
    }

    @RequestMapping(value = "/contributor", method = RequestMethod.GET)
    public String contribute() {
        return  "contributor/contributeWord";
    }

    @RequestMapping(value = "/contributor", method = RequestMethod.POST)
    public ModelAndView doAddWord
            (@ModelAttribute("word") WordFromContributer word,
             ModelAndView modelAndView,
             RedirectAttributes redirectAttributes
            ) {
        Word words = new Word();
        words.setOption1(word.getWord1());
        words.setOption2(word.getWord2());
        wordDao.save(words);

        redirectAttributes.addFlashAttribute(
                "flashSuccessMsg",
                "successfully added the word!"
        );

        modelAndView.setViewName("redirect:/contributor");
        return modelAndView;
    }

    @RequestMapping(value = "/findAllWords", method = RequestMethod.GET)
    public ModelAndView findAll(ModelAndView modelAndView){
        List<Word> wordList = wordDao.findAll();
        modelAndView.addObject("words",wordList);
        modelAndView.addObject("showWord","true");
        modelAndView.setViewName("contributor/contributeWord");
        return modelAndView;
    }
}
