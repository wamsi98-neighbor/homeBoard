package com.study.board.controller;

import com.study.board.entity.Board;
import com.study.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


// SB가 컨트롤러임을 인식하게 해주는 어노테이션

@RequestMapping("/board")
@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    /* 페이지네이션 https://wikidocs.net/162028#_1
    * 1. BoardRepository에 Pageable타입을 return하는 메소드 생성
    * 2. Service의 boardList() 메소드는 정수타입의 페이지 번호를 입력받고, 페이지 질문 목록을 return하는 메소드
    *   2-1. Pageable 객체를 생성하며 사용한 PageRequest.of(page, 10)은 한 페이지에 보여줄 게시물의 갯수를 의미한다.
    *   2-2. 2-1의 장점은, 데이터 전체를 조회하지 않고, 해당 페이지의 데이터만 조회하도록 쿼리가 변한다?
    * 3. http://localhost:8080/board/list?page=0 처럼 GET방식 요청 URL을 위해서
    *   3-1. @RequestParam(value = "page", defaultValue = "0") int page 사용 --> page가 전달 안되면 default는 0이 된다.
    *   3-2. Page<Board> 타입으로 받은 paging을 Model에 담아 뷰로 전달
    * 4. Page객체의 속성들
    *   4-1. paging.isEmpty	페이지 존재 여부 (게시물이 있으면 false, 없으면 true), paging.size	페이지당 보여줄 게시물 개수
    *   paging.number	현재 페이지 번호, paging.hasPrevious	이전 페이지 존재 여부, paging.hasNext	다음 페이지 존재 여부
    * 5. View에서 게시글 리스트 attribute를 paging으로 변경
    * */

    // 받은 data들을 다시 View로 보내주려면 Model에 담아야한다.
    @GetMapping("/list")
    public String boardList(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        // Service의 return 타입이 Page<Board>다
        Page<Board> paging = boardService.boardList(page);
        model.addAttribute("paging", paging);

        return "boardlist";
    }

    @GetMapping("/write")    // localhost:8080/이라는 요청이 들어오면 처리해줄 어노테이션
    public String boardWriteForm()  {
        return "boardwrite";
    }

    @PostMapping("/writepro")
    public String boardWritePro (Board board){      //Board로 받는 이유는, 여러 값들을 넘겨받기 위해사. 어차피 여기서 받는거와 Board와 구조 동일

        boardService.write(board);

        return "";
    }

    @GetMapping("/view")
    public String boardView(Model model, Integer id){       // 이 id는 ?id= 로 넘겨 받은 값

        model.addAttribute("board", boardService.boardView(id));
        return "boardview";
    }

    // 삭제 요청시 글id를 받고, View로 넘겨줄 Model 생성
    @GetMapping("/delete")
    public String boardDelete(Model model, Integer id){
        
        // 서비스에 글번호를 넘겨줌
        boardService.boardDelete(id);
        return "redirect:/board/list";      // 삭제 후 바로 리스트로 보내준다
    }

    // @PathVariable을 쓰면, queryString 대신 깔끔하게 int형 id가 들어옴
    @GetMapping("/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, Model model){

        model.addAttribute("update", boardService.boardView(id));
        return "boardmodify";
    }


    @PostMapping("/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id,  Board board){    // 새로 저장받은 Board, form 태그에 /update/{id} 요청하면서 저장되어있우ㅡㅁ

        // 기존 게시글(객체)의 내용을 불러옴
        // 기존의 내용에 새로온 내용을 덮어씀
        Board boardTmp = boardService.boardView(id);

        boardTmp.setTitle(board.getTitle());
        boardTmp.setContent(board.getContent());

        boardService.boardUpdate(boardTmp); 
        return "redirect:/board/list";
    }
}
