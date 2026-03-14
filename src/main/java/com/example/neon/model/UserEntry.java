package com.example.neon.model;

import jakarta.persistence.*;

@Entity
@Table(name = "network_configurations")
public class UserEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String rollNumber;
    private String abstractName;
    private String ipv4Frontend;
    private String ipv4Backend;

    public UserEntry() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRollNumber() { return rollNumber; }
    public void setRollNumber(String rollNumber) { this.rollNumber = rollNumber; }
    public String getAbstractName() { return abstractName; }
    public void setAbstractName(String abstractName) { this.abstractName = abstractName; }
    public String getIpv4Frontend() { return ipv4Frontend; }
    public void setIpv4Frontend(String ipv4Frontend) { this.ipv4Frontend = ipv4Frontend; }
    public String getIpv4Backend() { return ipv4Backend; }
    public void setIpv4Backend(String ipv4Backend) { this.ipv4Backend = ipv4Backend; }
}
