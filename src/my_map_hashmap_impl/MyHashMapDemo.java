package my_map_hashmap_impl;

public class MyHashMapDemo {
    public static void main(String[] args) {

        MyMap<String, Integer> myMap = new MyHashMap<>();
        myMap.put("Johnson", 25);
        myMap.put("Smith", 31);
        myMap.put("Jackson", 20);
        myMap.put("Anderson", 23);
        myMap.put("Kurtz", 17);
        myMap.put("Willard", 18);

        System.out.println("Map entries: " + myMap);

        System.out.println("The score for Smith is " + myMap.get("Smith"));

        System.out.println("Is a score value of 17 in the map? " + myMap.containsValue(17));
        System.out.println("Is a score value of 100 in the map? " + myMap.containsValue(100));

        System.out.println("Is Jackson in the map? " + myMap.containsKey("Jackson"));
        myMap.remove("Smith");
        System.out.println("Is Jackson now in the map? " + myMap.containsKey("Jackson"));

        myMap.clear();
        System.out.println("Map entries after clearing map: " + myMap);
    }
}
