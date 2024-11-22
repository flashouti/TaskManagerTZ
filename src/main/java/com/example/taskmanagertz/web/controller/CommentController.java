package com.example.taskmanagertz.web.controller;

import com.example.taskmanagertz.domain.user.User;
import com.example.taskmanagertz.services.CommentService;
import com.example.taskmanagertz.web.dto.requests.comment.CommentCreateRequest;
import com.example.taskmanagertz.web.dto.responses.comment.CommentFullResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/taskManager/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public CommentFullResponse createComment(@AuthenticationPrincipal User user,
                                             @RequestBody @Validated CommentCreateRequest request) {
        return commentService.addComment(request, user);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteComment(@AuthenticationPrincipal User user,
                                           @PathVariable Long id){
        commentService.deleteComment(id, user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
