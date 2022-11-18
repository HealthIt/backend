package com.swygbro.healthit.ingredient.domain;

import com.swygbro.healthit.food.domian.Food;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

/**
 * 식재료 Entity
 */
@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@ToString(of = {"id", "irdntNm"})
public class Ingredient {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "irdnt_id")
    private Long id;

    @Column(nullable = false)
    private String irdntNm;        // 식재료명

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id")
    private Food food;

    public Ingredient(final String irdntNm) {
        this.irdntNm = irdntNm;
    }
}
