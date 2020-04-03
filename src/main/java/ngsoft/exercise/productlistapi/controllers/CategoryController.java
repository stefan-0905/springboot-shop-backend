package ngsoft.exercise.productlistapi.controllers;

import ngsoft.exercise.productlistapi.documents.Category;
import ngsoft.exercise.productlistapi.repositories.CategoriesRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private CategoriesRepository categoriesRepository;

    public CategoryController(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    @GetMapping
    public ResponseEntity<Object> index() {
        return ResponseEntity.ok(this.categoriesRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> show(@PathVariable String id) {
        return ResponseEntity.ok(this.categoriesRepository.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> destroy(@PathVariable("id") String id) {
        if(!this.categoriesRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        this.categoriesRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Object> store(@RequestBody @Valid Category category, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        try {
            this.categoriesRepository.insert(category);

            return ResponseEntity.created(URI.create("localhost:8080/api/category")).body(category);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex);
        }
    }
}
