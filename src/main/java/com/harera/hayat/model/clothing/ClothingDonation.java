package com.harera.hayat.model.clothing;

import java.util.HashSet;
import java.util.Set;

import com.harera.hayat.model.BaseDonation;
import com.harera.hayat.model.user.User;

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
@Table(name = "clothing_donation")
public class ClothingDonation extends BaseDonation {

    @Column(name = "quantity")
    private int quantity;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "clothing_season_id", referencedColumnName = "id")
    private ClothingSeason clothingSeason;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "clothing_size_id", referencedColumnName = "id")
    private ClothingSize clothingSize;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "clothing_type_id", referencedColumnName = "id")
    private ClothingType clothingType;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "clothing_category_id", referencedColumnName = "id")
    private ClothingCategory clothingCategory;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "clothing_condition_id", referencedColumnName = "id")
    private ClothingCondition clothingCondition;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "clothing_donation_upvotes",
                    joinColumns = @JoinColumn(name = "donation_id"),
                    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> upvotes = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "clothing_donation_downvotes",
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
