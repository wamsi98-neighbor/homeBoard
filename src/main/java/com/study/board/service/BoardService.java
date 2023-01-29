package com.study.board.service;

import com.study.board.entity.Board;
import com.study.board.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    // 글 작성은 return이 필요 없기 때문에 void
    public void write(Board board){
        boardRepository.save(board);
    }

    // 게시글 리스트는 list타입, 하지만 페이지네이션 떄는 Page 타입이다
    public Page<Board> boardList(int page)  {   // Board를 담고 있는 List

        Pageable pageable = PageRequest.of(page, 10);
        // BoardRepositoty 인터페이스에 findAll 메소드 생성
        return boardRepository.findAll(pageable);
    }

    // 게시판 상세보기는 게시글 내용들을 return 하므로 Board 타입
    public Board boardView(Integer id){
        
        return boardRepository.findById(id).get();
    }
    
    // 게시글삭제
    public void boardDelete(Integer id){

        // boardRepository에서 글번호를 갖고 글삭제
        boardRepository.deleteById(id);
    }

    // 글번호는 당연히 필요하고, 게시글에 대한 내용이기 때문에 Board로 받아감
    public Board boardUpdate (Board board){

        return boardRepository.save(board);
    }

}
