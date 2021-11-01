package com.blogging.Controller;

import java.security.Principal;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.blogging.constant.BlogConstant;
import com.blogging.entity.User;
import com.blogging.security.service.CustomUserDetailsService;
import com.blogging.service.UserService;

@Controller
public class HomeController {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserService userService;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	private static Logger logger = LoggerFactory.getLogger(HomeController.class);

	@GetMapping("/")
	public String home(Principal principal) {
		if (principal != null) {
			User user = userService.findByUsername(principal.getName());
			if (user.getRole().equals(BlogConstant.ROLE_ADMIN)) {
				return "redirect:/admin/dashboard";
			} else if (user.getRole().equals(BlogConstant.ROLE_BLOGGER)) {
				return "redirect:/blogger/newsfeed";
			}
		}
		return "/login";
	}

	@GetMapping("/login")
	public String showLoginPage() {
		return "login";
	}

	@GetMapping("/registration")
	public String showSignupPage(Model theModel) {
		theModel.addAttribute("user", new User());
		return "registration";
	}

	@PostMapping("/registration")
	public String createUser(@Valid User user, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("msg", "You entered invalid data..!!");
		} else {
			User userExists = userService.findByUsername(user.getUsername());
			if (userExists != null) {
				model.addAttribute("msg", "This username already exist..!!");
			} else {
				user.setUsername(user.getUsername().trim());
				user.setPassword(passwordEncoder.encode(user.getPassword().trim()));
				user.setRole(BlogConstant.ROLE_BLOGGER);
				user.setIsApproved(BlogConstant.PENDING_STATUS);
				userService.save(user);
				model.addAttribute("msg", "User Registration Successfull & Sent For Admin Approval..!!");
				model.addAttribute("user", new User());
			}
		}
		return "registration";
	}

	@GetMapping("/profile")
	public String profile(Principal principal, Model model) {
		User user = userService.findByUsername(principal.getName());
		model.addAttribute(user);
		return "profile";
	}

	@PostMapping("/profile")
	public String profile(Principal principal, @Valid User newUser, Model model, BindingResult bindingResult) {
		User user = userService.findByUsername(principal.getName());
		String msg = "";
		if (bindingResult.hasErrors()) {
			msg = "You entered invalid data..!!";
		} else {
			User userExists = userService.findByUsername(newUser.getUsername());
			if (userExists != null && !user.getId().equals(userExists.getId())) {
				msg = "This username already exist..!!";
			} else {
				user.setUsername(newUser.getUsername().trim());
				user.setPassword(passwordEncoder.encode(newUser.getPassword()));
				user.setEmail(newUser.getEmail());
				userService.save(user);
				msg = "Profile Update Successfull..!!!!!!";
				updateUserData(user);
			}
		}
		model.addAttribute("msg", msg);
		model.addAttribute("user", user);
		return "profile";
	}

	public void updateUserData(User user) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(),
				userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	@GetMapping("/access-denied")
	public String accessDenied() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			logger.info(String.format("User '%s' attempted to access the protected URL", auth.getName()));
		}
		return "access-denied";
	}
}
