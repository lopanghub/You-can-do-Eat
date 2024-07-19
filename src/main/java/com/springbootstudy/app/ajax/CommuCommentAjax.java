package com.springbootstudy.app.ajax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.springbootstudy.app.domain.Comment;
import com.springbootstudy.app.domain.CommuComment;
import com.springbootstudy.app.dto.CommentDTO;
import com.springbootstudy.app.service.CommentService;
import com.springbootstudy.app.service.CommuCommentService;
import com.springbootstudy.app.service.RecipeService;

@RestController
public class CommuCommentAjax {
	
	@Autowired
	private CommuCommentService commuCommentService;
	
	@Autowired
	private RecipeService recipeService;
	
	@PostMapping("/ajax/addCommuComment")
	public CommuComment addCommuComment(@RequestParam(name="no")int no,@RequestParam(name="commuCommentContent")String commuCommentContent,
			@RequestParam(name="memberId")String memberId) {
		System.out.println("memberId : "+ commuCommentContent );
		CommuComment comment = commuCommentService.addCommuComment(no, commuCommentContent,memberId);
	    
	    return comment;
	}
	
	
	@PostMapping("/ajax/updateCommuComment")
    public CommuComment updateCommuComment(
    								@RequestParam(name="commuCommentId") int commuCommentId, 
                                 @RequestParam(name="commuCommentContent") String commuCommentContent
                                ) {
		CommuComment commuComment =	commuCommentService.updatecCommuComment(commuCommentId, commuCommentContent);
		
		
		
        return commuComment;
    }
	
	
	@PostMapping("/ajax/deleteCommuComment")
	public CommuComment deleteCommuComment(@RequestParam(name="commuCommentId") int commuCommentId,@RequestParam(name="no")int no) {
		CommuComment commuComment =commuCommentService.deleteCommuComment(commuCommentId);
			
		 
		return commuComment;
		
	}
	
}
