package ru.svetlov.webstore.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Repository;
import ru.svetlov.webstore.repository.CartRepository;
import ru.svetlov.webstore.util.cart.Cart;
import ru.svetlov.webstore.util.cart.impl.CartImpl;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

/***
 * Репозиторий для хранения корзин в Redis
 * Корзины идентифицируются по [id], который атомарно генерится из счётчика cartIdCounter
 * Корзины хранятся в репозитории по ключу [cid] = "cart:[id]"
 * Владельцы корзин идентифицируются по [uid] передаваемым из сервиса
 * Записи о владельцах хранятся в списке "owner:[cid]" = [uid] и обратном списке "user:[uid]" = [cid];
 * Для неавторизованных пользователей записей о владении корзинами не создается,
 * такие корзины можно назначать авторизованным пользователям.
 * Для корзин без владельца установлен EXPIRE  = 7 дней
 */

@Repository
public class CartRepositoryImpl implements CartRepository {
    private static final int DAYS_TO_EXPIRE = 7; // TODO: move setting to application.yaml

    private final RedisAtomicLong cartIdCounter;

    private final RedisTemplate<String, String> template;
    private final RedisTemplate<String, Cart> cartTemplate;

    @Autowired
    public CartRepositoryImpl(RedisTemplate<String, Cart> cartTemplate, RedisTemplate<String, String> template) {
        this.template = template;
        this.cartTemplate = cartTemplate;
        cartIdCounter = new RedisAtomicLong(KeyUtil.CID_GENERATOR, template.getRequiredConnectionFactory());
    }

    @Override
    public Optional<Cart> findById(String cartId) {
        return cartId == null ? Optional.empty() : Optional.ofNullable(cartTemplate.opsForValue().get(cartId));
    }

    @Override
    public boolean existsById(String cartId) {
        return cartId != null && Optional.ofNullable(cartTemplate.hasKey(cartId)).orElse(false);
    }

    @Override
    public void save(Cart cart) {
        Long owner = findOwner(cart);
        if (owner == null || owner == 0) {
            cartTemplate.opsForValue().set(cart.getId(), cart, Duration.ofDays(DAYS_TO_EXPIRE));
        } else {
            cartTemplate.opsForValue().set(cart.getId(), cart);
        }
    }

    @Override
    public Cart create() {
        long cid = cartIdCounter.incrementAndGet();
        String cartKey = KeyUtil.cartKey(cid);
        Cart cart = new CartImpl(cartKey);
        save(cart);
        return cart;
    }

    @Override
    public Cart create(Long uid) {
        Cart cart = create();
        setOwner(cart, uid);
        return cart;
    }

    @Override
    public void clear(String cid) {
        Cart cart = findById(cid).orElseThrow();
        Long uid = findOwner(cart);
        template.unlink(List.of(KeyUtil.ownerKey(cid), KeyUtil.userKey(uid)));
        cartTemplate.unlink(cid);
    }

    @Override
    public Optional<Cart> findByOwnerId(Long uid) {
        String key = KeyUtil.userKey(uid);
        String cid = template.opsForValue().get(key);
        return findById(cid);
    }

    @Override
    public Long findOwner(Cart cart) {
        String key = KeyUtil.ownerKey(cart.getId());
        return Long.valueOf(Optional.ofNullable(template.opsForValue().get(key)).orElse("0"));
    }

    @Override
    public Cart merge(Cart source, Cart destination) {
        destination.merge(source);
        clear(source.getId());
        return destination;
    }

    @Override
    public void setOwner(Cart cart, Long uid) {
        String ownerKey = KeyUtil.ownerKey(cart.getId());
        String userKey = KeyUtil.userKey(uid);
        template.opsForValue().set(ownerKey, uid.toString());
        template.opsForValue().set(userKey, cart.getId());
        cartTemplate.persist(cart.getId());
    }

    private static class KeyUtil {
        public static final String CID_GENERATOR = "global:cart:id";
        private static final String CART_PREFIX = "cart:"; // [cart:NN]
        private static final String OWNER_PREFIX = "owner:"; // [owner:cart:NN]
        private static final String USER_PREFIX = "user:"; // [user:NN]

        public static String ownerKey(String cid) {
            return OWNER_PREFIX + cid;
        }

        public static String userKey(Long uid) {
            return USER_PREFIX + uid;
        }

        public static String cartKey(Long id) {
            return CART_PREFIX + id;
        }

    }
}
