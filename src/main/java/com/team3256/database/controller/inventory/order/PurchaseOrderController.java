package com.team3256.database.controller.inventory.order;

import com.team3256.database.error.DatabaseNotFoundException;
import com.team3256.database.model.hr.User;
import com.team3256.database.model.hr.UserRepository;
import com.team3256.database.model.inventory.PartRepository;
import com.team3256.database.model.inventory.order.*;
import com.team3256.database.service.NotificationService;
import com.team3256.database.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Secured("ROLE_USER")
@RestController
@RequestMapping("/api/inventory/order")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PartRepository partRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/")
    public Page getPurchaseOrders(Pageable pageable) {
        return purchaseOrderRepository.findAllByOrderByNeedByDateAsc(pageable);
    }

    @GetMapping("/{id}")
    public PurchaseOrder getPurchaseOrder(@PathVariable Integer id) {
        return purchaseOrderRepository.findById(id).orElseThrow(DatabaseNotFoundException::new);
    }

    @PostMapping("/")
    public PurchaseOrder createPurchaseOrder(@AuthenticationPrincipal Principal principal, @RequestBody PurchaseOrderDTO purchaseOrder) {
        PurchaseOrder dbPurchaseOrder = new PurchaseOrder();

        userRepository.findByEmail(principal.getName()).map(user -> {
            dbPurchaseOrder.setUser(user);
            dbPurchaseOrder.setDateOrdered(new Date());
            dbPurchaseOrder.setNeedByDate(purchaseOrder.getNeedByDate());
            dbPurchaseOrder.setPartName(purchaseOrder.getPartName());
            dbPurchaseOrder.setPartNumber(purchaseOrder.getPartNumber());
            dbPurchaseOrder.setQuantity(purchaseOrder.getQuantity());
            dbPurchaseOrder.setCostPerItem(purchaseOrder.getCostPerItem());
            dbPurchaseOrder.setUrl(purchaseOrder.getUrl());
            dbPurchaseOrder.setAsap(purchaseOrder.isAsap());
            dbPurchaseOrder.setStatus(PurchaseOrderStatus.TO_BE_ORDERED);

            dbPurchaseOrder.setUpdatedAt(new Date());
            dbPurchaseOrder.setLastUpdatedBy(user.getFirstName() + " " + user.getLastName());

            if (purchaseOrder.getPart() != null) {
                partRepository.findById(purchaseOrder.getPart().getId()).map(part -> {
                    dbPurchaseOrder.setPart(part);
                    return part;
                });
            }

            return user;
        }).orElseThrow(DatabaseNotFoundException::new);

        purchaseOrderRepository.save(dbPurchaseOrder);

        notificationService.sendNotificationAndEmailToMentors(
                String.format("%s created a new purchase order for %s with a quantity of %s", dbPurchaseOrder.getUser().getFirstName(), dbPurchaseOrder.getPartName(), dbPurchaseOrder.getQuantity()),
                "Click open to view",
                String.format(
                        "%s with a quantity of %d, totaling $%.2f. Needed by %s. %s",
                        dbPurchaseOrder.getPartName(),
                        dbPurchaseOrder.getQuantity(),
                        dbPurchaseOrder.getCostPerItem() * dbPurchaseOrder.getQuantity(),
                        dbPurchaseOrder.getNeedByDate().toString(),
                        "https://mywb.vcs.net/purchase-orders/" + dbPurchaseOrder.getId()),
                "https://mywb.vcs.net/purchase-orders/" + dbPurchaseOrder.getId()
        );

        return dbPurchaseOrder;
    }

    @PutMapping("/{id}/status")
    public PurchaseOrder updateStatus(@AuthenticationPrincipal Principal principal, @PathVariable Integer id, @RequestBody PurchaseOrderStatusDTO purchaseOrderStatus) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(DatabaseNotFoundException::new);
        PurchaseOrder purchaseOrder = purchaseOrderRepository.findById(id).orElseThrow(DatabaseNotFoundException::new);
        purchaseOrder.setUpdatedAt(new Date());
        purchaseOrder.setLastUpdatedBy(String.format("%s %s", user.getFirstName(), user.getLastName()));
        System.out.println(purchaseOrderStatus.status);
        purchaseOrder.setStatus(purchaseOrderStatus.status);

        String notificationText = String.format("%s updated the status of the purchase order '%s' to %s", purchaseOrder.getUser().getFirstName(), purchaseOrder.getPartName(), purchaseOrder.getStatus());

        notificationService.sendNotificationAndEmailToMentors(
                notificationText,
                "Click open to view",
                String.format(
                        "%s. %s",
                        notificationText,
                        "https://mywb.vcs.net/purchase-orders/" + purchaseOrder.getId()),
                "https://mywb.vcs.net/purchase-orders/" + purchaseOrder.getId()
        );

        notificationService.sendNotificationAndEmailToUser(
                purchaseOrder.getUser(),
                notificationText,
                "Click open to view",
                String.format(
                        "%s. %s",
                        notificationText,
                        "https://mywb.vcs.net/purchase-orders/" + purchaseOrder.getId()),
                "https://mywb.vcs.net/purchase-orders/" + purchaseOrder.getId()
        );

        return purchaseOrderRepository.save(purchaseOrder);
    }

    @DeleteMapping("/{id}")
    public Integer deletePurchaseOrder(@PathVariable Integer id) {
        purchaseOrderRepository.deleteById(id);
        return id;
    }

    @GetMapping("/user/{id}")
    public List<PurchaseOrder> getPurchaseOrdersForUser(@PathVariable Integer id) {
        return userRepository.findById(id).orElseThrow(DatabaseNotFoundException::new).getPurchaseOrders();
    }

    @GetMapping("/count")
    public Long getCount() {
        return purchaseOrderRepository.count();
    }
}
