package com.github.youssefwadie.bugtracker.ticket.comment;

import com.github.youssefwadie.bugtracker.dto.mappers.TicketCommentMapper;
import com.github.youssefwadie.bugtracker.dto.model.TicketCommentDto;
import com.github.youssefwadie.bugtracker.model.TicketComment;
import com.github.youssefwadie.bugtracker.security.UserContextHolder;
import com.github.youssefwadie.bugtracker.util.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.github.youssefwadie.bugtracker.constants.ResponseConstants.TOTAL_COUNT_HEADER_NAME;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/tickets")
public class TicketCommentController {
    private final TicketCommentService commentService;

    private final TicketCommentMapper commentMapper;

    @GetMapping("/{ticketId:[1-9]\\d*}/comments/{pageNumber:[1-9]\\d*}")
    public ResponseEntity<List<TicketCommentDto>> listByPage(
            @PathVariable("ticketId") Long ticketId,
            @PathVariable("pageNumber") Integer pageNumber) {

        Page<TicketComment> ticketsPage = commentService.findAllByTicket(ticketId, pageNumber - 1);
        return ResponseEntity.status(HttpStatus.OK)
                .header(TOTAL_COUNT_HEADER_NAME, Long.toString(ticketsPage.getTotalElements()))
                .body(commentMapper.commentsToCommentsDTOs(ticketsPage.getContent()));
    }

    @PutMapping("/comments")
    public ResponseEntity<Object> editComment(@RequestBody TicketCommentDto ticketCommentDto) {
        if (ticketCommentDto.getId() == null) {
            return ResponseUtils.badRequest("id is missing.");
        }
        final TicketComment comment = commentMapper.commentDtoToComment(ticketCommentDto);
        comment.setCommenter(UserContextHolder.get());
        TicketComment savedComment = commentService.save(comment);
        return ResponseEntity.ok(commentMapper.commentToCommentDto(savedComment));
    }

    @PostMapping(value = "/{ticketId:[1-9]\\d*}/comments", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createComment(@RequestBody TicketCommentDto ticketCommentDto,
                                                @PathVariable("ticketId") Long ticketId) {
        final TicketComment comment = commentMapper.commentDtoToComment(ticketCommentDto);
        comment.setCommenter(UserContextHolder.get());
        comment.setTicketId(ticketId);

        TicketComment savedComment = commentService.save(comment);
        return ResponseEntity.ok(commentMapper.commentToCommentDto(savedComment));
    }
}
