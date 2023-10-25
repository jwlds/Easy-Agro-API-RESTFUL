package jw.com.br.EasyAgro.domain.review;

import jw.com.br.EasyAgro.dtos.ReviewDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    private ObjectId reviewId;
    private String code;
    private String comment;
    private double ratting;

    public Review(ReviewDTO payload) {
        this.code = payload.code();
        this.comment = payload.comment();
        this.ratting = payload.ratting();
    }
}
