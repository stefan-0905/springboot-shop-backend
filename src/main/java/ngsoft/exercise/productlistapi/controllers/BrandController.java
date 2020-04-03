package ngsoft.exercise.productlistapi.controllers;

import ngsoft.exercise.productlistapi.documents.Brand;
import ngsoft.exercise.productlistapi.repositories.BrandsRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/brand")
public class BrandController {

    private BrandsRepository brandsRepository;

    public BrandController(BrandsRepository brandsRepository) {
        this.brandsRepository = brandsRepository;
    }

    @GetMapping
    public ResponseEntity<Object> index() {
        return ResponseEntity.ok(this.brandsRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> show(@PathVariable String id) {
        return ResponseEntity.ok(this.brandsRepository.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> destroy(@PathVariable("id") String id) {
        if(!this.brandsRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        this.brandsRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Object> store(@RequestBody @Valid Brand brand, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getAllErrors());
        }

        try {
            this.brandsRepository.insert(brand);

            return ResponseEntity.created(URI.create("localhost:8080/api/brand")).body(brand);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex);
        }
    }

    @PostMapping
    public ResponseEntity<Object> update(@RequestBody Brand brand) {
        if (!this.brandsRepository.existsById(brand.getId())) {
            return ResponseEntity.notFound().build();
        }

        this.brandsRepository.save(brand);
        return ResponseEntity.ok(brand);
    }
}
