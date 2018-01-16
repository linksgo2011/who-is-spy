package com.spy.model.dao;

import com.spy.model.Word;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by bingwang on 8/5/17.
 */
@Repository
public interface WordDao extends CrudRepository<Word, Integer> {
    Word findOneById(int id);
    List<Word> findAll();
}
