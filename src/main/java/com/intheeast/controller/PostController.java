package com.intheeast.controller;

import com.intheeast.entity.Comment;
import com.intheeast.entity.Post;
import com.intheeast.service.CommentService;
import com.intheeast.service.EntityCallback;
import com.intheeast.service.PostService;
import com.intheeast.utils.Utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private EntityCallback<Post> postCallbackImpl;

    @Autowired
    private EntityCallback<Comment> commentCallbackImpl;
    
    // properties 파일에 적용 예상
    private static final int PAGE_SIZE = 10; // 한 페이지에 표시할 게시글 수


 // 게시글 목록 조회 (페이지네이션 추가)
    @GetMapping
    public String listPosts(@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        // 전체 게시글 개수 조회
        long totalPosts = postService.countPosts();

        // 게시글이 없을 경우 바로 반환
        if (totalPosts == 0) {
            model.addAttribute("posts", Collections.emptyList()); // 빈 리스트를 반환
            model.addAttribute("currentPage", 1); // 페이지 번호를 1로 설정
            model.addAttribute("totalPages", 0); // 총 페이지 수는 0
            return "post/list"; // 바로 뷰를 반환
        }

        int totalPages = (int) Math.ceil((double) totalPosts / PAGE_SIZE); // 총 페이지 수 계산

        // 해당 페이지의 게시글 목록 조회
        List<Post> posts = postService.findPostsByPage(page, PAGE_SIZE);

        model.addAttribute("posts", posts);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "post/list";
    }


    // 게시글 상세 조회 및 댓글 표시
    @GetMapping("/{id}")
    public String getPostById(@PathVariable("id") Long id, Model model) {
        Post post = postService.findById(id, postCallbackImpl);
        if (post == null) {
            return "post/not_found";
        }

        List<Comment> comments = commentService.findAllCommentsByPost(post, commentCallbackImpl);
        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        model.addAttribute("comment", new Comment()); // 새 댓글 작성 폼을 위해 빈 객체 추가
        return "post/detail";
    }

    // 게시글 작성
    @GetMapping("/new")
    public String showNewPostForm(Model model) {
        model.addAttribute("post", new Post());
        return "post/new";
    }

    @PostMapping
    public String createPost(@Valid @ModelAttribute("post") Post post, 
    		BindingResult result,
    		HttpServletRequest request) {
        if (result.hasErrors()) {
            return "post/new";
        }
        
        String clientIp = Utilities.getClientIp(request);
        post.setIpAddress(clientIp);
        
        postService.post(post, postCallbackImpl);
        return "redirect:/posts";
    }

    // 게시글 삭제
    @PostMapping("/{postId}/delete")
    public String deletePost(@PathVariable("postId") Long postId) {
        Post post = postService.findById(postId, postCallbackImpl);
        if (post != null) {
            postService.delete(post, postCallbackImpl);
        }
        return "redirect:/posts";
    }

    // 댓글 추가
    @PostMapping("/{postId}/comments")
    public String addCommentToPost(@PathVariable("postId") Long postId, 
                                   @Valid @ModelAttribute("comment") Comment comment, 
                                   BindingResult result, 
                                   HttpServletRequest request, // HttpServletRequest로 IP 주소 가져오기
                                   Model model) {
        if (result.hasErrors()) {
            return "redirect:/posts/" + postId;
        }

        Post post = postService.findById(postId, postCallbackImpl);
        if (post == null) {
            return "post/not_found";
        }

        // IP 주소 설정
        String clientIp = Utilities.getClientIp(request);
        comment.setIpAddress(clientIp);
        
        comment.setPost(post);
        commentService.post(comment, commentCallbackImpl);

        return "redirect:/posts/" + postId;
    }

    // 댓글 수정 폼으로 이동
    @GetMapping("/{postId}/comments/{commentId}/edit")
    public String showEditCommentForm(@PathVariable("postId") Long postId,
                                      @PathVariable("commentId") Long commentId,
                                      Model model) {
        Post post = postService.findById(postId, postCallbackImpl);
        if (post == null) {
            return "post/not_found";
        }

        Comment comment = commentService.findById(commentId, commentCallbackImpl);
        if (comment == null) {
            return "post/not_found";
        }

        model.addAttribute("post", post);
        model.addAttribute("comment", comment);
        return "post/edit_comment";  // 댓글 수정 폼으로 이동
    }

    // 댓글 수정
    @PostMapping("/{postId}/comments/{commentId}/edit")
    public String updateComment(@PathVariable("postId") Long postId,
                                @PathVariable("commentId") Long commentId,
                                @Valid @ModelAttribute("comment") Comment comment,
                                BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "post/edit_comment";
        }

        Post post = postService.findById(postId, postCallbackImpl);
        if (post == null) {
            return "post/not_found";
        }

        Comment existingComment = commentService.findById(commentId, commentCallbackImpl);
        if (existingComment == null) {
            return "post/not_found";
        }

        existingComment.setName(comment.getName());
        existingComment.setEmail(comment.getEmail());
        existingComment.setText(comment.getText());

        commentService.post(existingComment, commentCallbackImpl);
        return "redirect:/posts/" + postId;
    }

    // 댓글 삭제
    @PostMapping("/{postId}/comments/{commentId}/delete")
    public String deleteComment(@PathVariable("postId") Long postId,
                                @PathVariable("commentId") Long commentId) {
        Post post = postService.findById(postId, postCallbackImpl);
        if (post == null) {
            return "post/not_found";
        }

        Comment comment = commentService.findById(commentId, commentCallbackImpl);
        if (comment != null) {
            commentService.delete(comment, commentCallbackImpl);
        }

        return "redirect:/posts/" + postId;
    }
}