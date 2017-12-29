package com.sjw.ShiroTest.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sjw.ShiroTest.Pojo.CommentPojo;
import com.sjw.ShiroTest.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Watson on 01/24/2017.
 */
@CrossOrigin("http://localhost:8082")
@RestController
@RequestMapping(value = "/ShiroTest/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @RequestMapping(value = "/byProduct", method = RequestMethod.POST)
    public List getCommentsByProduct(HttpServletRequest request) throws JsonProcessingException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        int pageNum = 1;
        if(request.getParameter("pageNum") != null)
            pageNum = Integer.parseInt(request.getParameter("pageNum"));
        Map<String,Integer> params = new HashMap<>();
        int offset = (pageNum-1)*10;
        params.put("productId",productId);
        params.put("offset",offset);
        List<CommentPojo> comments = commentService.getCommentsByProductService(params);
        return comments;
    }
}
