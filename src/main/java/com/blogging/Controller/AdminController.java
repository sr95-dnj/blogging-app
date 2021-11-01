package com.blogging.Controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.blogging.constant.BlogConstant;
import com.blogging.entity.Post;
import com.blogging.entity.User;
import com.blogging.service.PostService;
import com.blogging.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private UserService userService;

	@Autowired
	private PostService postService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@GetMapping("/dashboard")
	public String dashboard() {
		return "admin/admin-dashboard";
	}

	@GetMapping("/approvedusers")
	public String approvedUsers(Model model) {
		List<User> users = userService.findByIsApproved(BlogConstant.APPROVED_STATUS);
		model.addAttribute("user", new User());
		model.addAttribute("users", users);
		return "admin/view-users";
	}

	@PostMapping("/adduser")
	public String addUser(@Valid User user, BindingResult bindingResult) {
		if (!bindingResult.hasErrors()) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			user.setIsApproved(BlogConstant.APPROVED_STATUS);
			user.setTime(LocalDateTime.now());
			userService.save(user);
		}
		return "redirect:/admin/approvedusers";
	}

	@GetMapping("deactivateuser")
	public String deactivateUser(@RequestParam("id") Integer id) {
		User user = userService.findById(id);
		if (user != null) {
			user.setIsApproved(BlogConstant.PENDING_STATUS);
			userService.save(user);
		}
		return "redirect:/admin/approvedusers";
	}

	@GetMapping("/pendingusers")
	public String viewPendingUsers(Model model) {
		List<User> users = userService.findByIsApproved(BlogConstant.PENDING_STATUS);
		model.addAttribute("users", users);
		return "admin/view-pending-users";
	}

	@GetMapping("/approveuser")
	public String approveUser(@RequestParam("id") Integer id) {
		User user = userService.findById(id);
		if (user != null) {
			user.setIsApproved(BlogConstant.APPROVED_STATUS);
			user.setTime(LocalDateTime.now());
			userService.save(user);
		}
		return "redirect:/admin/pendingusers";
	}

	@GetMapping("/rejectuser")
	public String rejectUser(@RequestParam("id") Integer id) {
		User user = userService.findById(id);
		if (user != null) {
			userService.delete(user);
		}
		return "redirect:/admin/pendingusers";
	}

	@GetMapping("/pendingposts")
	public String viewPendingPosts(Model model) {
		List<Post> posts = postService.findByIsApproved(BlogConstant.PENDING_STATUS);
		model.addAttribute("posts", posts);
		return "admin/pending-posts";
	}

	@GetMapping("/approvedposts")
	public String viewApprovedPosts(Model model) {
		List<Post> posts = postService.findByIsApproved(BlogConstant.APPROVED_STATUS);
		model.addAttribute("posts", posts);
		return "admin/approved-posts";
	}

	@GetMapping("/deletepost")
	public String deletePost(@RequestParam("id") Integer id) {
		Post post = postService.findById(id);
		if (post != null) {
			postService.deleteById(id);
		}
		return "redirect:/admin/pendingposts";
	}

	@GetMapping("/approvepost")
	public String approvePost(@RequestParam("id") Integer id) {
		Post post = postService.findById(id);
		if (post != null) {
			post.setIsApproved(BlogConstant.APPROVED_STATUS);
			postService.save(post);
		}
		return "redirect:/admin/pendingposts";
	}

}
