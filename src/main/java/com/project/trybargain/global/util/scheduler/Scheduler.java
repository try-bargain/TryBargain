package com.project.trybargain.global.util.scheduler;

import com.project.trybargain.domain.board.service.BoardService;
import com.project.trybargain.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final BoardService boardService;
    private final CommentService commentService;

    @Scheduled(cron = "0/30 * * * * *")
    @Transactional
    public void boardLikeUpdate() {
//        boardService.boardLikeUpdate();
        commentService.commentLikeUpdate();
    }
}
