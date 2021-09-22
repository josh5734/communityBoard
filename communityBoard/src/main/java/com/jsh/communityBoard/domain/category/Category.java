package com.jsh.communityBoard.domain.category;


import com.jsh.communityBoard.domain.user.Role;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@Entity
public class Category {
    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true)
    private String name;


    @Builder
    public Category(String name){
        this.name = name;
    }

}
