package com.example.app.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Admin {
	private Integer id;
	@NotBlank
	private String loginId;
	private String loginPass;

}
