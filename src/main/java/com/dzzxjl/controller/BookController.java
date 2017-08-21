package com.dzzxjl.controller;

import com.dzzxjl.domain.Book;
import com.dzzxjl.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.bind.SchemaOutputResolver;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by dzzxjl on 2017/8/16.
 */
@Controller
//@RestController
public class BookController {
    @Autowired
    BookRepository bookRepository;


    @RequestMapping("/bookcan")
    @ResponseBody
    public List<Book> getBookList() {
        List<Book> bookList = null;
        bookList = (List<Book>) this.bookRepository.findAll();
        return bookList;
    }

    @RequestMapping("/bookcancount")
    @ResponseBody
    public Integer getBookListCount() {
        return (int) this.bookRepository.count();
    }

    @RequestMapping("/search")
    @ResponseBody
    public List<Book> getBookListByTag (String tag) {
        List<Book> list = null;
        return this.bookRepository.findByTag(tag);
    }

    @RequestMapping("/book")
    @ResponseBody
    public List<Book> getHasReadBook(int status) {
        List<Book> list = null;
        return (List<Book>) this.bookRepository.findHasReadBook(status);
    }

    @RequestMapping("/bookmore")
    @ResponseBody
    public Book getBook(long id) {
        return this.bookRepository.findOne(id);
    }

    @RequestMapping("/addbook")
    @ResponseBody
    public List<Book> addBook(String name, String tag, int status) {
        // 重复查询
        Iterator<Book> iterator = this.bookRepository.findAll().iterator();
        while (iterator.hasNext()) {
            String temp = iterator.next().getName();
//            System.out.println(temp);
            if(temp.equals(name)) {
                return null;

            }
        }
        Book book1 = new Book();
        book1.setName(name);
        book1.setTag(tag);
        book1.setStatus(status);
        this.bookRepository.save(book1);

        return (List<Book>) this.bookRepository.findAll();
    }

    @RequestMapping("/deletebook")
    @ResponseBody
    public void deleteBook(long id) {
        this.bookRepository.delete(id);
    }
    @RequestMapping("/thy")
    public String  thy(Model model) {
        List<Book> list = (List<Book>) this.bookRepository.findAll();
        model.addAttribute("list", list);
        return "thy";
    }




}
