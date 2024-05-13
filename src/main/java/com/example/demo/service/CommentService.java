package com.example.demo.service;

import java.util.ArrayList;

import com.example.demo.model.Comment;

public interface CommentService {
	 boolean insertCommentInfo(Comment comment) throws Exception;
	 ArrayList<Comment> getCommentList(Integer boardId) throws Exception;
	 boolean updateCommentInfo(Comment comment) throws Exception;
	 boolean deleteCommentInfo(Integer commentId) throws Exception;
}
