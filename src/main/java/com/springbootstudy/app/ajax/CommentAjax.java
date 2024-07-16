package com.springbootstudy.app.ajax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.springbootstudy.app.domain.Comment;
import com.springbootstudy.app.dto.commentDTO;
import com.springbootstudy.app.service.CommentService;
import com.springbootstudy.app.service.RecipeService;

@RestController
public class CommentAjax {
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private RecipeService recipeService;
	
	@PostMapping("/ajax/addComment")
	public commentDTO addComment(@RequestParam(name="boardNo")int boardNo,@RequestParam(name="commentContent")String commentContent,
			@RequestParam(name="commentPoint")int commentPoint,@RequestParam(name="memberId")String memberId) {
		Comment comment = commentService.addComment(boardNo, commentContent, commentPoint,memberId);
		 // 평균 점수 계산
	    double averagePoint = commentService.calculateAveragePoint(boardNo);
		
		 recipeService.updateApoint(boardNo,averagePoint);
		 
	    // 평균 점수 별점
	    StringBuilder symbols = new StringBuilder();
	    int i = 0;
	    int Ipoint = (int) (averagePoint * 10) / 10;
	    for (int j = 0; j < Ipoint; j++) {
	        symbols.append(" <i class=\"bi bi-star-fill\"></i>");
	        i++;
	    }
	    if ((averagePoint * 10) % 10 > 1) {
	        if (i < 5) {
	            symbols.append(" <i class=\"bi bi-star-half\"></i>");
	            i++;
	        }
	    }
	    for (int j = i; j < 5; j++) {
	        symbols.append(" <i class=\"bi bi-star\"></i>");
	        i++;
	    }
	    String stars = symbols.toString();
	    
	    // commentDTO 객체 생성 및 반환
	    commentDTO commentDTO = new commentDTO(comment, stars);
	    return commentDTO;
	}
	
	
	@PostMapping("/ajax/updateComment")
    public commentDTO updateComment(@RequestParam(name="boardNo")int boardNo,
    								@RequestParam(name="commentId") int commentId, 
                                 @RequestParam(name="commentContent") String commentContent,
                                 @RequestParam(name="commentPoint") int commentPoint) {
		Comment comment =	commentService.updateComment(commentId, commentContent, commentPoint);
		 // 평균 점수 계산
	    double averagePoint = commentService.calculateAveragePoint(boardNo);
		
		 recipeService.updateApoint(boardNo,averagePoint);
		 
	    // 평균 점수 별점
	    StringBuilder symbols = new StringBuilder();
	    int i = 0;
	    int Ipoint = (int) (averagePoint * 10) / 10;
	    for (int j = 0; j < Ipoint; j++) {
	        symbols.append(" <i class=\"bi bi-star-fill\"></i>");
	        i++;
	    }
	    if ((averagePoint * 10) % 10 > 1) {
	        if (i < 5) {
	            symbols.append(" <i class=\"bi bi-star-half\"></i>");
	            i++;
	        }
	    }
	    for (int j = i; j < 5; j++) {
	        symbols.append(" <i class=\"bi bi-star\"></i>");
	        i++;
	    }
	    String stars = symbols.toString();
	    
	    // commentDTO 객체 생성 및 반환
	    commentDTO commentDTO = new commentDTO(comment, stars);
		
		
        return commentDTO;
    }
	
	
	@PostMapping("/ajax/deleteComment")
	public commentDTO deleteComment(@RequestParam(name="commentId") int commentId,@RequestParam(name="boardNo")int boardNo) {
		Comment comment =commentService.deleteComment(commentId);
		 // 평균 점수 계산
	    double averagePoint = commentService.calculateAveragePoint(boardNo);
		 recipeService.updateApoint(boardNo,averagePoint);
		 // 평균 점수 별점
		    StringBuilder symbols = new StringBuilder();
		    int i = 0;
		    int Ipoint = (int) (averagePoint * 10) / 10;
		    for (int j = 0; j < Ipoint; j++) {
		        symbols.append(" <i class=\"bi bi-star-fill\"></i>");
		        i++;
		    }
		    if ((averagePoint * 10) % 10 > 1) {
		        if (i < 5) {
		            symbols.append(" <i class=\"bi bi-star-half\"></i>");
		            i++;
		        }
		    }
		    for (int j = i; j < 5; j++) {
		        symbols.append(" <i class=\"bi bi-star\"></i>");
		        i++;
		    }
		    String stars = symbols.toString();
		    
		    // commentDTO 객체 생성 및 반환
		    commentDTO commentDTO = new commentDTO(comment, stars);
			
		 
		return commentDTO;
		
	}
	
}
