package com.springboot.demo.service;

import com.springboot.demo.mapper.GameExtMapper;
import com.springboot.demo.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Component

public class MybatisService {
    @Autowired(required = false)
    private GameExtMapper gameMapper;

    public void insertAll(Game game) throws Exception {
        try {
            gameMapper.insertSelective(game);
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    public List<Game> findGame(Date start, Date end, String type) throws Exception {
        try {
            List<Game> gameList = gameMapper.selectgame(start, end, type);
            return gameList;
        } catch (Exception e) {
            System.out.print(e);
            return null;
        }
    }

    public List<Game> findGameAll() throws Exception {
        try {
            List<Game> gameList = gameMapper.selectall();
            return gameList;
        } catch (Exception e) {
            System.out.print(e);
            return null;
        }
    }
}
