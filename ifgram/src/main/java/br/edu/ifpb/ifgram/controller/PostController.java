package br.edu.ifpb.ifgram.controller;

import br.edu.ifpb.ifgram.DTO.PostRequest;
import br.edu.ifpb.ifgram.DTO.PostResponse;
import br.edu.ifpb.ifgram.service.PostService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("post")
public class PostController {

    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }
    @GetMapping
    public List<PostResponse> listarFeed() {
        return service.listarFeed();
    }
    @GetMapping("/{id}")
    public PostResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }
    @GetMapping("/autor/{autorId}")
    public List<PostResponse> listarPorAutor(@PathVariable Long autorId) {
        return service.listarPorAutor(autorId);
    }
    @PostMapping
    public PostResponse criar(@Valid @RequestBody PostRequest request) {
        return service.criar(request);
    }
    @DeleteMapping("/{id}")
    public String deletar(@PathVariable Long id) {
        service.deletar(id);
        return "Publicação excluída com sucesso!";
    }
    @GetMapping("/feed/{userId}")
    public List<PostResponse> listarFeedDoUsuario(@PathVariable Long userId) {
        return service.listarFeedDoUsuario(userId);}
}
