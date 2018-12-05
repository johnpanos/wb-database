package com.team3256.database.service;

import com.team3256.database.error.DatabaseNotFoundException;
import com.team3256.database.model.hr.Notification;
import com.team3256.database.model.hr.NotificationRepository;
import com.team3256.database.model.hr.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NotificationService {
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    public List<Notification> getNotificationsForUser(User user) {
        return user.getNotifications();
    }

    public List<Notification> getUnseenNotificationsForUser(User user) {
        return notificationRepository.findAllByUserAndSeen(user, false);
    }

    public void sendNotificationAndEmailToMentors(String title, String description, String body, String url) {
        for (User mentor : userService.getMentors()) {
            sendNotificationAndEmailToUser(mentor, title, description, body, url);
        }
    }

    // Create new notification in database and also send it to all platforms (eg. web, iOS, Android, email)
    public Notification sendNotificationToUser(User user, String title, String description, String url) {
        logger.info(String.format("Sending notification with title (%s), description (%s), url (%s), to user %s %s", title, description, url, user.getFirstName(), user.getLastName()));
        Notification notification = new Notification(user, title, description, url, new Date());
        return notificationRepository.save(notification);
    }

    public Notification sendNotificationAndEmailToUser(User user, String title, String description, String body, String url) {
        emailService.sendEmail(user.getEmail(), title, body);
        return sendNotificationToUser(user, title, description, url);
    }

    public void readAllNotificationsForUser(User user) {
        List<Notification> notifications = user.getNotifications();
        for (Notification notification : notifications) {
            logger.info(String.format("Setting (%s) as read", notification.getTitle()));
            notification.setSeen(true);
            notificationRepository.save(notification);
        }
    }

    public Notification readNotificationById(Integer id) {
        return readNotification(notificationRepository.findById(id).orElseThrow(DatabaseNotFoundException::new));
    }

    public Notification readNotification(Notification notification) {
        notification.setSeen(true);
        return notificationRepository.save(notification);
    }
}
