package com.zhc.web;

import com.zhc.pojo.Filmtype;
import com.zhc.service.FilmTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller
public class CinemaController {
    @Autowired
    private FilmTypeService filmTypeService;

    List<Filmtype> allFilmType;

    @RequestMapping("/toCinema")
    public ModelAndView toCinema() {

        System.out.println("!!!!!!!!!!!!!!!toCinema");

        //获取所有的电影类型
        allFilmType = filmTypeService.findAllTypes();

        ModelAndView mv = new ModelAndView();
        mv.setViewName("cinema");

        mv.addObject("allFilmType",allFilmType);
        return mv;
    }

    @RequestMapping("/toAddFilm")
    public ModelAndView toAddFilm() {
        ModelAndView mv = new ModelAndView();
        mv.addObject("allFilmType",allFilmType);
        mv.setViewName("page/add");

        return mv;
    }
}
