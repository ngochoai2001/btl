package com.example.btl.model;

import java.io.Serializable;

public class User implements Serializable {
     String user;
     String password;
     String name;
     String phone;
     String adress;

     public User(String user, String password,  String name, String phone, String adress) {
          this.user = user;
          this.password = password;
          this.name = name;
          this.phone = phone;
          this.adress = adress;
     }

     public User(){

     }


     public String getUser() {
          return user;
     }

     public void setUser(String user) {
          this.user = user;
     }

     public String getPassword() {
          return password;
     }

     public void setPassword(String password) {
          this.password = password;
     }

     public String getName() {
          return name;
     }

     public void setName(String name) {
          this.name = name;
     }

     public String getPhone() {
          return phone;
     }

     public void setPhone(String phone) {
          this.phone = phone;
     }

     public String getAdress() {
          return adress;
     }

     public void setAdress(String adress) {
          this.adress = adress;
     }
}
