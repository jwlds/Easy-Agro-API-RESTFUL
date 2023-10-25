package jw.com.br.EasyAgro.services;



import jw.com.br.EasyAgro.domain.product.Product;
import jw.com.br.EasyAgro.domain.review.Review;
import jw.com.br.EasyAgro.dtos.ReviewDTO;
import jw.com.br.EasyAgro.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;


@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MongoTemplate mongoTemplate;
    public Review createReview(ReviewDTO payload) {
        Review review = reviewRepository.insert(new Review(payload));

        mongoTemplate.update(Product.class)
                .matching(Criteria.where("code").is(payload.code()))
                .apply(new Update().push("reviewIds").value(review))
                .first();

        return review;
    }
}
