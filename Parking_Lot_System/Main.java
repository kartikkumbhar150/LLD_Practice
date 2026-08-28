package Parking_Lot_System;

import java.time.LocalDateTime;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

// ===================== MAIN =====================

public class Main {

    public static void main(String[] args) {

        ParkingLot parkingLot = new ParkingLot();

        parkingLot.addSpot(new ParkingSpot(1, SpotType.BIKE));
        parkingLot.addSpot(new ParkingSpot(2, SpotType.CAR));
        parkingLot.addSpot(new ParkingSpot(3, SpotType.LARGE));

        Vehicle car = new Car("MH12AB1234");
        Vehicle bike = new Bike("MH12XY5678");
        Vehicle truck = new Truck("MH14TR9999");

        ParkingTicket carTicket = parkingLot.parkVehicle(car);
        ParkingTicket bikeTicket = parkingLot.parkVehicle(bike);
        ParkingTicket truckTicket = parkingLot.parkVehicle(truck);

        System.out.println();

        carTicket.displayTicketDetails();
        bikeTicket.displayTicketDetails();
        truckTicket.displayTicketDetails();

        System.out.println();

        parkingLot.removeVehicle(carTicket.getTicketId(), PaymentType.UPI);
    }
}


// ===================== ENUMS =====================

enum VehicleType {
    BIKE,
    CAR,
    TRUCK
}

enum SpotType {
    BIKE,
    CAR,
    LARGE
}

enum PaymentType {
    UPI,
    CASH,
    CARD
}


// ===================== VEHICLE =====================

abstract class Vehicle {

    private String vehicleNumber;
    private VehicleType vehicleType;

    public Vehicle(String vehicleNumber, VehicleType vehicleType) {
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public abstract boolean canParkIn(SpotType spotType);
}


// ===================== CAR =====================

class Car extends Vehicle {

    public Car(String vehicleNumber) {
        super(vehicleNumber, VehicleType.CAR);
    }

    @Override
    public boolean canParkIn(SpotType spotType) {
        return spotType == SpotType.CAR ||
               spotType == SpotType.LARGE;
    }
}


// ===================== BIKE =====================

class Bike extends Vehicle {

    public Bike(String vehicleNumber) {
        super(vehicleNumber, VehicleType.BIKE);
    }

    @Override
    public boolean canParkIn(SpotType spotType) {
        return spotType == SpotType.BIKE ||
               spotType == SpotType.LARGE;
    }
}


// ===================== TRUCK =====================

class Truck extends Vehicle {

    public Truck(String vehicleNumber) {
        super(vehicleNumber, VehicleType.TRUCK);
    }

    @Override
    public boolean canParkIn(SpotType spotType) {
        return spotType == SpotType.LARGE;
    }
}


// ===================== PARKING SPOT =====================

class ParkingSpot {

    private int spotId;
    private SpotType spotType;
    private boolean occupied;
    private Vehicle vehicle;

    public ParkingSpot(int spotId, SpotType spotType) {
        this.spotId = spotId;
        this.spotType = spotType;
        this.occupied = false;
    }

    public int getSpotId() {
        return spotId;
    }

    public SpotType getSpotType() {
        return spotType;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void park(Vehicle vehicle) {

        if (occupied) {
            System.out.println("Spot is already occupied");
            return;
        }

        this.vehicle = vehicle;
        this.occupied = true;
    }

    public void removeVehicle() {
        this.vehicle = null;
        this.occupied = false;
    }
}


// ===================== PARKING TICKET =====================

class ParkingTicket {

    private int ticketId;
    private Vehicle vehicle;
    private ParkingSpot parkingSpot;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;

    public ParkingTicket(
            int ticketId,
            Vehicle vehicle,
            ParkingSpot parkingSpot
    ) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.parkingSpot = parkingSpot;
        this.entryTime = LocalDateTime.now();
    }

    public int getTicketId() {
        return ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    public LocalDateTime getExitTime() {
        return exitTime;
    }

    public void closeTicket() {
        this.exitTime = LocalDateTime.now();
    }

    public void displayTicketDetails() {

        System.out.println("----- Parking Ticket -----");
        System.out.println("Ticket ID: " + ticketId);
        System.out.println("Vehicle Number: "
                + vehicle.getVehicleNumber());
        System.out.println("Vehicle Type: "
                + vehicle.getVehicleType());
        System.out.println("Spot ID: "
                + parkingSpot.getSpotId());
        System.out.println("Entry Time: "
                + entryTime);
        System.out.println("Exit Time: "
                + exitTime);
    }
}


// ===================== PAYMENT INTERFACE =====================

interface Payment {

    void pay(double amount);
}


// ===================== PAYMENT IMPLEMENTATIONS =====================

class UPIPayment implements Payment {

    @Override
    public void pay(double amount) {
        System.out.println(
                "₹" + amount + " paid using UPI"
        );
    }
}


class CashPayment implements Payment {

    @Override
    public void pay(double amount) {
        System.out.println(
                "₹" + amount + " paid using Cash"
        );
    }
}


class CardPayment implements Payment {

    @Override
    public void pay(double amount) {
        System.out.println(
                "₹" + amount + " paid using Card"
        );
    }
}


// ===================== PARKING FEE =====================

class ParkingFeeCalculator {

    public double calculateFee(ParkingTicket ticket) {

        LocalDateTime exitTime = ticket.getExitTime();

        if (exitTime == null) {
            return 0;
        }

        long minutes = Duration.between(
                ticket.getEntryTime(),
                exitTime
        ).toMinutes();

        long hours = Math.max(1, (minutes + 59) / 60);

        double rate;

        switch (ticket.getVehicle().getVehicleType()) {

            case BIKE:
                rate = 20;
                break;

            case CAR:
                rate = 40;
                break;

            case TRUCK:
                rate = 80;
                break;

            default:
                rate = 0;
        }

        return hours * rate;
    }
}


// ===================== PARKING LOT =====================

class ParkingLot {

    private List<ParkingSpot> spots;
    private List<ParkingTicket> tickets;

    private int nextTicketId = 1;

    private ParkingFeeCalculator feeCalculator;

    public ParkingLot() {

        spots = new ArrayList<>();
        tickets = new ArrayList<>();

        feeCalculator = new ParkingFeeCalculator();
    }

    public void addSpot(ParkingSpot spot) {
        spots.add(spot);
    }

    public ParkingTicket parkVehicle(Vehicle vehicle) {

        for (ParkingSpot spot : spots) {

            if (!spot.isOccupied()
                    && vehicle.canParkIn(spot.getSpotType())) {

                spot.park(vehicle);

                ParkingTicket ticket =
                        new ParkingTicket(
                                nextTicketId++,
                                vehicle,
                                spot
                        );

                tickets.add(ticket);

                System.out.println(
                        vehicle.getVehicleNumber()
                                + " parked at spot "
                                + spot.getSpotId()
                );

                return ticket;
            }
        }

        System.out.println(
                "No suitable parking spot available"
        );

        return null;
    }

    public void removeVehicle(
            int ticketId,
            PaymentType paymentType
    ) {

        ParkingTicket ticket = findTicket(ticketId);

        if (ticket == null) {
            System.out.println("Ticket not found");
            return;
        }

        ticket.closeTicket();

        double fee =
                feeCalculator.calculateFee(ticket);

        Payment payment;

        switch (paymentType) {

            case UPI:
                payment = new UPIPayment();
                break;

            case CASH:
                payment = new CashPayment();
                break;

            case CARD:
                payment = new CardPayment();
                break;

            default:
                System.out.println("Invalid payment type");
                return;
        }

        payment.pay(fee);

        ticket.getParkingSpot().removeVehicle();

        System.out.println(
                ticket.getVehicle().getVehicleNumber()
                        + " removed from parking"
        );

        tickets.remove(ticket);
    }

    private ParkingTicket findTicket(int ticketId) {

        for (ParkingTicket ticket : tickets) {

            if (ticket.getTicketId() == ticketId) {
                return ticket;
            }
        }

        return null;
    }
}