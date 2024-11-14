package com.ecommerce.demo.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ecommerce.demo.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    
    // Find subcategories by parent ID
    @Query("""
        SELECT c FROM Category c 
        WHERE c.parentCategory.categoriesId = :parentId
    """)
    Set<Category> findSubcategoriesByParentId(@Param("parentId") Integer parentId);

    // Find all parent categories (categories with no parent)
    @Query("SELECT c FROM Category c WHERE c.parentCategory IS NULL")
    List<Category> findAllParentCategories();

    // Find category with all its products
    @Query("""
        SELECT DISTINCT c FROM Category c 
        LEFT JOIN FETCH c.products 
        WHERE c.categoriesId = :id
    """)
    Optional<Category> findByIdWithProducts(@Param("id") Integer id);

    // Find category with subcategories
    @Query("""
        SELECT DISTINCT c FROM Category c 
        LEFT JOIN FETCH c.subCategories 
        WHERE c.categoriesId = :id
    """)
    Optional<Category> findByIdWithSubcategories(@Param("id") Integer id);

    // Find by category name (case insensitive)
    @Query("SELECT c FROM Category c WHERE LOWER(c.categoryName) = LOWER(:name)")
    Optional<Category> findByCategoryName(@Param("name") String name);

    // Check if category name exists
    @Query("SELECT COUNT(c) > 0 FROM Category c WHERE LOWER(c.categoryName) = LOWER(:name)")
    boolean existsByCategoryName(@Param("name") String name);

    // Find complete category hierarchy
    @Query(value = """
        WITH RECURSIVE CategoryHierarchy AS (
            -- Base case: start with the category itself
            SELECT 
                c.categories_id,
                c.category_name,
                c.parent_categoryid,
                0 as level
            FROM category c
            WHERE c.categories_id = :categoryId
            
            UNION ALL
            
            -- Recursive case: join with subcategories
            SELECT 
                child.categories_id,
                child.category_name,
                child.parent_categoryid,
                ch.level + 1
            FROM category child
            INNER JOIN CategoryHierarchy ch ON child.parent_categoryid = ch.categories_id
        )
        SELECT * FROM CategoryHierarchy
        ORDER BY level ASC
    """, nativeQuery = true)
    List<Object[]> findCategoryHierarchy(@Param("categoryId") Integer categoryId);

    // Find categories with products
    @Query("""
        SELECT c FROM Category c 
        WHERE EXISTS (
            SELECT 1 FROM Product p 
            WHERE p.category = c
        )
    """)
    List<Category> findCategoriesWithProducts();

    // Find categories by parent ID with product count
    @Query("""
        SELECT c, COUNT(p) 
        FROM Category c 
        LEFT JOIN c.products p 
        WHERE c.parentCategory.categoriesId = :parentId 
        GROUP BY c
    """)
    List<Object[]> findSubcategoriesWithProductCount(@Param("parentId") Integer parentId);

    // Find leaf categories (categories with no subcategories)
    @Query("""
        SELECT c FROM Category c 
        WHERE NOT EXISTS (
            SELECT 1 FROM Category sub 
            WHERE sub.parentCategory = c
        )
    """)
    List<Category> findLeafCategories();

    // Search categories by name pattern
    @Query("""
        SELECT c FROM Category c 
        WHERE LOWER(c.categoryName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))
        ORDER BY c.categoryName
    """)
    List<Category> searchCategories(@Param("searchTerm") String searchTerm);
}