package com.zhc.service.impl;

import com.zhc.mapper.FilminfoMapper;
import com.zhc.mapper.FilmtypeMapper;
import com.zhc.pojo.Filmtype;
import com.zhc.service.FilmTypeService;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
/**
 * bean id="filmTypeService" class="org.gec.service.impl.FilmTypeServiceImpl"
 */
@Transactional
public class FilmTypeServiceImpl implements FilmTypeService {
    @Autowired
    private FilmtypeMapper filmtypeMapper;

    @Override
    public List<Filmtype> findAllTypes() {
        List<Filmtype> types = filmtypeMapper.selectByExample(null);
        System.out.println("types------------------:" + types.size());
        return types;
    }

}
