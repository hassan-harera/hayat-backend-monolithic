package com.harera.hayat.model.food;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.harera.hayat.model.BaseDonation;
import com.harera.hayat.model.user.User;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "food_donation")
public class FoodDonation extends BaseDonation {

    @Basic
    @Column(name = "food_expiration_date")
    private LocalDate foodExpirationDate;

    @ManyToOne
    @JoinColumn(name = "food_unit_id", referencedColumnName = "id")
    private FoodUnit foodUnit;

    @ManyToOne
    @JoinColumn(name = "food_category_id", referencedColumnName = "id")
    private FoodCategory foodCategory;

    @Basic
    @Column(name = "quantity")
    private Float quantity;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "food_donation_upvotes",
                    joinColumns = @JoinColumn(name = "donation_id"),
                    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> upvotes = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "food_donation_downvotes",
                    joinColumns = @JoinColumn(name = "donation_id"),
                    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> downvotes = new HashSet<>();

    public Integer getReputation() {
        return upvotes.size() - downvotes.size();
    }

    public void upvote(User user) {
        upvotes.add(user);
        downvotes.remove(user);
    }

    public void downvote(User user) {
        downvotes.add(user);
        upvotes.remove(user);
    }
}
