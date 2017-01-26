package com.sjw.ShiroTest.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sjw.ShiroTest.Pojo.CommentPojo;
import com.sjw.ShiroTest.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Watson on 01/24/2017.
 */
@Controller
@RequestMapping(value = "/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/byProduct", method = RequestMethod.POST)
    public String getCommentsByProduct(HttpServletRequest request) throws JsonProcessingException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        int pageNum = 1;
        if(request.getParameter("pageNum") != null)
            pageNum = Integer.parseInt(request.getParameter("pageNum"));
        Map<String,Integer> params = new HashMap<>();
        int offset = (pageNum-1)*10;
        params.put("productId",productId);
        params.put("offset",offset);
        List<CommentPojo> comments = commentService.getCommentsByProductService(params);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(comments);
    }
}
