package example.ecommerce.website.repositories;

import example.ecommerce.website.model.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    Category findByName( @NotBlank @Size(min=5,message="name should be atleast 5 chara") String name);
}
