package br.edu.ifpb.ifgram.controller;

import br.edu.ifpb.ifgram.DTO.CommentRequest;
import br.edu.ifpb.ifgram.DTO.CommentResponse;
import br.edu.ifpb.ifgram.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("comment")
public class CommentController {

    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }
    @PostMapping
    public CommentResponse criar(
            @Valid @RequestBody CommentRequest request
    ) {
        return service.criar(request);
    }
    @GetMapping("/post/{postId}")
    public List<CommentResponse> listarPorPost(@PathVariable Long postId) {
        return service.listarPorPost(postId);
    }
    @DeleteMapping("/{id}")
    public String deletar(@PathVariable Long id) {
        service.deletar(id);
        return "Comentário excluído com sucesso!";
    }
}
