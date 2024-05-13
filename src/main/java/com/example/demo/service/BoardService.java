package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.demo.model.Board;
import com.example.demo.model.SearchDTO;

public interface BoardService {
	boolean insertBoardInfo(Board board) throws Exception;
	int countBoardList() throws Exception;
	ArrayList<HashMap<String, Object>> getBoardListInfo(SearchDTO searchDTO) throws Exception;
	HashMap<String,Object> getBoardDetailInfo(Integer boardId) throws Exception;
	boolean updateBoardInfo(Board board) throws Exception;
	boolean deleteBoardInfo(Integer boardId) throws Exception;
	boolean deleteHardBoardInfo(Integer boardId) throws Exception;
}
