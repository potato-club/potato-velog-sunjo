package com.velog.service.board;

import com.velog.domain.board.Board;
import com.velog.domain.board.Series;
import com.velog.domain.board.repository.BoardRepository;
import com.velog.domain.member.Member;
import com.velog.domain.member.repository.MemberRepository;
import com.velog.dto.board.response.BoardInfoWithHashTagResponse;
import com.velog.dto.board.response.BoardRetrieveResponse;
import com.velog.enumData.BoardPeriod;
import com.velog.dto.board.request.BoardRequest;
import com.velog.dto.board.response.BoardInfoResponse;
import com.velog.dto.board.response.SeriesResponse;
import com.velog.exception.NotFoundException;
import com.velog.service.member.MemberServiceUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public SeriesResponse createSeries(BoardRequest.CreateSeries request, String email) {
        Member member = MemberServiceUtils.findMemberByEmail(memberRepository, email);
        Series series = member.addSeries(request.getSeriesName());
        return SeriesResponse.of(series.getId(), series.getSeriesName());
    }

    @Transactional
    public BoardInfoResponse createBoard(BoardRequest.CreateBoard request, Long memberId) {
        Board board = boardRepository.save(request.toEntity(memberId));
        board.addHashTag(request.getHashTagList(), memberId);
        return BoardInfoResponse.of(board);
    }

    @Transactional(readOnly = true)
    public List<BoardRetrieveResponse> retrieveBoard(Long lastBoardId, int size, BoardPeriod period) {
        return boardRepository.findAllBoardByOrderByIdDescAndTerm(lastBoardId, size, period);
    }

    @Transactional
    public void boardLike(Long boardId, Long memberId) {
        Board board = boardRepository.findBoardById(boardId)
                .orElseThrow(() -> new NotFoundException(String.format("???????????? ?????? ????????? %s?????????.", boardId)));
        board.boardAddLike(memberId);
    }

    @Transactional
    public void boardUnLike(Long boardId, Long memberId) {
        Board board = boardRepository.findBoardById(boardId)
                .orElseThrow(() -> new NotFoundException(String.format("???????????? ?????? ????????? %s?????????.", boardId)));
        board.boardUnLike(memberId);
    }

    @Transactional
    public BoardInfoWithHashTagResponse getBoard(Long boardId) {
        boardRepository.findBoardById(boardId)
                .orElseThrow(() -> new NotFoundException(String.format("???????????? ?????? ????????? %s?????????.", boardId)));
        Board boardWithHashTag = boardRepository.getBoardWithHashTag(boardId);
        Member member = memberRepository.findMemberById(boardWithHashTag.getMemberId())
                .orElseThrow(() -> new NotFoundException(String.format("%s??? ???????????? ?????? ???????????????.", boardWithHashTag.getMemberId())));
        return BoardInfoWithHashTagResponse.of(boardWithHashTag, member);
    }

}
