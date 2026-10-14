package br.edu.ifpb.ifgram.controller;
import java.util.List;

import br.edu.ifpb.ifgram.model.User;
import br.edu.ifpb.ifgram.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import br.edu.ifpb.ifgram.service.UserService;
import br.edu.ifpb.ifgram.DTO.UserRequest;
import br.edu.ifpb.ifgram.DTO.UserResponse;

@RestController
@RequestMapping("user")
public class UserController {

    public final UserService service;

    public UserController(UserService service){
        this.service = service;
    }

    @GetMapping
    public List<UserResponse> getUsers(){
        List<UserResponse> listaUsuarios = service.buscarTodosUsuarios();
        return listaUsuarios;
    }

    @PostMapping
    public UserResponse postUser(UserRequest request) throws Exception {
        UserResponse userResponse = service.criar(request);
        return userResponse;
    }

    @DeleteMapping
    public String deleteUser(){
        return "chamei o endpoint como um DELETE!";
    }

    @PutMapping
    public UserResponse UpdateUser (UserRequest request){
        return service.atualizarUsuario(request);
    }
}