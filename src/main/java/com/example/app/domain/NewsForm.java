package com.example.app.domain;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NewsForm {

	@NotBlank
	private String title;
	private String author;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate postDate;
	// NewsDetail 
	@NotBlank
	private String article;

	// お知らせの対象リスト(会員種別の ID リスト)
	private List<Integer> targetIdList;

	// 画像のアップロード
	private MultipartFile upfile;

	public NewsForm() {
		this.postDate = LocalDate.now();
	}

}
