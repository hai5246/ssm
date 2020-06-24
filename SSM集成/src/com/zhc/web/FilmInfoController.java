package com.zhc.web;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhc.pojo.Filminfo;
import com.zhc.pojo.FilminfoExample;
import com.zhc.pojo.Filmtype;
import com.zhc.service.FilmInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class FilmInfoController {

    @Autowired//byType
    private FilmInfoService filmInfoService;

    public List<Filminfo> getResult() {
        return result;
    }

    //电影list
    List<Filminfo> result;

    //每页显示数量
    private static final Integer PAGESIZE = 2;


    @RequestMapping("/findFilms")
    public ModelAndView findFilms(Filminfo film,FilminfoExample filminfo,@RequestParam(value = "typeid",required = false,defaultValue = "0") Integer typeid,
                                  @RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum) throws Exception{
        //选了类型
        System.out.println("typeid:"+typeid);
        FilminfoExample.Criteria criteria = filminfo.createCriteria();
        if (typeid != 0){
            criteria.andTypeidEqualTo(typeid);
        }

        //各种判断
        if(StringUtils.isNotEmpty(film.getFilmname())){
            criteria.andFilmnameLike("%"+film.getFilmname()+"%");
        }

        if(StringUtils.isNotEmpty(film.getActor())){
            criteria.andActorLike("%"+film.getActor()+"%");
        }

        if(StringUtils.isNotEmpty(film.getDirector())){
            criteria.andDirectorLike("%"+film.getDirector()+"%");
        }

        if(film.getBigprice() != null && film.getBigprice() != 0){
            criteria.andTicketpriceLessThan(new BigDecimal(film.getBigprice()));
        }

        if(film.getSmallprice() != null && film.getSmallprice() != 0){
            criteria.andTicketpriceGreaterThan(new BigDecimal(film.getSmallprice()));
        }

        //设置分页
        PageHelper.startPage(pageNum, PAGESIZE);

        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!");

        //查询
        result = filmInfoService.findAllInfo2(filminfo);

        if (result == null){
            System.out.println("result为空");
        }else {
            result.forEach(filminfo1 -> System.out.println(filminfo1+"!!!"));
        }

        ModelAndView mv = new ModelAndView();

        //分页上一页，下一页，尾页，总记录数
        PageInfo info = new PageInfo(result);
        mv.addObject("info",info);
        mv.addObject("typeid",typeid);

        mv.addObject("result",result);
        mv.setViewName("page/result");
        return mv;
    }

    //检查用户名是否被占用
    @RequestMapping("/checkName")
    @ResponseBody
    public String checkname(String filmname){
        boolean result = filmInfoService.checkName(filmname);
        System.out.println("result:"+result);
        return result+"";
    }


    @RequestMapping("/FilmAddServlet")
    public String FilmAddServlet(@Validated Filminfo filminfo, BindingResult result,
                                 Model model,
                                 Integer typeid, MultipartFile picname) throws Exception{

        //判断有没有校验错误
        if (result.hasErrors()){
            //循环获得错误
            List<FieldError> fieldErrors = result.getFieldErrors();
            for (FieldError error:fieldErrors){
                //error.getField() 出错的字段
                //error.getDefaultMessage() 配置的错误信息
                model.addAttribute(error.getField(),error.getDefaultMessage());//配置的错误信息
            }
            return "forward:/toAddFilm";
        }

        //电影类型
        Filmtype type = new Filmtype();
        type.setTypeid(typeid);
        filminfo.setFilmtype(type);

        //上传的文件名
        String fileNmae = picname.getOriginalFilename();
        //文件存储路径
        File file = new File("F:\\粤嵌第三阶段\\ssm集成\\day3",fileNmae);
        filminfo.setPic(fileNmae);

        //写到磁盘
        picname.transferTo(file);

        //查询
        filmInfoService.save(filminfo);

        return "redirect:/toAddFilm";
    }

    //批量删除
    @RequestMapping("/deleteFilms")
    public String  deleteFilms(int[] filmIds){

        System.out.println("!!!!!!!!!!!!!!!!!!!filmIds:"+filmIds.toString());
        //调用方法
        filmInfoService.deleteByIds(filmIds);
        return "redirect:/toCinema";
    }

    //rest风格
    @RequestMapping("/queryDetails/{filmid}/{filmname}")
    @ResponseBody
    public Filminfo queryDetails(@PathVariable("filmid") Integer filmid, @PathVariable("filmname") String filmname){
        System.out.println("filmname:"+filmname);
        Filminfo filminfo = filmInfoService.selectByPrimaryKey(filmid);
        System.out.println("@@@@@@@@@"+filminfo);
        return filminfo;
    }
}
