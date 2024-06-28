package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.domain.Member;
import com.example.app.service.MemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
	// 1 ページ当たりの表示人数
	private static final int NUM_PER_PAGE = 5;

	private final MemberService service;

	@GetMapping
	public String list(
			@RequestParam(name = "page", defaultValue = "1") Integer page,
			Model model) throws Exception {
		model.addAttribute("members",
				service.getMemberListByPage(page, NUM_PER_PAGE));
		model.addAttribute("page", page);
		model.addAttribute("totalPages",
				service.getTotalPages(NUM_PER_PAGE));
		return "members/list";
	}

	@GetMapping("/add")
	public String addGet(Model model) throws Exception {
		model.addAttribute("title", "会員の追加");
		model.addAttribute("member", new Member());
		model.addAttribute("types", service.getTypeList());
		return "members/save";
	}

	@PostMapping("/add")
	public String addPost(
			@Valid Member member,
			Errors errors,
			RedirectAttributes rd,
			Model model) throws Exception {
		if (errors.hasErrors()) {
			model.addAttribute("title", "会員の追加");
			model.addAttribute("types", service.getTypeList());
			return "members/save";
		}
		service.addMember(member);
		rd.addFlashAttribute("statusMessage", "会員を追加しました。");
		return "redirect:/members";
	}

	@GetMapping("/edit/{id}")
	public String editGet(@PathVariable Integer id, Model model) throws Exception {
		model.addAttribute("title", "会員情報の変更");
		model.addAttribute("member", service.getMemberById(id));
		model.addAttribute("types", service.getTypeList());
		return "members/save";
	}

	@PostMapping("/edit/{id}")
	public String editPost(
			@PathVariable Integer id,
			@Valid Member member,
			Errors errors,
			RedirectAttributes rd,
			Model model) throws Exception {
		if (errors.hasErrors()) {
			model.addAttribute("title", "会員情報の変更");
			model.addAttribute("types", service.getTypeList());
			return "members/save";
		}
		member.setId(id); //更新に必要な会員 ID をセット
		service.editMember(member);
		rd.addFlashAttribute("statusMessage", "会員情報を更新しました。");
		return "redirect:/members";
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable Integer id, RedirectAttributes rd)
			throws Exception {
		service.deleteMember(id);
		rd.addFlashAttribute("statusMessage", "会員情報を削除しました。");
		return "redirect:/members";
	}
}