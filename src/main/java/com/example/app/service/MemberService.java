package com.example.app.service;

import java.util.List;

import com.example.app.domain.Member;
import com.example.app.domain.MemberType;

public interface MemberService {
	List<Member> getMemberList() throws Exception;

	Member getMemberById(Integer id) throws Exception;

	List<Member> getMemberListByPage(int page, int numPerPage) throws Exception;

	int getTotalPages(int numPerPage) throws Exception;

	void addMember(Member member) throws Exception;

	void editMember(Member member) throws Exception;

	void deleteMember(Integer id) throws Exception;

	List<MemberType> getTypeList() throws Exception;
}