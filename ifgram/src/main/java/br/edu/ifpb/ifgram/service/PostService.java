package br.edu.ifpb.ifgram.service;

import br.edu.ifpb.ifgram.DTO.PostRequest;
import br.edu.ifpb.ifgram.DTO.PostResponse;
import br.edu.ifpb.ifgram.model.Post;
import br.edu.ifpb.ifgram.model.User;
import br.edu.ifpb.ifgram.repository.PostRepository;
import br.edu.ifpb.ifgram.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(
            PostRepository postRepository,
            UserRepository userRepository
    ) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }
    @Transactional
    public PostResponse criar(PostRequest request) {
        User autor = userRepository.findById(request.autorId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuário autor não encontrado."
                ));
        Post post = new Post(
                request.imagemUrl(),
                request.legenda(),
                autor
        );
        Post postSalvo = postRepository.save(post);
        return PostResponse.from(postSalvo);
    }
    public List<PostResponse> listarFeed() {
        return postRepository.listarFeed()
                .stream()
                .map(PostResponse::from)
                .toList();
    }
    public List<PostResponse> listarPorAutor(Long autorId) {
        return postRepository.findByAutorId(autorId)
                .stream()
                .map(PostResponse::from)
                .toList();
    }
    public PostResponse buscarPorId(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Publicação não encontrada."
                ));
        return PostResponse.from(post);
    }
    @Transactional
    public void deletar(Long id) {
        if (!postRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Publicação não encontrada."
            );
        }
        postRepository.deleteById(id);
    }
    public List<PostResponse> listarFeedDoUsuario(Long userId) {
        PostRepository repository = null;
        if (!repository.existsById(userId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado.");
        }
        return repository.buscarFeedDoUsuario(userId)
                .stream()
                .map(PostResponse::from)
                .collect(Collectors.toList());
    }

}
