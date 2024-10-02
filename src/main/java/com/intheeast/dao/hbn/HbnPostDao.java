package com.intheeast.dao.hbn;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.intheeast.dao.AbstractHbnDao;
import com.intheeast.dao.PostDao;
import com.intheeast.entity.Post;
import com.intheeast.entity.QComment;
import com.intheeast.entity.QPost;
import com.querydsl.jpa.impl.JPAQueryFactory;


@Repository("postDao")
public class HbnPostDao extends AbstractHbnDao<Post> 
	implements PostDao{
	
	public HbnPostDao() {
		super(Post.class);
	}	
	
	public HbnPostDao(Class<Post> entityClass) {
		super(entityClass);
		// TODO Auto-generated constructor stub
	}
	
	/**
     * Fetch join을 사용하여 Post와 관련된 Comment들을 함께 가져오는 메서드.
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true) // 트랜잭션이 필요한 부분에 트랜잭션 설정 추가
    public List<Post> findAllWithComments() {
//        Session session = getSession(); // Hibernate 세션 가져오기
        
        JPAQueryFactory queryFactory = new JPAQueryFactory(getSession());

        QPost post = QPost.post;
        QComment comment = QComment.comment;
        return queryFactory.selectFrom(post)
                           .leftJoin(post.commentList, comment).fetchJoin() // commentList를 left join으로 가져옴
                           .fetch(); // 결과를 리스트로 반환

        // JPQL 쿼리에서 fetch join을 사용하여 Post와 연결된 Comment 목록을 함께 가져옴
//        Query<Post> query = session.createQuery(
//            "SELECT p FROM Post p LEFT JOIN FETCH p.commentList", Post.class
//        );
//
//        return query.getResultList();
    }

}
