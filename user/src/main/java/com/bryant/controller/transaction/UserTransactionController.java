package com.bryant.controller.transaction;

import com.bryant.model.User;
import com.bryant.model.UserDetail;
import com.bryant.service.UserService;
import org.bouncycastle.jce.provider.AnnotatedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;
import java.util.Random;

@RestController
@RequestMapping("/transaction")
@Transactional(propagation = Propagation.NEVER, readOnly = false)
public class UserTransactionController {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @PostMapping("/user/add")
    public UserDetail addUser(User user)
    {
        long id = new Random().nextLong();
        UserDetail detail = UserDetail.builder()
                .id(id)
                .age(new Random().nextInt(100))
                .email(new Random().nextInt(100000000) + "@qq.com")
                .name("bryant" + new Random().nextInt(1111))
                .tenantId(new Random().nextLong())
                .build();
        userService.insert(detail);

        return userService.getById(id);
    }

    @GetMapping("/user/{id}")
    public UserDetail user(
            @PathVariable("id") Long id
    ) {
        return userService.getById(id);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUser(
            @PathVariable("id") Long id
    ) {
        userService.delete(id);
    }


    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    @PostMapping("/user/add_with_ex")
    public void addUserWithEx(User user) throws AnnotatedException {
        long id = new Random().nextLong();
        UserDetail detail = UserDetail.builder()
                .id(id)
                .age(new Random().nextInt(100))
                .email(new Random().nextInt(100000000) + "@qq.com")
                .name("bryant" + new Random().nextInt(1111))
                .tenantId(new Random().nextLong())
                .build();
        userService.insert(detail);
        throw new NullPointerException("addUserWithEx...");
//        throw new AnnotatedException("1111");
    }


    @PostMapping("/user/add_with_ex2")
    public void addUserWithEx2(User user)
    {
        long id = new Random().nextLong();
        transactionTemplate.execute(
                new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                        UserDetail detail = UserDetail.builder()
                                .id(id)
                                .age(new Random().nextInt(100))
                                .email(new Random().nextInt(100000000) + "@qq.com")
                                .name("bryant" + new Random().nextInt(1111))
                                .tenantId(new Random().nextLong())
                                .build();
                        userService.insert(detail);
                        throw new NullPointerException("addUserWithEx...");
                    }
                }
        );
    }
}
