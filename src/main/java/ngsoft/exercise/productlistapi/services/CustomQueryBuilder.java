package ngsoft.exercise.productlistapi.services;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import ngsoft.exercise.productlistapi.documents.*;
import ngsoft.exercise.productlistapi.repositories.BrandsRepository;
import ngsoft.exercise.productlistapi.repositories.CategoriesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;
import java.util.List;

@Service
public class CustomQueryBuilder {

    public CustomQueryBuilder(CategoriesRepository categoriesRepository, BrandsRepository brandsRepository) {
        CustomQueryBuilder.categoriesRepository = categoriesRepository;
        CustomQueryBuilder.brandsRepository = brandsRepository;
    }

    public static CategoriesRepository categoriesRepository;
    public static BrandsRepository brandsRepository;

    public static BooleanBuilder build(String category, String brands, String sizes, String between) throws HttpClientErrorException {
        QProduct qProduct = new QProduct("product");

        BooleanBuilder queryBuilder = new BooleanBuilder();

        if(category != null && !category.equals("")) {
            QCategory qCategory = new QCategory("category");
            BooleanExpression filterByName = qCategory.name.equalsIgnoreCase(category);
            List<Category> categories = (List<Category>) categoriesRepository.findAll(filterByName);

            // throw notFound HttpClientErrorException if there is no category found
            if(categories.size() == 0) {
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "There is no product with specified category");
            }

            // append category predicate
            queryBuilder.and(qProduct.category.eq(categories.get(0)));
        }

        if(brands != null && !brands.equals("")) {
            BooleanBuilder subQueryBuilder = new BooleanBuilder();
            QBrand qBrand = new QBrand("brand");
            // create array out of string brands by spliting it on "," delimiter
            String[] stringBrands = brands.split(",");
            // loop through them and see if they exist in db. If yes add them to sub query
            for (String stringBrand : stringBrands) {
                BooleanExpression filterByTitle = qBrand.title.equalsIgnoreCase(stringBrand);
                // find brand from db
                List<Brand> foundBrand = (List<Brand>) brandsRepository.findAll(filterByTitle);
                if(foundBrand.size() > 0) {
                    subQueryBuilder.or(qProduct.brand.eq(foundBrand.get(0)));
                }
            }
            // throw notFound HttpClientErrorException if there is no brands found
            if(subQueryBuilder.getValue() == null) {
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "There is no product with specified brands");
            }

            // append brands predicate
            queryBuilder.and(subQueryBuilder);
        }

        if(sizes != null && !sizes.equals("")) {
            BooleanBuilder subQueryBuilder = new BooleanBuilder();
            // create array out of string sizes by spliting it on "," delimiter
            List<String> stringSizes = Arrays.asList(sizes.split(","));

            stringSizes.forEach(size -> subQueryBuilder.or(qProduct.sizes.contains(size)));

            // throw notFound HttpClientErrorException if there is no size found
            if(subQueryBuilder.getValue() == null) {
                throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "There is no product with specified sizes");
            }

            // append sizes predicate
            queryBuilder.and(subQueryBuilder);
        }

        if(between != null) {
            String[] params = between.split(",");
            // if 2 params weren't specified throw BadRequest HttpClientErrorException
            if(params.length != 2) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Between parameter needs to have 2 numbers separated by comma");
            }

            queryBuilder.and(qProduct.price.between(Integer.parseInt(params[0]), Integer.parseInt(params[1])));
        }

        return queryBuilder;
    }
}
