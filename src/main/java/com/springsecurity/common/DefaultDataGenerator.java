package com.springsecurity.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.springsecurity.account.Account;
import com.springsecurity.account.AccountService;
import com.springsecurity.book.Book;
import com.springsecurity.book.BookRepository;

/**
 * jpa 로 기본 유저 생성 및 book 생성
 */
@Component
public class DefaultDataGenerator implements ApplicationRunner {

    @Autowired
    AccountService accountService;

    @Autowired
    BookRepository bookRepository;

    public void run(ApplicationArguments args) throws Exception {
        Account gos40 = createUser("gos40");
        Account gos1004 = createUser("gos1004");
        createBook("spring", gos40);
        createBook("hibernate", gos1004);
    }

    private void createBook(String title, Account account) {
        Book book = new Book();
        book.setTitle(title);
        book.setAuthor(account);
        bookRepository.save(book);
    }

    private Account createUser(String usename) {
        Account account = new Account();
        account.setUsername(usename);
        account.setPassword("123");
        account.setRole("USER");
        return accountService.createNew(account);
    }
}