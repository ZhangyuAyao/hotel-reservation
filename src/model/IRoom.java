package model;

public interface IRoom {
    public String getRoomNumber();
    public Double getRoomPrice();
    public RoomType getRoomType();
    public Boolean isFree();
    default void show(){
        System.out.println("I am new here by default access modifier");
    }
}
