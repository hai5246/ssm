package com.zhc.mapper;

import com.zhc.pojo.Filminfo;
import com.zhc.pojo.FilminfoExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FilminfoMapper {
    int countByExample(FilminfoExample example);

    int deleteByExample(FilminfoExample example);

    int deleteByPrimaryKey(Integer filmid);

    int insert(Filminfo record);

    int insertSelective(Filminfo record);

    List<Filminfo> selectByExample(FilminfoExample example);

    Filminfo selectByPrimaryKey(Integer filmid);

    int updateByExampleSelective(@Param("record") Filminfo record, @Param("example") FilminfoExample example);

    int updateByExample(@Param("record") Filminfo record, @Param("example") FilminfoExample example);

    int updateByPrimaryKeySelective(Filminfo record);

    int updateByPrimaryKey(Filminfo record);

    List<Filminfo> findAllFilmInfo(Filminfo filminfo);
}