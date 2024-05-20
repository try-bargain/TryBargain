package com.project.trybargain.global.util.scheduler;

import com.project.trybargain.domain.board.entity.Board;
import com.project.trybargain.domain.board.repository.BoardLikeRepository;
import com.project.trybargain.domain.board.repository.BoardRepository;
import com.project.trybargain.domain.board.service.BoardService;
import com.project.trybargain.global.redis.RedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final BoardService boardService;
    private final BoardLikeRepository boardLikeRepository;
    private final RedisRepository redisRepository;

    @Scheduled(cron = "10 * * * * *")
    @Transactional
    public void boardLikeUpdate() {
        boardService.boardLikeUpdate();
    }
}
