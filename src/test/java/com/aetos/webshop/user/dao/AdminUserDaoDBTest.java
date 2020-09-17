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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@DataJpaTest
@ActiveProfiles("test")
public class AdminUserDaoDBTest {

    private AdminUserDao adminUserDao;

    @Autowired
    private UserRepository userRepository;

    private WebshopUser user1;
    private WebshopUser user2;
    private WebshopUser admin;

    private PasswordEncoder passwordEncoder;

    private ProductDao productDaoMock;

    @Autowired
    private ProductRepository productRepository;

    private Product product1;
    private Product product2;
    private Product product3;

    @BeforeEach
    void setUp() {
        productDaoMock = Mockito.mock(ProductDao.class);
        adminUserDao = new AdminUserDaoDB(userRepository, productDaoMock);
        passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        initialiseUsers();
        initialiseProducts();
    }

    private void initialiseUsers() {

        user1 = WebshopUser.builder()
                .email("user1@gmail.com")
                .password(passwordEncoder.encode("user1"))
                .firstName("István")
                .lastName("Nagy")
                .roles(new ArrayList<>(Collections.singletonList("ROLE_USER")))
                .build();

        user2 = WebshopUser.builder()
                .email("user2@gmail.com")
                .password(passwordEncoder.encode("user2"))
                .firstName("László")
                .lastName("Kis")
                .roles(new ArrayList<>(Collections.singletonList("ROLE_USER")))
                .build();

        admin = WebshopUser.builder()
                .email("admin@gmail.com")
                .password(passwordEncoder.encode("admin"))
                .firstName("Admin")
                .lastName("Admin")
                .roles(new ArrayList<>(Arrays.asList("ROLE_USER", "ROLE_ADMIN")))
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
        adminUserDao.addUser(user1);
        assertThat(adminUserDao.getAll()).hasSize(1);
    }

    @Test
    void emailShouldNotBeNull() {
        WebshopUser user = WebshopUser.builder()
                .password(passwordEncoder.encode("user"))
                .firstName("Kis")
                .lastName("Imre")
                .roles(new ArrayList<>(Collections.singletonList("ROLE_USER")))
                .build();

        assertThrows(DataIntegrityViolationException.class, () -> adminUserDao.addUser(user));
    }

    @Test
    void passwordShouldNotBeNull() {
        WebshopUser user = WebshopUser.builder()
                .email("kis@gmail.com")
                .firstName("Kis")
                .lastName("Imre")
                .roles(new ArrayList<>(Collections.singletonList("ROLE_USER")))
                .build();

        assertThrows(DataIntegrityViolationException.class, () -> adminUserDao.addUser(user));
    }

    @Test
    void firstNameShouldNotBeNull() {
        WebshopUser user = WebshopUser.builder()
                .email("kis@gmail.com")
                .password(passwordEncoder.encode("user"))
                .lastName("Imre")
                .roles(new ArrayList<>(Collections.singletonList("ROLE_USER")))
                .build();

        assertThrows(DataIntegrityViolationException.class, () -> adminUserDao.addUser(user));
    }

    @Test
    void lastNameShouldNotBeNull() {
        WebshopUser user = WebshopUser.builder()
                .email("kis@gmail.com")
                .password(passwordEncoder.encode("user"))
                .firstName("Kis")
                .roles(new ArrayList<>(Collections.singletonList("ROLE_USER")))
                .build();

        assertThrows(DataIntegrityViolationException.class, () -> adminUserDao.addUser(user));
    }

    @Test
    void getById() throws UserNotFoundException {
        adminUserDao.addUser(user1);
        adminUserDao.addUser(user2);
        adminUserDao.addUser(admin);

        assertThat(adminUserDao.getById(user2.getUserId())).isEqualTo(user2);
    }

    @Test
    void getNonExistingUser() {
        assertThrows(UserNotFoundException.class, () -> adminUserDao.getById(330L));
    }

    @Test
    void getByEmail() throws UserNotFoundException {
        adminUserDao.addUser(user1);
        assertThat(adminUserDao.getByEmail("user1@gmail.com")).isEqualTo(user1);
    }

    @Test
    void getByEmailNonExistingUser() {
        assertThrows(UserNotFoundException.class, () -> adminUserDao.getByEmail("nouser@nouser.nouser"));
    }

    @Test
    void exists() {
        adminUserDao.addUser(user1);
        assertThat(adminUserDao.exists(user1.getEmail())).isTrue();
    }

    @Test
    void existsNonExistingUser() {
        assertThat(adminUserDao.exists("nouser@nouser.nouser")).isFalse();
    }

    @Test
    void updateUser() throws UserNotFoundException {
        adminUserDao.addUser(user1);
        Long userId = user1.getUserId();

        WebshopUser updatedUser = WebshopUser.builder()
                .firstName("updated First name")
                .lastName("Nagy")
                .build();
        adminUserDao.updateUser(userId, updatedUser);

        assertThat(adminUserDao.getById(userId).getFirstName()).isEqualTo("updated First name");
    }

    @Test
    void updateNonExistingUser() {
        assertThrows(UserNotFoundException.class, () -> adminUserDao.updateUser(330L, user1));
    }

    @Test
    void deleteUser() throws UserNotFoundException {
        adminUserDao.addUser(user1);
        adminUserDao.addUser(user2);
        adminUserDao.addUser(admin);
        Long userId = admin.getUserId();

        adminUserDao.deleteUser(userId);
        assertThat(adminUserDao.getAll()).hasSize(2);
        assertThrows(UserNotFoundException.class, () -> adminUserDao.getById(userId));
    }

    @Test
    void deleteNonExistingUser() {
        assertThrows(UserNotFoundException.class, () -> adminUserDao.deleteUser(230L));
    }

    @Test
    void getCart() throws UserNotFoundException, ProductNotFoundException {
        adminUserDao.addUser(user1);
        Long userId = user1.getUserId();

        when(productDaoMock.getById(product1.getProductId())).thenReturn(product1);
        when(productDaoMock.getById(product2.getProductId())).thenReturn(product2);
        when(productDaoMock.getById(product3.getProductId())).thenReturn(product3);

        adminUserDao.addToCart(userId, product1.getProductId(), 1);
        adminUserDao.addToCart(userId, product2.getProductId(), 2);
        adminUserDao.addToCart(userId, product3.getProductId(), 3);

        assertThat(adminUserDao.getCart(userId)).hasSize(3);
        assertThat(adminUserDao.getById(userId).getCart().get(product1)).isEqualTo(1);
    }

    @Test
    void getCartOfNonExistingUser() {
        assertThrows(UserNotFoundException.class, () -> adminUserDao.getCart(800L));
    }

    @Test
    void addToCart() throws UserNotFoundException, ProductNotFoundException {
        adminUserDao.addUser(user1);

        Long userId = user1.getUserId();
        Long productId = product1.getProductId();
        when(productDaoMock.getById(productId)).thenReturn(product1);

        adminUserDao.addToCart(userId, productId, 2);

        assertThat(adminUserDao.getCart(userId).get(product1)).isEqualTo(2);
        adminUserDao.addToCart(userId, productId, 3);
        assertThat(adminUserDao.getCart(userId).get(product1)).isEqualTo(5);
    }

    @Test
    void addToCartOfNonExistingUser() throws ProductNotFoundException {
        Long productId = product1.getProductId();
        when(productDaoMock.getById(productId)).thenReturn(product1);

        assertThrows(UserNotFoundException.class,
                () -> adminUserDao.addToCart(990L, productId, 10));
    }

    @Test
    void addToCartNonExistingProduct() throws ProductNotFoundException {
        adminUserDao.addUser(user1);
        when(productDaoMock.getById(8800L)).thenThrow(ProductNotFoundException.class);
        assertThrows(ProductNotFoundException.class,
                () -> adminUserDao.addToCart(user1.getUserId(), 8800L, 10));
    }

    @Test
    void removeOneFromCart() throws UserNotFoundException, ProductNotFoundException {
        adminUserDao.addUser(user1);
        Long userId = user1.getUserId();
        Long productId = product1.getProductId();
        when(productDaoMock.getById(productId)).thenReturn(product1);
        adminUserDao.addToCart(userId, productId, 3);

        adminUserDao.removeOneFromCart(userId, productId);

        assertThat(adminUserDao.getCart(userId).get(product1)).isEqualTo(2);
    }

    @Test
    void removeOneFromCartOfNonExistingUser() throws ProductNotFoundException {
        Long productId = product1.getProductId();
        when(productDaoMock.getById(productId)).thenReturn(product1);

        assertThrows(UserNotFoundException.class,
                () -> adminUserDao.removeOneFromCart(4500L, productId));
    }

    @Test
    void removeOneFromCartNonExistingProduct() throws ProductNotFoundException {
        adminUserDao.addUser(user1);
        when(productDaoMock.getById(40L)).thenThrow(ProductNotFoundException.class);

        assertThrows(ProductNotFoundException.class,
                () -> adminUserDao.removeOneFromCart(user1.getUserId(), 40L));
    }

    @Test
    void removeProductFromCart() throws ProductNotFoundException, UserNotFoundException {
        adminUserDao.addUser(user1);
        Long userId = user1.getUserId();

        when(productDaoMock.getById(product1.getProductId())).thenReturn(product1);
        when(productDaoMock.getById(product2.getProductId())).thenReturn(product2);
        when(productDaoMock.getById(product3.getProductId())).thenReturn(product3);

        adminUserDao.addToCart(userId, product1.getProductId(), 1);
        adminUserDao.addToCart(userId, product2.getProductId(), 2);
        adminUserDao.addToCart(userId, product3.getProductId(), 3);

        adminUserDao.removeProductFromCart(userId, product2.getProductId());

        assertThat(adminUserDao.getCart(userId)).hasSize(2);
        assertThat(adminUserDao.getCart(userId).get(product2)).isNull();
    }

    @Test
    void removeProductFromCartOfNonExistingUser() throws ProductNotFoundException {
        Long productId = product1.getProductId();
        when(productDaoMock.getById(productId)).thenReturn(product1);

        assertThrows(UserNotFoundException.class,
                () -> adminUserDao.removeProductFromCart(57000L, productId));
    }

    @Test
    void removeProductFromCartNonExistingProduct() throws ProductNotFoundException {
        adminUserDao.addUser(user1);
        when(productDaoMock.getById(200L)).thenThrow(ProductNotFoundException.class);

        assertThrows(ProductNotFoundException.class,
                () -> adminUserDao.removeProductFromCart(user1.getUserId(), 200L));
    }

    @Test
    void updateQuantityOfProductInCart() throws ProductNotFoundException, UserNotFoundException {
        adminUserDao.addUser(user1);
        Long userId = user1.getUserId();
        Long productId = product1.getProductId();
        when(productDaoMock.getById(productId)).thenReturn(product1);
        adminUserDao.addToCart(userId, productId, 12);

        adminUserDao.updateQuantityOfProductInCart(userId, productId, 20);

        assertThat(adminUserDao.getCart(userId).get(product1)).isEqualTo(20);
    }

    @Test
    void updateQuantityOfProductInCartOfNonExistingUser() throws ProductNotFoundException {
        Long productId = product1.getProductId();
        when(productDaoMock.getById(productId)).thenReturn(product1);

        assertThrows(UserNotFoundException.class,
                () -> adminUserDao.updateQuantityOfProductInCart(4300L, productId, 30));
    }

    @Test
    void updateQuantityOfProductInCartOfNonExistingProduct() throws ProductNotFoundException {
        adminUserDao.addUser(user1);
        when(productDaoMock.getById(40L)).thenThrow(ProductNotFoundException.class);

        assertThrows(ProductNotFoundException.class,
                () -> adminUserDao.updateQuantityOfProductInCart(user1.getUserId(), 40L, 30));
    }

    @Test
    void clearCart() throws ProductNotFoundException, UserNotFoundException {
        adminUserDao.addUser(user1);
        Long userId = user1.getUserId();

        when(productDaoMock.getById(product1.getProductId())).thenReturn(product1);
        when(productDaoMock.getById(product2.getProductId())).thenReturn(product2);
        when(productDaoMock.getById(product3.getProductId())).thenReturn(product3);

        adminUserDao.addToCart(userId, product1.getProductId(), 1);
        adminUserDao.addToCart(userId, product2.getProductId(), 2);
        adminUserDao.addToCart(userId, product3.getProductId(), 3);

        adminUserDao.clearCart(userId);

        assertThat(adminUserDao.getCart(userId)).hasSize(0);
    }

    @Test
    void clearCartOfNonExistingUser() {
        assertThrows(UserNotFoundException.class, () -> adminUserDao.clearCart(65000L));
    }

}
