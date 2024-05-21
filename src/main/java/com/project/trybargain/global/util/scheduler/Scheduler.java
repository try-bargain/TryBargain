package com.project.trybargain.global.util.scheduler;

import com.project.trybargain.domain.board.service.BoardService;
import com.project.trybargain.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class Scheduler {

    private final BoardService boardService;
    private final CommentService commentService;

    @Scheduled(cron = "0 0/5 * * * *")
    @Transactional
    public void boardLikeUpdate() {
        log.info("게시글 좋아요 업데이트");
        boardService.boardLikeUpdate();
        log.info("댓글 좋아요 업데이트");
        commentService.commentLikeUpdate();
    }
}
