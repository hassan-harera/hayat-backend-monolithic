package com.harera.hayat.model.property;

import java.time.OffsetDateTime;
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
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "property_donation")
public class PropertyDonation extends BaseDonation {

    @Column(name = "rooms")
    private int rooms;

    @Column(name = "bathrooms")
    private int bathrooms;

    @Column(name = "kitchens")
    private int kitchens;

    @Column(name = "people_capacity")
    private int peopleCapacity;

    @Column(name = "available_from")
    private OffsetDateTime availableFrom;

    @Column(name = "available_to")
    private OffsetDateTime availableTo;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "property_donation_upvotes",
                    joinColumns = @JoinColumn(name = "donation_id"),
                    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> upvotes = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "property_donation_downvotes",
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
