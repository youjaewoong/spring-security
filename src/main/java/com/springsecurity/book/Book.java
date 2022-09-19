package com.springsecurity.book;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.springsecurity.account.Account;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Book {

    @Id @GeneratedValue
    private Integer id;

    private String title;

    @ManyToOne
    private Account author;

}