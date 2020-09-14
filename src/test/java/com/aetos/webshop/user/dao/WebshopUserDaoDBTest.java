package com.aetos.webshop.user.dao;

import com.aetos.webshop.product.dao.ProductDao;
import com.aetos.webshop.product.exception.ProductNotFoundException;
import com.aetos.webshop.product.model.Product;
import com.aetos.webshop.product.repository.ProductRepository;
import com.aetos.webshop.user.exception.UserNotFoundException;
import com.aetos.webshop.user.model.WebshopUser;
import com.aetos.webshop.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@DataJpaTest
@ActiveProfiles("test")
public class WebshopUserDaoDBTest {

    @Autowired
    private TestEntityManager entityManager;

    private UserDao userDao;

    @Autowired
    private UserRepository userRepository;

    private WebshopUser user1;
    private WebshopUser user2;
    private WebshopUser admin;

    private ProductDao productDaoMock;

    @Autowired
    private ProductRepository productRepository;

    private Product product1;
    private Product product2;
    private Product product3;

    @BeforeEach
    void setUp() {
        productDaoMock = Mockito.mock(ProductDao.class);
        userDao = new UserDaoDB(userRepository, productDaoMock);
        initialiseUsers();
        initialiseProducts();
    }

    private void initialiseUsers() {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        user1 = WebshopUser.builder()
                .email("user1@gmail.com")
                .hashedPassword(passwordEncoder.encode("user1"))
                .firstName("István")
                .lastName("Nagy")
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

        user2 = WebshopUser.builder()
                .email("user2@gmail.com")
                .hashedPassword(passwordEncoder.encode("user2"))
                .firstName("László")
                .lastName("Kis")
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

        admin = WebshopUser.builder()
                .email("admin@gmail.com")
                .hashedPassword(passwordEncoder.encode("admin"))
                .firstName("Admin")
                .lastName("Admin")
                .roles(Arrays.asList("ROLE_USER", "ROLE_ADMIN"))
                .build();
    }

    private void initialiseProducts() {
        product1 = Product.builder()
                .name("Lego Train station")
                .description("A great lego")
                .imageUrl("https://www.lego.com/cdn/cs/set/assets/blt3e446b6889283ae1/60050_alt1.jpg?fit=bounds&format=jpg&quality=80&width=320&height=320&dpr=1")
                .category("Lego")
                .price(new BigDecimal(13000))
                .quantityOnStock(2)
                .build();

        product2 = Product.builder()
                .name("Football")
                .description("A great football")
                .imageUrl("https://scoresports.com/media/catalog/product/cache/1/small_image/1710x1980/9df78eab33525d08d6e5fb8d27136e95/b/a/ball-wht.jpg")
                .category("Sport")
                .price(new BigDecimal(4000))
                .quantityOnStock(1)
                .build();

        product3 = Product.builder()
                .name("Lego")
                .description("A great lego tractor")
                .imageUrl("https://sportszert.hu/i.php?f=/images/products/lego_60181.jpg")
                .category("Lego")
                .price(new BigDecimal(20000))
                .quantityOnStock(0)
                .build();

        productRepository.save(product1);
        productRepository.save(product2);
        productRepository.save(product3);
    }

    @Test
    void saveOneSimple() {
        userDao.addUser(user1);
        assertThat(userDao.getAll()).hasSize(1);
    }

    @Test
    void getById() throws UserNotFoundException {

        userDao.addUser(user1);
        userDao.addUser(user2);
        userDao.addUser(admin);

        assertThat(userDao.getById(user2.getUserId()).getUserId()).isNotNull();
    }

    @Test
    void getNonExistingUser() {
        assertThrows(UserNotFoundException.class, () -> userDao.getById(330L));
    }

    @Test
    void updateUser() throws UserNotFoundException {
        userDao.addUser(user1);
        Long userId = user1.getUserId();

        WebshopUser updatedUser = WebshopUser.builder()
                .firstName("updated First name")
                .lastName("Nagy")
                .build();
        userDao.updateUser(userId, updatedUser);

        assertThat(userDao.getById(userId).getFirstName()).isEqualTo("updated First name");
    }

    @Test
    void updateNonExistingUser() {
        assertThrows(UserNotFoundException.class, () -> userDao.updateUser(330L, user1));
    }

    @Test
    void deleteUser() throws UserNotFoundException {
        userDao.addUser(user1);
        userDao.addUser(user2);
        userDao.addUser(admin);
        Long userId = admin.getUserId();

        userDao.deleteUser(userId);
        assertThat(userDao.getAll()).hasSize(2);
        assertThrows(UserNotFoundException.class, () -> userDao.getById(userId));
    }

    @Test
    void deleteNonExistingUser() {
        assertThrows(UserNotFoundException.class, () -> userDao.deleteUser(230L));
    }

    @Test
    void getCart() throws UserNotFoundException, ProductNotFoundException {
        userDao.addUser(user1);
        Long userId = user1.getUserId();

        when(productDaoMock.getById(product1.getProductId())).thenReturn(product1);
        when(productDaoMock.getById(product2.getProductId())).thenReturn(product2);
        when(productDaoMock.getById(product3.getProductId())).thenReturn(product3);

        userDao.addToCart(userId, product1.getProductId(), 1);
        userDao.addToCart(userId, product2.getProductId(), 2);
        userDao.addToCart(userId, product3.getProductId(), 3);

        assertThat(userDao.getCart(userId)).hasSize(3);
        assertThat(userDao.getById(userId).getCart().get(product1)).isEqualTo(1);
    }

    @Test
    void getCartOfNonExistingUser() {
        assertThrows(UserNotFoundException.class, () -> userDao.getCart(800L));
    }

    @Test
    void addToCart() throws UserNotFoundException, ProductNotFoundException {
        userDao.addUser(user1);
        Long userId = user1.getUserId();
        Long productId = product1.getProductId();
        when(productDaoMock.getById(productId)).thenReturn(product1);

        entityManager.flush();

        userDao.addToCart(userId, productId, 2);
        assertThat(userDao.getCart(userId).get(product1)).isEqualTo(2);
        userDao.addToCart(userId, productId, 3);
        assertThat(userDao.getCart(userId).get(product1)).isEqualTo(5);
    }

    @Test
    void addToCartOfNonExistingUser() throws ProductNotFoundException {
        Long productId = product1.getProductId();
        when(productDaoMock.getById(productId)).thenReturn(product1);

        assertThrows(UserNotFoundException.class,
                () -> userDao.addToCart(990L, productId, 10));
    }

    @Test
    void addToCartNonExistingProduct() throws ProductNotFoundException {
        userDao.addUser(user1);
        when(productDaoMock.getById(8800L)).thenThrow(ProductNotFoundException.class);
        assertThrows(ProductNotFoundException.class,
                () -> userDao.addToCart(user1.getUserId(), 8800L, 10));
    }

    @Test
    void removeOneFromCart() throws UserNotFoundException, ProductNotFoundException {
        userDao.addUser(user1);
        Long userId = user1.getUserId();
        Long productId = product1.getProductId();
        when(productDaoMock.getById(productId)).thenReturn(product1);
        userDao.addToCart(userId, productId, 3);

        userDao.removeOneFromCart(userId, productId);

        assertThat(userDao.getCart(userId).get(product1)).isEqualTo(2);
    }

    @Test
    void removeOneFromCartOfNonExistingUser() throws ProductNotFoundException {
        Long productId = product1.getProductId();
        when(productDaoMock.getById(productId)).thenReturn(product1);

        assertThrows(UserNotFoundException.class,
                () -> userDao.removeOneFromCart(4500L, productId));
    }

    @Test
    void removeOneFromCartNonExistingProduct() throws ProductNotFoundException {
        userDao.addUser(user1);
        when(productDaoMock.getById(40L)).thenThrow(ProductNotFoundException.class);

        assertThrows(ProductNotFoundException.class,
                () -> userDao.removeOneFromCart(user1.getUserId(), 40L));
    }

    @Test
    void removeProductFromCart() throws ProductNotFoundException, UserNotFoundException {
        userDao.addUser(user1);
        Long userId = user1.getUserId();

        when(productDaoMock.getById(product1.getProductId())).thenReturn(product1);
        when(productDaoMock.getById(product2.getProductId())).thenReturn(product2);
        when(productDaoMock.getById(product3.getProductId())).thenReturn(product3);

        userDao.addToCart(userId, product1.getProductId(), 1);
        userDao.addToCart(userId, product2.getProductId(), 2);
        userDao.addToCart(userId, product3.getProductId(), 3);

        userDao.removeProductFromCart(userId, product2.getProductId());

        assertThat(userDao.getCart(userId)).hasSize(2);
        assertThat(userDao.getCart(userId).get(product2)).isNull();
    }

    @Test
    void removeProductFromCartOfNonExistingUser() throws ProductNotFoundException {
        Long productId = product1.getProductId();
        when(productDaoMock.getById(productId)).thenReturn(product1);

        assertThrows(UserNotFoundException.class,
                () -> userDao.removeProductFromCart(57000L, productId));
    }

    @Test
    void removeProductFromCartNonExistingProduct() throws ProductNotFoundException {
        userDao.addUser(user1);
        when(productDaoMock.getById(200L)).thenThrow(ProductNotFoundException.class);

        assertThrows(ProductNotFoundException.class,
                () -> userDao.removeProductFromCart(user1.getUserId(), 200L));
    }

    @Test
    void updateQuantityOfProductInCart() throws ProductNotFoundException, UserNotFoundException {
        userDao.addUser(user1);
        Long userId = user1.getUserId();
        Long productId = product1.getProductId();
        when(productDaoMock.getById(productId)).thenReturn(product1);
        userDao.addToCart(userId, productId, 12);

        userDao.updateQuantityOfProductInCart(userId, productId, 20);

        assertThat(userDao.getCart(userId).get(product1)).isEqualTo(20);
    }

    @Test
    void updateQuantityOfProductInCartOfNonExistingUser() throws ProductNotFoundException {
        Long productId = product1.getProductId();
        when(productDaoMock.getById(productId)).thenReturn(product1);

        assertThrows(UserNotFoundException.class,
                () -> userDao.updateQuantityOfProductInCart(4300L, productId, 30));
    }

    @Test
    void updateQuantityOfProductInCartOfNonExistingProduct() throws ProductNotFoundException {
        userDao.addUser(user1);
        when(productDaoMock.getById(40L)).thenThrow(ProductNotFoundException.class);

        assertThrows(ProductNotFoundException.class,
                () -> userDao.updateQuantityOfProductInCart(user1.getUserId(), 40L, 30));
    }

    @Test
    void clearCart() throws ProductNotFoundException, UserNotFoundException {
        userDao.addUser(user1);
        Long userId = user1.getUserId();

        when(productDaoMock.getById(product1.getProductId())).thenReturn(product1);
        when(productDaoMock.getById(product2.getProductId())).thenReturn(product2);
        when(productDaoMock.getById(product3.getProductId())).thenReturn(product3);

        userDao.addToCart(userId, product1.getProductId(), 1);
        userDao.addToCart(userId, product2.getProductId(), 2);
        userDao.addToCart(userId, product3.getProductId(), 3);

        userDao.clearCart(userId);

        assertThat(userDao.getCart(userId)).hasSize(0);
    }

    @Test
    void clearCartOfNonExistingUser() {
        assertThrows(UserNotFoundException.class, () -> userDao.clearCart(65000L));
    }

}
