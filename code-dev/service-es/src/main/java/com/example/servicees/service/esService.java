package com.example.servicees.service;

import com.example.servicees.dao.esDao;
import com.example.servicees.dto.Pagedto;
import com.example.servicees.pojo.article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @project: blog
 * @ClassName: esService
 * @author: smallwei
 * @creat: 2022/8/12 18:58
 * 描述:
 */
@Service
public class esService {
    @Autowired
    esDao esdao;

    public void save(article article) {
        esdao.save(article);
    }

    public Optional<article> searchbyId(String id) {
        return esdao.findById(id);
    }

    public List<article> Search(String title) {
        return esdao.getAllByTitle(title);
    }

    public List<article> searchbytitleandcontent(String title, String content) {
        return esdao.getAllByTitleAndContent(title, content);
    }

    public void saveall(List<article> articlelist) {
        esdao.saveAll(articlelist);
    }

    public void delete(List<article> articleList) {
        esdao.deleteAll(articleList);
    }

    public void deleteById(String id) {
        esdao.deleteById(id);
    }

    public List<article> searchByTipe(String type) {
        return esdao.getAllByType(type);
    }

    public Pagedto SearchLImit(String title, String type, int currentpage) {
        currentpage--;
        Pageable pageable = PageRequest.of( currentpage, 5);
        Page<article> page = esdao.findAllByTitleOrType(title, type,pageable);
        Pagedto dto=new Pagedto();
        dto.setArticleList(page.getContent());
        dto.setCurrentpage( currentpage+1);
        dto.setTotalpage(page.getTotalPages());
        return dto;
    }

}
