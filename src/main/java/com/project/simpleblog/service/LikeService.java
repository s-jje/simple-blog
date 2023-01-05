package com.project.simpleblog.service;

import com.project.simpleblog.domain.User;

public interface LikeService {

    String likeOrUnlike(Long id, User user);

}
