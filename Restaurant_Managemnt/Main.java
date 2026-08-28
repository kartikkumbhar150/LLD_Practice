package Restaurant_Managemnt;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        Customer customer = new Customer(1 , "Kartik");
        Menu menu = new Menu(new ArrayList<>());
        FoodItem pizza = new FoodItem(1, "Pizza", 300, FoodType.VEG);

        Restaurant restaurant = new Restaurant(1, "Hotel Rajendra", menu);
        

        ArrayList<FoodItem> food = new ArrayList<>();
        food.add(pizza);

        Order order = customer.createOrder(1, food, restaurant);
        menu.addFoodItem(pizza);
        Billing billing = new Billing();

        billing.setOrder(order);

        Bill bill = billing.createBill();

        System.out.println("Customer: " + order.getCustomer().getName());
        System.out.println("Restaurant: " + order.getRestaurantName().getName());

        System.out.println("Food Items:");

        for (FoodItem foodItem : order.getFoodItems()) {
            System.out.println(
                foodItem.getName() + " -Rs" + foodItem.getPrice()
        );
    }

System.out.println("Total Amount: Rs" + bill.getTotalAmount());

        
        
    }
}

// An interface is a blueprint of class , it defines a contract that a class follows. 
// It tells what a class should do rather than telling how it should do , without specifying how it should de.
/* So here interface defines a contract of payment
*/
/* Interface does not have constructors.
*/

interface Payment{
    void pay(int amount);

}

enum FoodType{
    VEG, 
    NON_VEG;

}

class Restaurant{

    private int id;
    private String name;
    private Menu menu;

    public Restaurant(int id, String name, Menu menu){
        this.id = id;
        this.name = name;
        this.menu = menu;
    }

    public String getName(){
        return name;

    }
    public void setName(String name){
        this.name = name;
    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;

    }
    public Menu getMenu(){
        return menu;
    }

    public void setMenu(Menu menu){
        this.menu = menu;
    }
}

class Customer{
    private int id;
    private String name;

    public Customer(int id, String name){
        this.id = id;
        this.name = name;
    }
    public int getId(){
        return id;

    }
    public void setId(int id){
        this.id = id;

    }
    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Order createOrder(int orderid, ArrayList<FoodItem> foodItems, Restaurant restaurantName) {
        return new Order(orderid, foodItems, restaurantName, this);
    }

}

class Order{

    private int orderid;
    private ArrayList<FoodItem> foodItems;
    private Restaurant restaurantName;
    private Customer customer;
    

    public Order(int orderId, ArrayList<FoodItem> foodItems, Restaurant name , Customer cname){
        this.orderid = orderId;
        this.foodItems = foodItems;
        this.restaurantName = name;
        this.customer = cname; 
    }
    public int getOrderid(){
        return orderid;
    }

    public void setOrderid(int orderid){
        this.orderid = orderid;
    }

    public ArrayList<FoodItem> getFoodItems(){
        return foodItems;

    }

    public void setFoodItems(ArrayList<FoodItem> foodItems){
        this.foodItems = foodItems;

    }

    public Restaurant getRestaurantName(){
        return restaurantName;
    }
    public void setRestaurantName(Restaurant restaurantName){
        this.restaurantName = restaurantName;

    }
    public Customer getCustomer(){
        return customer;
    }

    public void setCustomer(Customer customer){
        this.customer = customer;
    }

    


}
class Menu{
    private ArrayList<FoodItem> list;
    public Menu(ArrayList<FoodItem> list){
        this.list = list;

    }

    public void addFoodItem(FoodItem fooditem){
        list.add(fooditem);
    }

    public ArrayList<FoodItem> getList(){
        return list;
    }

    public void setList(ArrayList<FoodItem> list){
        this.list = list;
    }

}
class FoodItem {

    private int id;
    private String name;
    private int price;
    private FoodType foodType;

    public FoodItem(int id, String name, int price, FoodType foodType) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.foodType = foodType;

    }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getPrice(){
        return price;
    }

    public void setPrice(int price){
        this.price = price;
    }

    public FoodType getFoodType(){
        return foodType;
    }

    public void setFoodType(FoodType foodType){
        this.foodType = foodType;
    }

}

class Billing{

    private Order order;
    public Order getOrder(){
       return order;
    }
    public void setOrder(Order order){
        this.order = order;
    }


    public int calculateBill(Order order){
        int total = 0;

        for(FoodItem foodItem : order.getFoodItems()){
            total += foodItem.getPrice();
        }

        return total;
    }

    public Bill createBill() {

        int total = calculateBill(order);

        return new Bill(order, total);
    }

}

class Bill {

    private Order order;
    private int totalAmount;

    public Bill(Order order, int totalAmount) {
        this.order = order;
        this.totalAmount = totalAmount;
    }

    public int getTotalAmount() {
        return totalAmount;
    }
}