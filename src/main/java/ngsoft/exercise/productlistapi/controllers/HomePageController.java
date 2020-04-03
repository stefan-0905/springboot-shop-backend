package ngsoft.exercise.productlistapi.controllers;

import ngsoft.exercise.productlistapi.documents.Brand;
import ngsoft.exercise.productlistapi.documents.Category;
import ngsoft.exercise.productlistapi.documents.Product;
import ngsoft.exercise.productlistapi.repositories.BrandsRepository;
import ngsoft.exercise.productlistapi.repositories.CategoriesRepository;
import ngsoft.exercise.productlistapi.repositories.ProductsRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/home")
public class HomePageController {
    private ProductsRepository productsRepository;
    private BrandsRepository brandsRepository;
    private CategoriesRepository categoriesRepository;

    public HomePageController(ProductsRepository productsRepository, BrandsRepository brandsRepository, CategoriesRepository categoriesRepository) {
        this.productsRepository = productsRepository;
        this.brandsRepository = brandsRepository;
        this.categoriesRepository = categoriesRepository;
    }

    @GetMapping
    public ResponseEntity<Object> getNecessaryData() {
        int productsPerPage = 2;
        Pageable pageableRequest = PageRequest.of(0, productsPerPage);

        List<Brand> myBrands = this.brandsRepository.findAll();
        List<Category> myCategories = this.categoriesRepository.findAll();
        List<Product> myProducts = this.productsRepository.findAll(pageableRequest).getContent();

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("brands", myBrands);
        responseData.put("categories", myCategories);
        responseData.put("products", myProducts);

        return ResponseEntity.ok(responseData);
    }
}
