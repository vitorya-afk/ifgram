package br.edu.ifpb.ifgram.service;

import br.edu.ifpb.ifgram.DTO.FollowRequest;
import br.edu.ifpb.ifgram.DTO.FollowResponse;
import br.edu.ifpb.ifgram.model.Follow;
import br.edu.ifpb.ifgram.model.User;
import br.edu.ifpb.ifgram.repository.FollowRepository;
import br.edu.ifpb.ifgram.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public FollowService(
            FollowRepository followRepository,
            UserRepository userRepository
    ) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
    }
    @Transactional
    public FollowResponse seguir(FollowRequest request) {
        if (request.seguidorId().equals(request.seguidoId())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Um usuário não pode seguir a si mesmo."
            );
        }
        User seguidor = userRepository.findById(request.seguidorId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "O usuário que deseja seguir não foi encontrado."
                ));
        User seguido = userRepository.findById(request.seguidoId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "O usuário que será seguido não foi encontrado."
                ));
        if (followRepository.existsBySeguidorIdAndSeguidoId(
                request.seguidorId(), request.seguidoId()
        )) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Este usuário já está seguindo essa pessoa."
            );
        }
        Follow follow = new Follow(seguidor, seguido);
        Follow followSalvo = followRepository.save(follow);
        return FollowResponse.from(followSalvo);
    }
    @Transactional
    public void deixarDeSeguir(Long seguidorId, Long seguidoId) {
        Follow follow = followRepository
                .findBySeguidorIdAndSeguidoId(seguidorId, seguidoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Relacionamento de seguir não encontrado."
                ));
        followRepository.delete(follow);
    }
    public List<FollowResponse> listarSeguidores(Long userId) {
        verificarUsuario(userId);
        return followRepository.findBySeguidoId(userId)
                .stream()
                .map(FollowResponse::from)
                .collect(Collectors.toList());
    }
    public List<FollowResponse> listarSeguindo(Long userId) {
        verificarUsuario(userId);
        return followRepository.findBySeguidorId(userId)
                .stream()
                .map(FollowResponse::from)
                .collect(Collectors.toList());
    }
    public long contarSeguidores(Long userId) {
        verificarUsuario(userId);
        return followRepository.countBySeguidoId(userId);
    }
    public long contarSeguindo(Long userId) {
        verificarUsuario(userId);
        return followRepository.countBySeguidorId(userId);
    }
    private void verificarUsuario(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Usuário não encontrado."
            );
        }
    }
}