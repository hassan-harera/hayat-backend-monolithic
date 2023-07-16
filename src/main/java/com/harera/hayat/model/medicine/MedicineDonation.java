package com.harera.hayat.model.medicine;

import java.time.LocalDate;
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
@Table(name = "medicine_donation")
public class MedicineDonation extends BaseDonation {

    @Column(name = "quantity")
    private Float quantity;

    @ManyToOne
    @JoinColumn(name = "medicine_unit_id", referencedColumnName = "id")
    private MedicineUnit medicineUnit;

    @Column(name = "medicine_expiration_date")
    private LocalDate medicineExpirationDate;

    @ManyToOne
    @JoinColumn(name = "medicine_id", referencedColumnName = "id")
    private Medicine medicine;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "medicine_donation_upvotes",
                    joinColumns = @JoinColumn(name = "donation_id"),
                    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> upvotes = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "medicine_donation_downvotes",
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
