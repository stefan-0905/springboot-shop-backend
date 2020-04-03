package ngsoft.exercise.productlistapi.services;

import com.querydsl.core.types.dsl.BooleanExpression;
import ngsoft.exercise.productlistapi.documents.Brand;
import ngsoft.exercise.productlistapi.documents.Category;
import ngsoft.exercise.productlistapi.documents.Product;
import ngsoft.exercise.productlistapi.documents.QBrand;
import ngsoft.exercise.productlistapi.repositories.BrandsRepository;
import ngsoft.exercise.productlistapi.repositories.CategoriesRepository;
import ngsoft.exercise.productlistapi.repositories.ProductsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ProductCreator {

    static ProductsRepository productsRepository;
    static CategoriesRepository categoriesRepository;
    static BrandsRepository brandsRepository;

    public ProductCreator(ProductsRepository products, CategoriesRepository categories, BrandsRepository brands) {
        productsRepository = products;
        categoriesRepository = categories;
        brandsRepository = brands;
    }

    public static Product create(String title, Double price, String sizes, String imagePath, String categoryId, String brandName) throws HttpClientErrorException {
        Product product;

        Category category;

        Optional<Category> optionalCategory = categoriesRepository.findById(categoryId);
        if(optionalCategory.isPresent()) {
            category = optionalCategory.get();
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "Category was not found");
        }

        Brand brand;
        QBrand qBrand = new QBrand("brand");
        BooleanExpression booleanExpression = qBrand.title.equalsIgnoreCase(brandName);
        List<Brand> brands = (List<Brand>)brandsRepository.findAll(booleanExpression);
        if(brands.isEmpty()) {
            brand = new Brand(brandName, "");
            brandsRepository.insert(brand);
        } else {
            brand = brands.get(0);
        }

        product = new Product(title, price, Arrays.asList(sizes.split(",")), imagePath, category, brand);
        productsRepository.insert(product);

        return product;
    }
}
