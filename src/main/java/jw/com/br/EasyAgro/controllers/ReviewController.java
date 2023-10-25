package jw.com.br.EasyAgro.controllers;


import jw.com.br.EasyAgro.domain.review.Review;
import jw.com.br.EasyAgro.dtos.ReviewDTO;
import jw.com.br.EasyAgro.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody ReviewDTO payload){
        return new ResponseEntity<Review>(reviewService.createReview(payload),
                HttpStatus
                .OK);
    }
}
