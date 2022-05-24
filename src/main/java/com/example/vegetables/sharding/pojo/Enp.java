package com.example.vegetables.sharding.pojo;

import lombok.Data;

import java.util.List;

@Data
public class Enp {
    private List<String> addresses;
    private boolean dhcp4;
    private boolean optional;
    private String gateway4;
    private Nameservers nameservers;
}
