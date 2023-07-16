package com.harera.hayat.model.book;

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
@Table(name = "book_donation")
public class BookDonation extends BaseDonation {

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "book_title")
    private String bookTitle;

    @Column(name = "book_sub_title")
    private String bookSubTitle;

    @Column(name = "book_author")
    private String bookAuthor;

    @Column(name = "book_publisher")
    private String bookPublisher;

    @Column(name = "book_publication_year")
    private String publicationYear;

    @Column(name = "book_language")
    private String bookLanguage;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "book_donation_upvotes",
                    joinColumns = @JoinColumn(name = "donation_id"),
                    inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> upvotes = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "book_donation_downvotes",
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
