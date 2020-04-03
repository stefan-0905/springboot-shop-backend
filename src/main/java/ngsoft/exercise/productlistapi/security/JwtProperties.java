package ngsoft.exercise.productlistapi.security;

public class JwtProperties {
    public static final String SECRET = "ProductList123";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final int EXPIRATION_TIME = 864000000;
}
