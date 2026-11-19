package br.edu.ifpb.ifgram.DTO;

import jakarta.validation.constraints.NotNull;

public record LikeRequest(
        @NotNull
        Long postId,

        @NotNull
        Long autorId
) {
}
