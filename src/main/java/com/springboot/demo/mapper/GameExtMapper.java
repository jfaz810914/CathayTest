package com.springboot.demo.mapper;

import com.springboot.demo.model.Game;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface GameExtMapper extends GameMapper{
    List<Game> selectall();
    List<Game> selectgame(@Param("start") Date start, @Param("end") Date end, @Param("type") String type);

}