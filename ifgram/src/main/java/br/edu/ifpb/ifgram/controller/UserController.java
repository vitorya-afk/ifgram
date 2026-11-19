package br.edu.ifpb.ifgram.controller;

import br.edu.ifpb.ifgram.DTO.UserRequest;
import br.edu.ifpb.ifgram.DTO.UserResponse;
import br.edu.ifpb.ifgram.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }
    @GetMapping
    public List<UserResponse> getUsers() {
        return service.buscarTodosUsuarios();
    }
    @PostMapping
    public UserResponse postUser(@Valid @RequestBody UserRequest request) throws Exception {
        return service.criar(request);
    }
    @DeleteMapping
    public String deleteUser(@Valid @RequestBody UserRequest request) throws Exception {
        service.deletarUsuario(request);
        return "Usuário deletado com sucesso!";
    }
    @PutMapping
    public UserResponse updateUser(@Valid @RequestBody UserRequest request) throws Exception {
        return service.atualizarUsuario(request);
    }
}
