package ngsoft.exercise.productlistapi.seeders;

import ngsoft.exercise.productlistapi.documents.Brand;
import ngsoft.exercise.productlistapi.documents.Category;
import ngsoft.exercise.productlistapi.documents.Product;
import ngsoft.exercise.productlistapi.repositories.BrandsRepository;
import ngsoft.exercise.productlistapi.repositories.CategoriesRepository;
import ngsoft.exercise.productlistapi.repositories.ProductsRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ProductSeeder implements CommandLineRunner {

    private ProductsRepository productsRepository;
    private CategoriesRepository categoriesRepository;
    private BrandsRepository brandsRepository;

    public ProductSeeder(ProductsRepository productsRepository, CategoriesRepository categoriesRepository, BrandsRepository brandsRepository) {
        this.productsRepository = productsRepository;
        this.categoriesRepository = categoriesRepository;
        this.brandsRepository = brandsRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Category man = new Category("Man");
        Category woman = new Category("Woman");
        Category children = new Category("Children");
        Brand adidas = new Brand("Adidas", "Herzogenaurach, Germany,");
        Brand nike = new Brand("Nike", "Beaverton, US,");
        Brand rebook = new Brand("Rebook", "Beaverton, US,");
        Brand china = new Brand("China", "Beaverton, US,");

        Product tShirt = new Product("Batman T-Shirt", 99.0,
                Arrays.asList("S", "L"),
                "https://svgsilh.com/svg_v2/306168.svg",
                man,
                nike
                );
        Product shorts = new Product("Aquaman shorts", 19.0,
                Arrays.asList("XL", "XXL"),
                "https://p1.pxfuel.com/preview/953/604/979/jeans-denim-fashion-pants.jpg",
                man,
                adidas
                );
        Product pants = new Product("Cowboy hat", 67.0,
                Arrays.asList("XS", "M"),
                "https://live.staticflickr.com/5057/5542684970_48ae9686f6_b.jpg",
                man,
                rebook
        );
        Product blueShirt = new Product("Superman T-Shirt", 244.0,
                Arrays.asList("S", "M"),
                "https://svgsilh.com/svg_v2/306168.svg",
                man,
                china
        );
        Product whiteShirt = new Product("Superman White T-Shirt", 222.0,
                Arrays.asList("S", "XL"),
                "https://svgsilh.com/svg_v2/306168.svg",
                man,
                adidas
        );
        Product greenShirt = new Product("Ninja T-Shirt", 23.0,
                Arrays.asList("L", "M"),
                "https://svgsilh.com/svg_v2/306168.svg",
                woman,
                nike
        );
        Product socks = new Product("Superman Socks", 5.0,
                Arrays.asList("S", "M"),
                "https://live.staticflickr.com/65535/48725844306_acc667bb01_c.jpg",
                man,
                nike
        );
        Product mask = new Product("Batman Mask", 39.0,
                Arrays.asList("XS", "XXL"),
                "https://svgsilh.com/svg_v2/306168.svg",
                children,
                nike
        );
        Product underwear = new Product("Ninja Underwear", 159.0,
                Arrays.asList("L", "S"),
                "https://svgsilh.com/svg_v2/306168.svg",
                man,
                china
        );

        this.categoriesRepository.deleteAll();
        this.brandsRepository.deleteAll();
        this.productsRepository.deleteAll();

        this.categoriesRepository.insert(Arrays.asList(man, woman, children));
        this.brandsRepository.insert(Arrays.asList(adidas, nike, rebook, china));
        this.productsRepository.insert(Arrays.asList(tShirt, shorts, pants, blueShirt, whiteShirt, greenShirt, underwear, mask, socks));

    }
}
