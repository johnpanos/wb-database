package com.team3256.database.controller.hr;

import com.team3256.database.model.hr.Notification;
import com.team3256.database.model.hr.NotificationDTO;
import com.team3256.database.model.hr.User;
import com.team3256.database.service.NotificationService;
import com.team3256.database.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/hr/notification")
public class NotificationController {
    @Autowired
    NotificationService notificationService;

    @Autowired
    UserService userService;

    @GetMapping("/")
    public List<Notification> getNotificationsForCurrentUser(@AuthenticationPrincipal Principal principal) {
        return notificationService.getUnseenNotificationsForUser(userService.getUserFromPrincipal(principal));
    }

    @PostMapping("/")
    public Notification sendNotificationToCurrentUser(@AuthenticationPrincipal Principal principal, @RequestBody NotificationDTO notificationDTO) {
        User user = userService.getUserFromPrincipal(principal);
        return notificationService.sendNotificationToUser(user, notificationDTO.title, notificationDTO.description, notificationDTO.url);
    }

    @PostMapping("/read")
    public String readAllNotificationsFromUser(@AuthenticationPrincipal Principal principal) {
        User user = userService.getUserFromPrincipal(principal);
        notificationService.readAllNotificationsForUser(user);
        return "success";
    }

    @GetMapping("/{id}/dismiss")
    public Notification dismissNotification(@PathVariable Integer id) {
        return notificationService.readNotificationById(id);
    }
}
