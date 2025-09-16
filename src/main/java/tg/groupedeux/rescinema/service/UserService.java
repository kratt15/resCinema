package tg.groupedeux.rescinema.service;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import tg.groupedeux.rescinema.model.Role;
import tg.groupedeux.rescinema.model.User;
import tg.groupedeux.rescinema.security.PasswordUtil;

@Stateless
public class UserService {

    @PersistenceContext(unitName = "resCinemaPU")
    private EntityManager entityManager;

    public User register(String email, String plainPassword, Role role) {
        if (email == null || plainPassword == null) {
            throw new IllegalArgumentException("Email et mot de passe requis");
        }
        if (findByEmailOrNull(email) != null) {
            throw new IllegalArgumentException("Email déjà utilisé");
        }
        User user = new User();
        user.setEmail(email.trim().toLowerCase());
        String salt = PasswordUtil.generateSalt();
        user.setPasswordSalt(salt);
        user.setPasswordHash(PasswordUtil.hashPassword(plainPassword.toCharArray(), salt));
        user.setRole(role == null ? Role.USER : role);
        entityManager.persist(user);
        return user;
    }

    public User authenticate(String email, String plainPassword) {
        User user = findByEmailOrNull(email);
        if (user == null) return null;
        boolean ok = PasswordUtil.verifyPassword(plainPassword.toCharArray(), user.getPasswordSalt(), user.getPasswordHash());
        return ok ? user : null;
    }

    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    public User findByEmailOrNull(String email) {
        if (email == null) return null;
        try {
            TypedQuery<User> q = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
            q.setParameter("email", email.trim().toLowerCase());
            return q.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
