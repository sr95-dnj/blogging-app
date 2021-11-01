package com.blogging.Controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.blogging.constant.BlogConstant;
import com.blogging.entity.Comment;
import com.blogging.entity.Post;
import com.blogging.entity.User;
import com.blogging.service.CommentService;
import com.blogging.service.PostService;
import com.blogging.service.UserService;

@Controller
@RequestMapping("/blogger")
public class BloggerController {

	@Autowired
	private PostService postService;

	@Autowired
	private UserService userService;

	@Autowired
	private CommentService commentService;

	@GetMapping("/createpost")
	public String createPost(Model model) {
		model.addAttribute("post", new Post());
		return "blogger/create-post";
	}

	@PostMapping("/createpost")
	public String savePost(@Valid Post post, BindingResult bindingResult, Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		if (!bindingResult.hasErrors()) {
			post.setUser(user);
			post.setDetails(post.getDetails().trim());
			post.setTime(LocalDateTime.now());
			post.setIsApproved(BlogConstant.PENDING_STATUS);
			postService.save(post);
		}
		model.addAttribute("post", new Post());
		model.addAttribute("msg", "Post Created Successfully & Send to Admin Approval");
		return "blogger/create-post";
	}

	@GetMapping("/newsfeed")
	public String newsFeed(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		List<Post> posts = postService.findByIsApproved(BlogConstant.APPROVED_STATUS);
		model.addAttribute("user", user);
		model.addAttribute("posts", posts);
		model.addAttribute("page", "newsfeed");
		return "blogger/view-post";
	}

	@GetMapping("/pendingpost")
	public String pendingPost(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		List<Post> posts = postService.findByIsApprovedAndUser(BlogConstant.PENDING_STATUS, user);
		model.addAttribute("posts", posts);
		return "blogger/pending-post";
	}

	@GetMapping("/timeline")
	public String timeline(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		List<Post> posts = postService.findByIsApprovedAndUser(BlogConstant.APPROVED_STATUS, user);
		model.addAttribute("user", user);
		model.addAttribute("posts", posts);
		model.addAttribute("page", "timeline");
		return "blogger/view-post";
	}

	@PostMapping("/like")
	public String likePost(Principal principal, HttpServletRequest request) {
		User user = userService.findByUsername(principal.getName());
		Post post = postService.findById(Integer.valueOf(request.getParameter("postId")));
		String page = request.getParameter("page");
		if (post != null) {
			postService.reactPost(post, user, BlogConstant.LIKE_STATUS);
		}
		return "redirect:/blogger/" + page;
	}

	@PostMapping("/dislike")
	public String dislikePost(Principal principal, HttpServletRequest request) {
		User user = userService.findByUsername(principal.getName());
		Post post = postService.findById(Integer.valueOf(request.getParameter("postId")));
		String page = request.getParameter("page");
		if (post != null) {
			postService.reactPost(post, user, BlogConstant.DISLIKE_STATUS);
		}
		return "redirect:/blogger/" + page;
	}

	@GetMapping("/comments")
	public String comments(@RequestParam("id") Integer id, Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		Post post = postService.findById(id);
		List<Comment> comments = post.getComments();
		model.addAttribute("user", user);
		model.addAttribute("post", post);
		model.addAttribute("comments", comments);
		model.addAttribute("comment", new Comment());
		return "blogger/comments";
	}

	@PostMapping("/comments")
	public String addComment(@Valid Comment comment, BindingResult result, HttpServletRequest request,
			Principal principal) {
		User user = userService.findByUsername(principal.getName());
		Post post = postService.findById(Integer.valueOf(request.getParameter("postId")));
		if (!result.hasErrors()) {
			comment.setDetails(comment.getDetails().trim());
			comment.setUser(user);
			comment.setPost(post);
			comment.setTime(LocalDateTime.now());
			commentService.save(comment);
		}
		return "redirect:/blogger/comments?id=" + post.getId();
	}

	@GetMapping("/removecomment")
	public String removeComment(@RequestParam("id") Integer id, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		Comment comment = commentService.findById(id);
		if (comment != null && comment.getUser().equals(user)) {
			commentService.deleteById(id);
		}
		return "redirect:/blogger/comments?id=" + comment.getPost().getId();
	}

	@GetMapping("/viewreacteduser")
	public String viewReactedUser(@RequestParam("id") Integer id, Model model) {
		Post post = postService.findById(id);
		model.addAttribute("reacts", post.getReacts());
		return "blogger/reacted-users";
	}

	@PostMapping("/deletepost")
	public String deletePost(Principal principal, HttpServletRequest request) {
		Integer postId = Integer.valueOf(request.getParameter("postId"));
		User user = userService.findByUsername(principal.getName());
		Post post = postService.findById(postId);
		String page = request.getParameter("page");
		if (post != null && post.getUser().equals(user)) {
			postService.deleteById(postId);
		}
		return "redirect:/blogger/" + page;
	}

}
