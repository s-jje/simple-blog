package com.project.simpleblog.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "Users")
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;


    //좋아요기능
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "users")
//    private List<BoardLike> boardLikeList = new ArrayList<>();


    public User(String username, String password, UserRoleEnum role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    //좋아요기능
//    public void mappingBoardLike(BoardLike boardLike){
//        this.boardLikeList.add(boardLike);
//    }

    public boolean isValidId(Long id) {
        return this.id.equals(id);
    }

    public boolean isValidPassword(String password) {
        return this.password.equals(password);
    }

    public boolean isAdmin() {
        return role.equals(UserRoleEnum.ADMIN);
    }

}