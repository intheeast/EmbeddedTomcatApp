package com.intheeast.dao;

import java.util.List;

import com.intheeast.entity.Post;

public interface PostDao extends Dao<Post> {
    // 추가적인 Post 관련 메서드가 필요하다면 여기에 선언합니다.
	List<Post> findAllWithComments();
}
