package com.ace.generator;


import com.ace.entities.Users;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InsertUsers {

    public static List<Users> insertUsers() {

        String password = "909394";

        List<Users> usersList = new ArrayList<>();
        Users u1 = new Users();
        Users u2 = new Users();
        Users u3 = new Users();
        Users u4 = new Users();
        Users u5 = new Users();
        Users u6 = new Users();
        Users u7 = new Users();
        Users u8 = new Users();
        Users admin = new Users();
        Users garlam = new Users();

        LocalDateTime now = LocalDateTime.now();
        u1.setDateOfBirth(now);
        u2.setDateOfBirth(now);
        u3.setDateOfBirth(now);
        u4.setDateOfBirth(now);
        u5.setDateOfBirth(now);
        u6.setDateOfBirth(now);
        u7.setDateOfBirth(now);
        u8.setDateOfBirth(now);
        admin.setDateOfBirth(now);
        garlam.setDateOfBirth(now);

        u1.setExpireDate(now.plusYears(3));
        u2.setExpireDate(now.plusYears(3));
        u3.setExpireDate(now.plusYears(3));
        u4.setExpireDate(now.plusYears(3));
        u5.setExpireDate(now.plusYears(3));
        u6.setExpireDate(now.plusYears(3));
        u7.setExpireDate(now.plusYears(3));
        u8.setExpireDate(now.plusYears(3));
        admin.setExpireDate(now.plusYears(3));
        garlam.setExpireDate(now.plusYears(3));

        u1.setCreatedDate(now);
        u2.setCreatedDate(now);
        u3.setCreatedDate(now);
        u4.setCreatedDate(now);
        u5.setCreatedDate(now);
        u6.setCreatedDate(now);
        u7.setCreatedDate(now);
        u8.setCreatedDate(now);
        admin.setCreatedDate(now);
        garlam.setCreatedDate(now);

        u1.setCreatedBy(1001l);
        u2.setCreatedBy(2001l);
        u3.setCreatedBy(3001l);
        u4.setCreatedBy(4001l);
        u5.setCreatedBy(5001l);
        u6.setCreatedBy(6001l);
        u7.setCreatedBy(7001l);
        u8.setCreatedBy(0l);
        admin.setCreatedBy(0l);
        garlam.setCreatedBy(0l);

        u1.setDescription(Users.ADMINISTRATOR);
        u2.setDescription(Users.USER);
        u3.setDescription(Users.VIEWER);
        u4.setDescription(Users.DISABLE);
        u5.setDescription(Users.VIEWER);
        u6.setDescription(Users.USER);
        u7.setDescription(Users.DISABLE);
        u8.setDescription(Users.ADMINISTRATOR);
        admin.setDescription(Users.ADMINISTRATOR);
        garlam.setDescription(Users.ADMINISTRATOR);


        u1.setEmail("timothy_au@qq.com");
        u2.setEmail("peter_wong@qq.com");
        u3.setEmail("mary_leeg@qq.com");
        u4.setEmail("kalam@qq.com");
        u5.setEmail("may_tang@qq.com");
        u6.setEmail("frank_chow@qq.com");
        u7.setEmail("eric_luk@qq.com");
        u8.setEmail("root@ace.com");
        admin.setEmail("admin@ace.com");
        garlam.setEmail("garlam_au@foxmail.com");

        u1.setGender("M");
        u2.setGender("M");
        u3.setGender("F");
        u4.setGender("M");
        u5.setGender("M");
        u6.setGender("M");
        u7.setGender("M");
        u8.setGender("M");
        admin.setGender("M");
        garlam.setGender("M");


        u1.setLastUpdatedBy(1001l);
        u2.setLastUpdatedBy(2001l);
        u3.setLastUpdatedBy(3001l);
        u4.setLastUpdatedBy(4001l);
        u5.setLastUpdatedBy(5001l);
        u6.setLastUpdatedBy(6001l);
        u7.setLastUpdatedBy(7001l);
        u8.setLastUpdatedBy(0l);
        admin.setLastUpdatedBy(0l);
        garlam.setLastUpdatedBy(0l);


        u1.setLastUpdateDate(now);
        u2.setLastUpdateDate(now);
        u3.setLastUpdateDate(now);
        u4.setLastUpdateDate(now);
        u5.setLastUpdateDate(now);
        u6.setLastUpdateDate(now);
        u7.setLastUpdateDate(now);
        u8.setLastUpdateDate(now);
        admin.setLastUpdateDate(now);
        garlam.setLastUpdateDate(now);


        u1.setMobile("55550000");
        u2.setMobile("55550000");
        u3.setMobile("55550000");
        u4.setMobile("55550000");
        u5.setMobile("55550000");
        u6.setMobile("55550000");
        u7.setMobile("55550000");
        u8.setMobile("55550000");
        admin.setMobile("55550000");
        garlam.setMobile("95186540");


        u1.setPassword(password);
        u2.setPassword(password);
        u3.setPassword(password);
        u4.setPassword(password);
        u5.setPassword(password);
        u6.setPassword(password);
        u7.setPassword(password);
        u8.setPassword(password);
        admin.setPassword(password);
        garlam.setPassword(password);


        u1.setEnabled(true);
        u2.setEnabled(true);
        u3.setEnabled(true);
        u4.setEnabled(false);
        u5.setEnabled(true);
        u6.setEnabled(true);
        u7.setEnabled(true);
        u8.setEnabled(true);
        admin.setEnabled(true);
        garlam.setEnabled(true);

        u1.setStatus(Users.ACTIVE);
        u2.setStatus(Users.ACTIVE);
        u3.setStatus(Users.ACTIVE);
        u4.setStatus(Users.INACTIVE);
        u5.setStatus(Users.ACTIVE);
        u6.setStatus(Users.ACTIVE);
        u7.setStatus(Users.ACTIVE);
        u8.setStatus(Users.ACTIVE);
        admin.setStatus(Users.ACTIVE);
        garlam.setStatus(Users.ACTIVE);


        u1.setUserAccount("timothy");
        u2.setUserAccount("peter");
        u3.setUserAccount("mary");
        u4.setUserAccount("kalam");
        u5.setUserAccount("may");
        u6.setUserAccount("frank");
        u7.setUserAccount("eric");
        u8.setUserAccount("root");
        admin.setUserAccount("admin");
        garlam.setUserAccount("garlam");

        u1.setUsername("Timothy Au");
        u2.setUsername("Peter Wong");
        u3.setUsername("Mary Lee");
        u4.setUsername("Ka Lam");
        u5.setUsername("May Tang");
        u6.setUsername("Frank Chow");
        u7.setUsername("Eric Luk");
        u8.setUsername("Root");
        admin.setUsername("Administrator");
        garlam.setUsername("Garlam Au");

        usersList.add(u1);
        usersList.add(u2);
        usersList.add(u3);
        usersList.add(u4);
        usersList.add(u5);
        usersList.add(u6);
        usersList.add(u7);
        usersList.add(u8);
        usersList.add(admin);
        usersList.add(garlam);

        return usersList;
    }
}
