package com.springbootstudy.app.ajax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.springbootstudy.app.domain.Comment;
import com.springbootstudy.app.service.CommentService;

@RestController
public class CommentAjax {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/ajax/addComment")
	public Comment addComment(@RequestParam(name="boardNo")int boardNo,@RequestParam(name="commentContent")String commentContent,
			@RequestParam(name="commentPoint")int commentPoint/*,@RequestParam(name="memberId")String memberId*/) {
		return commentService.addComment(boardNo, commentContent, commentPoint);
	}
	@PostMapping("/ajax/updateComment")
    public Comment updateComment(@RequestParam(name="commentId") int commentId, 
                                 @RequestParam(name="commentContent") String commentContent,
                                 @RequestParam(name="commentPoint") int commentPoint) {
        return commentService.updateComment(commentId, commentContent, commentPoint);
    }
	@PostMapping("/ajax/deleteComment")
	public Comment deleteComment(@RequestParam(name="commentId") int commentId) {
		System.out.println("deleteComment ajax입니다 "+ commentId +"를 받았습니다.");
		return commentService.deleteComment(commentId);
	}
	
}
