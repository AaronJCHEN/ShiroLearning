package com.sjw.ShiroTest.ServiceImpl;

import com.sjw.ShiroTest.Dao.CommentDao;
import com.sjw.ShiroTest.Pojo.CommentPojo;
import com.sjw.ShiroTest.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by Watson on 01/24/2017.
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentDao commentDao;

    @Override
    public List<CommentPojo> getCommentsByProductService(Map params) {
        return commentDao.getCommentsByProduct(params);
    }
}
