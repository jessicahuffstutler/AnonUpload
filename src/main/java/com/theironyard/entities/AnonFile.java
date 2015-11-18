package com.theironyard.entities;

import javax.persistence.*;

/**
 * Created by jessicahuffstutler on 11/18/15.
 */
@Entity
@Table(name = "files")
public class AnonFile {
    @Id
    @GeneratedValue
    @Column(nullable = false)
    //if this isnt public, it wont be send down in the json file
    public int id;

    @Column(nullable = false)
    public String originalName;

    @Column(nullable = false)
    public String name;
}
