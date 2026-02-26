package com.cdac.knowledgehub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cdac.knowledgehub.model.Article;


@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query("""
    SELECT DISTINCT a FROM Article a
    LEFT JOIN a.tags t
    WHERE (:keyword IS NULL OR
           lower(a.title) LIKE lower(concat('%', :keyword, '%'))
           OR lower(a.content) LIKE lower(concat('%', :keyword, '%'))
           OR lower(t.name) LIKE lower(concat('%', :keyword, '%')))
    AND (:category IS NULL OR a.category = :category)
    """)
    List<Article> search(String keyword, String category);
}
