package com.velog.domain.board;

import com.velog.domain.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class BoardLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    private Long memberId;

    public BoardLike(Board board, Long memberId) {
        this.board = board;
        this.memberId = memberId;
    }

    public static BoardLike of(Board board, Long memberId) {
        return new BoardLike(board, memberId);
    }

    public boolean findMember(Long memberId) {
        return this.memberId.equals(memberId);
    }

}
