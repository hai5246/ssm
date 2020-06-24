package com.zhc.service.impl;

import com.zhc.mapper.FilminfoMapper;
import com.zhc.pojo.Filminfo;
import com.zhc.pojo.FilminfoExample;
import com.zhc.service.FilmInfoService;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class FilmInfoServiceImpl implements FilmInfoService {
    @Autowired
    private FilminfoMapper filminfoMapper;
    @Override
    public List<Filminfo> findAllInfo(Filminfo filminfo) {
        return filminfoMapper.findAllFilmInfo(filminfo);
    }

    @Override
    public void save(Filminfo filminfo) {
        filminfoMapper.insert(filminfo);
    }

    @Override
    public void deleteByIds(int[] filmIds) {
        for(int id:filmIds){

            filminfoMapper.deleteByPrimaryKey(id);
        }
    }

    @Override
    public List<Filminfo> findAllInfo2(FilminfoExample filminfo) {
        List<Filminfo> filminfos = filminfoMapper.selectByExample(filminfo);
        return filminfos;
    }

    @Override
    public boolean checkName(String filmname) {
        FilminfoExample example = new FilminfoExample();
        FilminfoExample.Criteria criteria = example.createCriteria();

        criteria.andFilmnameEqualTo(filmname);

        List<Filminfo> list = filminfoMapper.selectByExample(example);

        if (list != null && list.size() > 0){
            return true;//已有电影名
        }else{
            return false;
        }
    }

    @Override
    public Filminfo selectByPrimaryKey(Integer filmid) {
        Filminfo filminfo = filminfoMapper.selectByPrimaryKey(filmid);
        return filminfo;
    }
}
