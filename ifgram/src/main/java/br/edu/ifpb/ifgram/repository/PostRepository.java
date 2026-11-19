package br.edu.ifpb.ifgram.repository;

import br.edu.ifpb.ifgram.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAutorId(Long autorId);
    List<Post> findByLegendaContainingIgnoreCase(String trecho);

    long countByAutorId(Long autorId);
    @Query("SELECT p FROM Post p ORDER BY p.criadoEm DESC")
    List<Post> listarFeed();
    @Query("""
    select p from Post p
    where p.autor.id = :userId
       or p.autor.id in (
           select f.seguido.id
           from Follow f
           where f.seguidor.id = :userId
       )
    order by p.criadoEm desc
""")
    List<Post> buscarFeedDoUsuario(@Param("userId") Long userId);

}
