package com.example.app.service;

import java.io.File;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.app.domain.News;
import com.example.app.domain.NewsDetail;
import com.example.app.domain.NewsForm;
import com.example.app.mapper.NewsDetailMapper;
import com.example.app.mapper.NewsMapper;
import com.example.app.mapper.NewsTargetMapper;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
	private final NewsMapper newsMapper;
	private final NewsDetailMapper newsDetailMapper;
	private final NewsTargetMapper newsTargetMapper;

	@Override
	public List<News> getNewsList() throws Exception {
		return newsMapper.selectAll();
	}

	//NewsService に個別のお知らせを取得するメソッド
	@Override
	public News getNewsById(Integer id) throws Exception {
		return newsMapper.selectById(id);
	}

	/* NewsService／NewsServiceImpl に、
	 * お知らせ追加フォームから受け取ったデータ(NewsFormオブジェクト)を元に、
	 * news と news_details テーブルにデータを追加するメソッドを定義している。
	 */
	@Override
	public void addNews(NewsForm formData) throws Exception {
		// news テーブルへの追加
		News news = new News();
		news.setTitle(formData.getTitle());
		news.setAuthor(formData.getAuthor());
		news.setPostDate(formData.getPostDate());
		newsMapper.insert(news); // news に id がセットされる
		// news_details テーブルへの追加
		NewsDetail detail = new NewsDetail();
		detail.setNewsId(news.getId());
		detail.setArticle(formData.getArticle());

		// 画像が選択されている場合の処理
		MultipartFile upfile = formData.getUpfile();
		if (!upfile.isEmpty()) {
			String photo = upfile.getOriginalFilename();
			// news_details テーブルへ格納するための画像名をセット
			detail.setPhoto(photo);
			// 画像ファイルの保存
			ResourceBundle bundle = ResourceBundle.getBundle("application");
			String directory = bundle.getString("upload.directory");
			File dest = new File(directory + photo);
			upfile.transferTo(dest);
		}
		newsDetailMapper.insert(detail);

		// news_targets テーブルへの追加
		for (Integer targetId : formData.getTargetIdList()) {
			newsTargetMapper.insert(news.getId(), targetId);
		}
	}
}