package com.aetos.webshop.user.dao;

import com.aetos.webshop.product.dao.ProductDao;
import com.aetos.webshop.product.exception.ProductNotFoundException;
import com.aetos.webshop.product.model.Product;
import com.aetos.webshop.product.repository.ProductRepository;
import com.aetos.webshop.user.exception.UserNotFoundException;
import com.aetos.webshop.user.model.User;
import com.aetos.webshop.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@DataJpaTest
@ActiveProfiles("test")
public class UserDaoDBTest {

    private UserDao userDao;

    @Autowired
    private UserRepository userRepository;

    private User user1;
    private User user2;
    private User admin;

    private Map<Product, Integer> cart;

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
        initialiseProducts();
        InitialiseCart();
        initialiseUsers();
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

    private void InitialiseCart() {
        cart = new HashMap<>();
        cart.put(product1, 1);
        cart.put(product2, 2);
        cart.put(product3, 3);
    }

    private void initialiseUsers() {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        user1 = User.builder()
                .email("user1@gmail.com")
                .hashedPassword(passwordEncoder.encode("user1"))
                .firstName("István")
                .lastName("Nagy")
                .cart(cart)
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

        user2 = User.builder()
                .email("user2@gmail.com")
                .hashedPassword(passwordEncoder.encode("user2"))
                .firstName("László")
                .lastName("Kis")
                .roles(Collections.singletonList("ROLE_USER"))
                .build();

        admin = User.builder()
                .email("admin@gmail.com")
                .hashedPassword(passwordEncoder.encode("admin"))
                .firstName("Admin")
                .lastName("Admin")
                .roles(Arrays.asList("ROLE_USER", "ROLE_ADMIN"))
                .build();
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

        User updatedUser = User.builder()
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
    void getCart() throws UserNotFoundException {
        userDao.addUser(user1);
        Long userId = user1.getUserId();

        assertThat(userDao.getById(userId).getCart()).isEqualTo(cart);
    }

    @Test
    void getCartOfNonExistingUser() {
        assertThrows(UserNotFoundException.class, () -> userDao.getCart(800L));
    }

    @Test
    void addToCart() throws UserNotFoundException, ProductNotFoundException {
        userDao.addUser(user1);
        Long userId = user1.getUserId();

        Long productId = 1L;
        when(productDaoMock.getById(productId)).thenReturn(product1);
        userDao.addToCart(userId, productId, 2);

        assertThat(userDao.getCart(userId).get(product1)).isEqualTo(3);
    }

}
