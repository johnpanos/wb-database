package com.team3256.database.model.hr;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "powerschool_id")
    private Integer powerSchoolId;

    @Column(name = "grade")
    private Integer grade;

    @JsonBackReference
    @OneToOne
    @JoinColumn(name = "wb_user_id")
    private User user;

    @Column(name = "backup_email")
    private String backupEmail;

    @Column(name = "shirt_size")
    private String shirtSize;

    @Column(name = "polo_size")
    private String poloSize;

    @Column(name = "in_ftc")
    private boolean inFTC;

    @Column(name = "in_frc")
    private boolean inFRC;

    @Column(name = "in_fll")
    private boolean inFLL;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPowerSchoolId() {
        return powerSchoolId;
    }

    public void setPowerSchoolId(Integer powerSchoolId) {
        this.powerSchoolId = powerSchoolId;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getBackupEmail() {
        return backupEmail;
    }

    public void setBackupEmail(String backupEmail) {
        this.backupEmail = backupEmail;
    }

    public String getShirtSize() {
        return shirtSize;
    }

    public void setShirtSize(String shirtSize) {
        this.shirtSize = shirtSize;
    }

    public String getPoloSize() {
        return poloSize;
    }

    public void setPoloSize(String poloSize) {
        this.poloSize = poloSize;
    }

    public boolean isInFTC() {
        return inFTC;
    }

    public void setInFTC(boolean inFTC) {
        this.inFTC = inFTC;
    }

    public boolean isInFRC() {
        return inFRC;
    }

    public void setInFRC(boolean inFRC) {
        this.inFRC = inFRC;
    }

    public boolean isInFLL() {
        return inFLL;
    }

    public void setInFLL(boolean inFLL) {
        this.inFLL = inFLL;
    }
}
