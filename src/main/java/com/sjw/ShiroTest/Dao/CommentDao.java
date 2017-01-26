package com.sjw.ShiroTest.Dao;

import com.sjw.ShiroTest.Pojo.CommentPojo;
import org.mybatis.spring.annotation.MapperScan;

import java.util.List;
import java.util.Map;

/**
 * Created by Watson on 01/24/2017.
 */
@MapperScan
public interface CommentDao {
    public List<CommentPojo> getCommentsByProduct(Map params);

}
