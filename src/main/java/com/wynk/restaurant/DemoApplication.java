/*assumptions have been made at a few places.
* mostly considered positive flow, since the restaurant will not try to enter corrupt data.
* some data has been set in Restaurant.java inside the constructor.
* Since it is only for one restaurant, the object has been made Singleton.
* */

package com.wynk.restaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
