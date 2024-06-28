package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.domain.NewsForm;
import com.example.app.service.MemberService;
import com.example.app.service.NewsService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {
	private final NewsService newsService;
	private final MemberService memberService;

	@GetMapping
	public String list(Model model) throws Exception {
		model.addAttribute("newsList", newsService.getNewsList());
		return "news/list";
	}

	///news/ {id} という URL でお知らせを個別表示できるようにしている。
	@GetMapping("/{id}")
	public String detail(@PathVariable Integer id, Model model)
			throws Exception {
		model.addAttribute("news", newsService.getNewsById(id));
		return "news/detail";
	}

	@GetMapping("/add")
	public String addGet(Model model) throws Exception {
		model.addAttribute("memberTypeList", memberService.getTypeList());
		model.addAttribute("newsForm", new NewsForm());
		return "news/save";
	}

	@PostMapping("/add")
	public String addPost(
			HttpSession session,
			@Valid NewsForm newsForm,
			Errors errors,
			RedirectAttributes ra,
			Model model) throws Exception {
		// バリデーション
		MultipartFile upfile = newsForm.getUpfile();
		if (!upfile.isEmpty()) {
			// 画像か否か判定する
			String type = upfile.getContentType();
			if (!type.startsWith("image/")) {
				// 画像ではない場合、エラーメッセージを表示
				errors.rejectValue("upfile", "error.not_image_file");
			}
		}

		if (errors.hasErrors()) {
			model.addAttribute("memberTypeList",
					memberService.getTypeList());
			return "news/save";
		}
		// 投稿者名は管理者のログイン ID とする
		newsForm.setAuthor((String) session.getAttribute("loginId"));
		// データベースへ追加
		newsService.addNews(newsForm);

		ra.addFlashAttribute("statusMessage", "お知らせを追加しました。");
		return "redirect:/news";
	}
}