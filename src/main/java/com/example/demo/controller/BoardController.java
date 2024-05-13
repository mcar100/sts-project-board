package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.Board;
import com.example.demo.model.Comment;
import com.example.demo.model.FileDTO;
import com.example.demo.service.BoardService;
import com.example.demo.service.CommentService;
import com.example.demo.service.FileService;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	BoardService boardService;
	@Autowired
	FileService fileService;
	@Autowired
	CommentService commentService;
	
	@RequestMapping
    public String goBoard(HttpServletRequest request) throws Exception {
    	return "/page/board";
    }

	@PostMapping
	@ResponseBody
	public Integer createBoard(@RequestBody Board board, HttpServletRequest request) throws Exception{		
		try {
			if(board==null) {
				throw new Exception("요청된 정보가 없습니다.");
			}
			
			HttpSession session = request.getSession(false);
			Integer userId = (Integer)session.getAttribute("userId");
			if(userId==null) {
				throw new Exception("Session 에러");
			}
			board.setUserId(userId);
			
			boolean result = boardService.insertBoardInfo(board);
			if(!result) {
				throw new Exception("Board Service 에러");
			}
			return board.getId();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	@GetMapping("/detail/{boardId}")
    public String goBoardDetail(@PathVariable("boardId") Integer boardId, Model model) throws Exception {
		try {
			if(boardId==null) {
				throw new Exception("요청된 정보가 없습니다.");
			}
			
			// 게시글 정보 가져오기
			HashMap<String,Object> boardInfo = boardService.getBoardDetailInfo(boardId);

			if(boardInfo==null) {
				throw new Exception("Board Service 에러");
			}
			else if(!boardInfo.get("boardStatus").equals("PUBLIC")) {
				throw new Exception("삭제 혹은 비공개된 글입니다.");
			}
			model.addAttribute("boardData", boardInfo);
			
			// 파일정보 가져오기
			FileDTO[] filesInfo = fileService.getFileInfo(boardId);
			if(filesInfo!=null) {
				model.addAttribute("filesData",filesInfo);
			}
			
			// 댓글정보 가져오기
			ArrayList<Comment> commentsInfo = commentService.getCommentList(boardId);
			if(commentsInfo!=null) {
				model.addAttribute("commentsData", commentsInfo);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			model.addAttribute("error", true);
			model.addAttribute("errorMsg", e.getMessage());
		}
    	return "/page/detail";
    }
	
	@DeleteMapping("/detail/{boardId}")
    @ResponseBody
	public boolean deleteBoard(@PathVariable("boardId") Integer boardId) throws Exception {
		try {
			if(boardId==null) {
				throw new Exception("요청된 정보가 없습니다.");
			}
			boolean result = boardService.deleteBoardInfo(boardId);
			if(!result) {
				throw new Exception("Board Service 에러");
			}
			return true;
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
    }
	
	@DeleteMapping("/detail/admin/{boardId}")
    @ResponseBody
	public boolean deleteHardBoard(@PathVariable("boardId") Integer boardId) throws Exception {
		try {
			if(boardId==null) {
				throw new Exception("요청된 정보가 없습니다.");
			}
			boolean fileResult = fileService.deleteFileInfo(boardId);
			if(!fileResult) {
				throw new Exception("File Service 에러");
			}
			boolean boardResult = boardService.deleteHardBoardInfo(boardId);
			if(!boardResult) {
				throw new Exception("Board Service 에러");
			}
			return true;
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
    }
	
	@GetMapping("/modify/{boardId}")
	public String goModify(@PathVariable("boardId") Integer boardId, HttpServletRequest request, Model model) throws Exception{
		try {
			if(boardId==null) {
				throw new Exception("요청된 정보가 없습니다.");
			}
			
			HashMap<String,Object> boardInfo = boardService.getBoardDetailInfo(boardId);
			if(boardInfo==null) {
				throw new Exception("Board Service 에러");
			}
			String boardUserId = String.valueOf(boardInfo.get("userId"));
			
			HttpSession session = request.getSession(false);
			String sessionUserId = String.valueOf(session.getAttribute("userId"));
			
			if(!boardUserId.equals(sessionUserId)) {
				throw new Exception("게시물 작성자가 아닙니다.");
			}
			
			model.addAttribute("boardData", boardInfo);
			model.addAttribute("commentsData", null);
			
			// 파일정보 가져오기
			FileDTO[] filesInfo = fileService.getFileInfo(boardId);
			if(filesInfo!=null) {
				model.addAttribute("filesData",filesInfo);
			}
			
			return "/page/board";
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return "redirect:/";
		}
	}
	
	@PutMapping("/modify/{boardId}")
	@ResponseBody
	public boolean updateBoard(@PathVariable("boardId") Integer boardId, @RequestBody Board board) {
		try {
			if(boardId==null || board==null) {
				throw new Exception("요청된 정보가 없습니다.");
			}
			board.setId(boardId);
			boolean result = boardService.updateBoardInfo(board);
			if(!result) {
				throw new Exception("Board Service 에러");
			}
			return true;
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
}
