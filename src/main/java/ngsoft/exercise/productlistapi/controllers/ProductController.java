package ngsoft.exercise.productlistapi.controllers;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import ngsoft.exercise.productlistapi.documents.Brand;
import ngsoft.exercise.productlistapi.documents.Category;
import ngsoft.exercise.productlistapi.documents.Product;
import ngsoft.exercise.productlistapi.repositories.BrandsRepository;
import ngsoft.exercise.productlistapi.repositories.CategoriesRepository;
import ngsoft.exercise.productlistapi.repositories.ProductsRepository;
import ngsoft.exercise.productlistapi.services.CustomQueryBuilder;
import ngsoft.exercise.productlistapi.services.ImageService;
import ngsoft.exercise.productlistapi.services.ProductCreator;
import ngsoft.exercise.productlistapi.services.storage.StorageService;
import org.apache.coyote.Response;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private static Integer productsPerPage = 2;

    private ProductsRepository productsRepository;
    private CategoriesRepository categoriesRepository;
    private BrandsRepository brandsRepository;
    private StorageService storageService;

    public ProductController(ProductsRepository productsRepository, CategoriesRepository categoriesRepository, BrandsRepository brandsRepository, StorageService storageService) {
        this.productsRepository = productsRepository;
        this.brandsRepository = brandsRepository;
        this.categoriesRepository = categoriesRepository;
        this.storageService = storageService;
    }

    @GetMapping
    public ResponseEntity<Object> index(@RequestParam(required = false, defaultValue = "0") Integer page) {
        Pageable pageableRequest = PageRequest.of(page, productsPerPage);
        return ResponseEntity.ok(this.productsRepository.findAll(pageableRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> show(@PathVariable("id") String id) {
        if(!this.productsRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(this.productsRepository.findById(id));
    }

    @GetMapping("/filter")
    public ResponseEntity<Object> showFiltered(@RequestParam(required = false) String category,
                                               @RequestParam(required = false) String brands,
                                               @RequestParam(required = false) String sizes,
                                               @RequestParam(required = false) String between,
                                               @RequestParam(required = false, defaultValue = "0") Integer page) {
        Pageable pageableRequest = PageRequest.of(page, productsPerPage);

        // if non specified no need for building query, just return casual response with all the products
        if(category == null && brands == null && sizes == null && between == null) {
            return ResponseEntity.ok(this.productsRepository.findAll(pageableRequest));
        }

        try {
            BooleanBuilder builtQuery = CustomQueryBuilder.build(category, brands, sizes, between);
            return ResponseEntity.ok(this.productsRepository.findAll(builtQuery, pageableRequest));
        } catch (HttpClientErrorException ex) {
            Map<String, String> data = new HashMap<>();
            data.put("error", ex.getMessage());
            return new ResponseEntity<>(data, ex.getStatusCode());
        }
    }

    @PostMapping
    public ResponseEntity<Object> store(@RequestParam("image") MultipartFile file,
                                        @RequestParam String title,
                                        @RequestParam Double price,
                                        @RequestParam String categoryId,
                                        @RequestParam String sizes,
                                        @RequestParam String brand
                                        ) {

        this.storageService.store(file);

        System.out.println(categoryId);

        Product newProduct = ProductCreator.create(title, price, sizes, file.getName(), categoryId, brand);

        return ResponseEntity.ok(newProduct);
    }

    @GetMapping(path = "/{id}/image")
    public ResponseEntity<Object> getImage(@PathVariable("id") String id) {
        try {
            if(!this.productsRepository.findById(id).isPresent()) {
                return ResponseEntity.badRequest().body("There is no product with specified id");
            }

            byte[] imageBytes = ImageService.fetchByProductId(id);

            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
        } catch (IOException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
