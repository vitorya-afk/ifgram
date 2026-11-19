package br.edu.ifpb.ifgram.DTO;

import jakarta.validation.constraints.NotNull;

public record FollowRequest(
        @NotNull
        Long seguidorId,

        @NotNull
        Long seguidoId
) {
}
