package com.next.infod.repositories;

import com.next.infod.Enums.GeneroLivro;
import com.next.infod.model.BooksModel;
import com.next.infod.model.Livro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID>, JpaSpecificationExecutor<Livro> {

    Page<Livro> findByBooks(BooksModel model, Pageable pageable); // Corrigido para refletir o nome do campo

    // Query Method
    // select * from livro where id_autor = id
    List<Livro> findByBooks(BooksModel autor); // Corrigido para refletir o relacionamento correto

    // select * from livro where titulo = titulo
    Optional<Livro> findByTitulo(String titulo);

    Optional<Livro> findByIsbnAndTituloAndGeneroAndPreco(String isbn, String titulo, GeneroLivro genero, BigDecimal preco);

    // select * from livro where isbn = ?
    Optional<Livro> findByIsbn(String isbn);

    // select * from livro where titulo = ? and preco = ?
    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    // select * from livro where titulo = ? or isbn = ?
    List<Livro> findByTituloOrIsbnOrderByTitulo(String titulo, String isbn);

    // select * from livro where data_publicacao between ? and ?
    List<Livro> findByDataPublicacaoBetween(LocalDate inicio, LocalDate fim);

    // JPQL -> referencia as entidades e as propriedades
    // select l.* from livro as l order by l.titulo
    @Query("select l from Livro l order by l.titulo, l.preco")
    List<Livro> listarTodosOrdenadoPorTituloAndPreco();

    /**
     * select a.*
     * from livro l
     * join autor a on a.id = l.id_autor
     */
    @Query("select b from Livro l join l.books b") // Ajustado para refletir o relacionamento correto com "books"
    List<BooksModel> listarAutoresDosLivros();

    // select distinct l.* from livro l
    @Query("select distinct l.titulo from Livro l")
    List<String> listarNomesDiferentesLivros();

    @Query("""
        select l.genero
        from Livro l
        join l.books b
        where b.nationality = 'Brasileira'
        order by l.genero
    """)
    List<String> listarGenerosAutoresBrasileiros();

    // named parameters -> parametros nomeados
    @Query("select l from Livro l where l.genero = :genero order by :paramOrdenacao ")
    List<Livro> findByGenero(
            @Param("genero") GeneroLivro generoLivro,
            @Param("paramOrdenacao") String nomePropriedade
    );

    // positional parameters
    @Query("select l from Livro l where l.genero = ?2 order by ?1 ")
    List<Livro> findByGeneroPositionalParameters(String nomePropriedade, GeneroLivro generoLivro);

    @Modifying
    @Transactional
    @Query("delete from Livro l where l.genero = ?1") // Corrigido para refletir a referência correta
    void deleteByGenero(GeneroLivro genero);

    @Modifying
    @Transactional
    @Query("update Livro l set l.dataPublicacao = ?1")
    void updateDataPublicacao(LocalDate novaData);

    boolean existsByBooks(BooksModel model); // Corrigido para refletir o nome correto do relacionamento
}
