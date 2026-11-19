package br.edu.ifpb.ifgram.service;

import br.edu.ifpb.ifgram.DTO.CommentRequest;
import br.edu.ifpb.ifgram.DTO.CommentResponse;
import br.edu.ifpb.ifgram.model.Comment;
import br.edu.ifpb.ifgram.model.Post;
import br.edu.ifpb.ifgram.model.User;
import br.edu.ifpb.ifgram.repository.CommentRepository;
import br.edu.ifpb.ifgram.repository.PostRepository;
import br.edu.ifpb.ifgram.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentService(
            CommentRepository commentRepository,
            PostRepository postRepository,
            UserRepository userRepository
    ) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }
    @Transactional
    public CommentResponse criar(CommentRequest request) {

        if (request.texto() == null || request.texto().isBlank()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "O texto do comentário não pode estar vazio."
            );
        }
        Post post = postRepository.findById(request.postId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Publicação não encontrada."
                ));
        User autor = userRepository.findById(request.autorId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Usuário autor não encontrado."
                ));
        Comment comment = new Comment(
                request.texto(),
                post,
                autor
        );
        Comment commentSalvo = commentRepository.save(comment);
        return CommentResponse.from(commentSalvo);
    }
    public List<CommentResponse> listarPorPost(Long postId) {
        return commentRepository
                .findByPostIdOrderByCriadoEmAsc(postId)
                .stream()
                .map(CommentResponse::from)
                .toList();
    }
    @Transactional
    public void deletar(Long id) {
        if (!commentRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Comentário não encontrado."
            );
        }
        commentRepository.deleteById(id);
    }
}
