package tg.groupedeux.rescinema.bootstrap;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import tg.groupedeux.rescinema.model.Role;
import tg.groupedeux.rescinema.service.AdminService;
import tg.groupedeux.rescinema.service.UserService;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Singleton
@Startup
public class DataInitializer {

    @Inject
    private AdminService adminService;

    @Inject
    private UserService userService;

    @PostConstruct
    public void init() {
        try {
            if (userService.findByEmailOrNull("admin@cinema.local") == null) {
                userService.register("admin@cinema.local", "admin123", Role.ADMIN);
            }
            if (userService.findByEmailOrNull("user@cinema.local") == null) {
                userService.register("user@cinema.local", "user123", Role.USER);
            }
            var f1 = adminService.createFilm("Inception", "Dreams within dreams", 148);
            var f2 = adminService.createFilm("Interstellar", "Space exploration", 169);
            var s1 = adminService.createSalle("Salle A", 5, 8);
            var s2 = adminService.createSalle("Salle B", 6, 10);
            adminService.createSeance(f1.getId(), s1.getId(), OffsetDateTime.now().plusHours(2), new BigDecimal("5.50"));
            adminService.createSeance(f2.getId(), s2.getId(), OffsetDateTime.now().plusHours(3), new BigDecimal("6.00"));
        } catch (Exception ignored) {
        }
    }
}
