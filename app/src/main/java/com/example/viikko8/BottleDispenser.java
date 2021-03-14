package com.example.viikko8;

import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class BottleDispenser {
    private int bottles;
    private double money;
    ArrayList<Bottle> bottle_array = new ArrayList<Bottle>();

    private static BottleDispenser automaatti = new BottleDispenser();

    private BottleDispenser() {
        money = 0;
        String name = "Pepsi Max";
        String maker = "Pepsi";
        Double tot_energ = 0.3;
        Double price = 1.80;
        Double size = 0.5;
        Bottle tempbottle = new Bottle(name, maker,tot_energ, size, price);
        bottle_array.add(tempbottle);
        bottles++;
        name = "Jaffa Palma";
        maker = "Jaffa";
        tot_energ = 1.2;
        price = 3.2;
        size = 1.5;
        tempbottle = new Bottle(name, maker,tot_energ, size, price);
        bottle_array.add(tempbottle);
        bottles++;
        name = "Coca Cola";
        maker = "Coca Cola";
        tot_energ = 0.4;
        price = 2.2;
        size = 0.5;
        tempbottle = new Bottle(name, maker,tot_energ, size, price);
        bottle_array.add(tempbottle);
        bottles++;
    }


    public static BottleDispenser getInstance(){
        return automaatti;
    }

    public void addMoney(int n) {
        money += n;
    }

    public int buyBottle(int pullonro) {
        if (bottles <= 0) {

            return 0;
        }
        Bottle temp_bottle = bottle_array.get(pullonro);
        if (money <= 0 || money < temp_bottle.getPrice() ) {
            return 1;
        }
        money -= temp_bottle.getPrice();
        bottle_array.remove(pullonro);
        bottles -= 1;
        return 2;
    }

    public double returnMoney() {
        double tempmoney = money;
        money = 0;
        return tempmoney;
    }

    public double getMoney() {
        return money;
    }
}
