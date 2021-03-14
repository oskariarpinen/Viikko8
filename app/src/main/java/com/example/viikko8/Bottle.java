package com.example.viikko8;

public class Bottle {
        private String name;
        private String maker;
        private double tot_energ;
        private double price;
        private double size;
        public Bottle() {
            name = "Pepsi Max";
            maker = "Pepsi";
            tot_energ = 0.3;
            price = 1.80;
            size = 0.5;
        }

        public Bottle(String n, String m, Double e, Double s, Double p) {
            name = n;
            maker = m;
            tot_energ = e;
            price = p;
            size = s;
        }
        public String getName() {
            return name;
        }
        public Double getPrice() {
            return price;
        }
        public Double getSize() {
            return size;
        }
    }
