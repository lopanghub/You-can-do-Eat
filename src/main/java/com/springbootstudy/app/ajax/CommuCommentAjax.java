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
import com.springbootstudy.app.service.CommuCommentService;
import com.springbootstudy.app.service.RecipeService;

@RestController
public class CommuCommentAjax {
	
	@Autowired
	private CommuCommentService commuCommentService;
	
	@Autowired
	private RecipeService recipeService;
	
	@PostMapping("/ajax/addCommuComment")
	public commentDTO addCommuComment(@RequestParam(name="no")int no,@RequestParam(name="commentContent")String commentContent,
			@RequestParam(name="commentPoint")int commentPoint,@RequestParam(name="memberId")String memberId) {
		Comment comment = commuCommentService.addCommuComment(no, commentContent, commentPoint,memberId);
		 // 평균 점수 계산
	    double averagePoint = commuCommentService.calculateAveragePoint(no);
		
		 recipeService.updateApoint(no,averagePoint);
		 
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
	
	
	@PostMapping("/ajax/updateCommuComment")
    public commentDTO updateCommuComment(@RequestParam(name="no")int no,
    								@RequestParam(name="commentId") int commentId, 
                                 @RequestParam(name="commentContent") String commentContent,
                                 @RequestParam(name="commentPoint") int commentPoint) {
		Comment comment =	commuCommentService.updatecCommuComment(commentId, commentContent, commentPoint);
		 // 평균 점수 계산
	    double averagePoint = commuCommentService.calculateAveragePoint(no);
		
		 recipeService.updateApoint(no,averagePoint);
		 
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
	
	
	@PostMapping("/ajax/deleteCommuComment")
	public commentDTO deleteCommuComment(@RequestParam(name="commentId") int commentId,@RequestParam(name="no")int no) {
		Comment comment =commuCommentService.deleteCommuComment(commentId);
		 // 평균 점수 계산
	    double averagePoint = commuCommentService.calculateAveragePoint(no);
		 recipeService.updateApoint(no,averagePoint);
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
