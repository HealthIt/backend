package com.swygbro.healthit.food.domian;

import com.swygbro.healthit.ingredient.domain.Ingredient;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

/**
 * 음식 Entity
 */
@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@ToString(of = {"id", "foodNm", "calorie", "protein", "carbs", "fat", "ingredients"})
public class Food {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "food_id")
    private Long id;

    @Column(nullable = false)
    private String foodNm;

    @Column(length = 1000)
    private String foodDesc;        // 음식소개

    @Column(nullable = false)
    private Integer calorie;        // 칼로리
    private Integer protein;        // 단백질
    private Integer carbs;          // 탄수화물
    private Integer fat;            // 지방

    private String img;             // 음식 이미지

    @OneToMany(mappedBy = "food", cascade = ALL)
    List<Ingredient> ingredients = new ArrayList<>();

    @Builder
    private Food(final Long id, final String foodNm, final String foodDesc, final Integer calorie,
                 final Integer protein, final Integer carbs, final Integer fat, final String img) {
        this.id = id;
        this.foodNm = foodNm;
        this.foodDesc = foodDesc;
        this.calorie = calorie;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.img = img;
    }

    /**
     * 식재료 추가
     * @param ingredient 식재료 Entity
     */
    public void addIngredient(final Ingredient ingredient) {
        if(ingredient.getFood() != null) {
            ingredient.getFood().getIngredients().remove(ingredient);
        }

        this.ingredients.add(ingredient);
        ingredient.setFood(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Food food = (Food) o;
        return Objects.equals(getId(), food.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}