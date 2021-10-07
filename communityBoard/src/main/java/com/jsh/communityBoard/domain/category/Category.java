package com.jsh.communityBoard.domain.category;


import com.jsh.communityBoard.domain.posts.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
public class Category {

    @Id
    @GeneratedValue
    @Column(name="CATEGORY_ID")
    private int id;

    private String name;

    @OneToMany(mappedBy = "category")
    private List<Post> postsList = new ArrayList<>();

    public Category(String name){
        this.name = name;
    }

}
