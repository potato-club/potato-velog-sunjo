package com.velog.controller.board;

import com.velog.config.security.PrincipalDetails;
import com.velog.controller.ApiResponse;
import com.velog.dto.boardComment.request.BoardCommentRequest;
import com.velog.dto.boardComment.response.BoardCommentInfoResponse;
import com.velog.service.board.BoardCommentService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class BoardCommentController {

    private final BoardCommentService boardCommentService;

    @ApiOperation(value = "게시글 댓글 생성", notes = "게시글 댓글 생성")
    @PostMapping("/api/v1/board/comment")
    public ApiResponse<BoardCommentInfoResponse> createBoardComment(@RequestBody @Valid BoardCommentRequest.CreateBoardComment request, @AuthenticationPrincipal PrincipalDetails principalDetails) {
        return ApiResponse.success(boardCommentService.createBoardComment(request, principalDetails.getMember()));
    }

}
