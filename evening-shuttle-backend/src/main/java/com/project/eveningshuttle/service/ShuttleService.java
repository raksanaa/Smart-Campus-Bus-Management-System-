// package com.project.eveningshuttle.service;

// import java.util.*;
// import java.util.concurrent.*;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import com.project.eveningshuttle.model.runtime.Passenger;

// @Service
// public class ShuttleService {

//     public static final int CAMPUS_STOP = 0;
//     public static final int STOP_1 = 1;
//     public static final int STOP_2 = 2;

//     private Queue<Passenger> mainQueue;
//     private HashMap<Integer, Passenger> pendingMap;

//     private ScheduledExecutorService scheduler;
//     private final ShuttleLocationService shuttleLocationService;

//     private boolean isWaiting;
//     private boolean isInTransit;
//     private boolean boardingStarted;
//     private long waitStartTime;

//     private final long WAIT_DURATION_MS = 30_000;

//     @Autowired
//     public ShuttleService(ShuttleLocationService shuttleLocationService) {
//         mainQueue = new LinkedList<>();
//         pendingMap = new HashMap<>();
//         scheduler = Executors.newSingleThreadScheduledExecutor();
//         isWaiting = false;
//         isInTransit = false;
//         boardingStarted = false;
//         this.shuttleLocationService = shuttleLocationService;
//     }

//     public boolean isInTransit() {
//         return isInTransit;
//     }

//     public synchronized String enqueuePendingPassenger(Passenger passenger) {
//         pendingMap.put(passenger.getSuid(), passenger);
//         return "Passenger Booked.";
//     }

//     public synchronized String addPassenger(Passenger passenger) {
//         int suid = passenger.getSuid();

//         if (isInTransit) {
//             return "Cannot board. Shuttle is currently in transit.";
//         }

//         if (!pendingMap.containsKey(suid)) {
//             if (mainQueue.stream().anyMatch(p -> p.getSuid() == suid)) {
//                 return "Passenger already boarded";
//             }
//             return "SUID: " + suid + " did not request pickup";
//         }

//         pendingMap.remove(suid);
//         mainQueue.add(passenger);

//         if (!boardingStarted) {
//             startWaitTimer();
//         }

//         return "Passenger Added";
//     }

//     public synchronized boolean containsSuid(int suid) {
//         return mainQueue.stream().anyMatch(p -> p.getSuid() == suid)
//                 || pendingMap.containsKey(suid);
//     }

//     private synchronized void startWaitTimer() {
//         boardingStarted = true;
//         isWaiting = true;
//         waitStartTime = System.currentTimeMillis();
//         System.out.println("Shuttle is waiting for more passengers...");
//         scheduler.schedule(this::startJourney, WAIT_DURATION_MS, TimeUnit.MILLISECONDS);
//     }

//     private synchronized void startJourney() {
//         isWaiting = false;
//         isInTransit = true;
//         pendingMap.clear();

//         System.out.println("Shuttle journey started with " + mainQueue.size() + " passengers.");

//         scheduler.execute(() -> {
//             try {
//                 Passenger p = mainQueue.peek();
//                 if (p != null && p.getAddress() == STOP_1) {
//                     dropOffAtStop(STOP_1);
//                     Thread.sleep(3_000);
//                     dropOffAtStop(STOP_2);
//                     Thread.sleep(3_000);
//                     System.out.println("Returning to campus stop...");
//                     Thread.sleep(2_000);
//                 } else {
//                     dropOffAtStop(STOP_2);
//                     Thread.sleep(3_000);
//                     dropOffAtStop(STOP_1);
//                     Thread.sleep(3_000);
//                     System.out.println("Returning to campus stop...");
//                     Thread.sleep(2_000);
//                 }
//             } catch (InterruptedException e) {
//                 e.printStackTrace();
//             }
//             endJourney();
//         });
//     }

//     private synchronized void endJourney() {
//         isInTransit = false;
//         mainQueue.clear();
//         boardingStarted = false;

//         System.out.println("Shuttle returned to the Campus Stop.");

//     }

//     private synchronized void dropOffAtStop(int stopNumber) {
//         Iterator<Passenger> iterator = mainQueue.iterator();
//         while (iterator.hasNext()) {
//             Passenger p = iterator.next();
//             if (p.getAddress() == stopNumber) {
//                 System.out.println("Dropped off SUID " + p.getSuid() + " at Stop " + stopNumber);
//                 iterator.remove();
//             }

//         }
//     }

//     public synchronized String getETA() {
//         if (isInTransit) {
//             return "ETA " + shuttleLocationService.ETA + " seconds. Shuttle is in transit.";
//         } else if (boardingStarted) {
//             long remaining = WAIT_DURATION_MS - (System.currentTimeMillis() - waitStartTime);
//             return "ETA 0 seconds. Board at the Campus Stop within the next " + Math.max(remaining / 1000, 0)
//                     + " seconds.";
//         } else if (mainQueue.isEmpty()) {
//             return "ETA 0 seconds. Board at the Campus Stop";
//         } else {
//             return "Service Unavailable";
//         }
//     }
// }

package com.project.eveningshuttle.service;

import java.util.*;
import java.util.concurrent.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.eveningshuttle.model.runtime.Passenger;
import com.project.eveningshuttle.state.*;

@Service
public class ShuttleService {

    public static final int CAMPUS_STOP = 0;
    public static final int STOP_1 = 1;
    public static final int STOP_2 = 2;

    private Queue<Passenger> mainQueue;
    private HashMap<Integer, Passenger> pendingMap;

    private ScheduledExecutorService scheduler;
    private final ShuttleLocationService shuttleLocationService;

    private ShuttleState currentState;
    private long waitStartTime;
    private final long WAIT_DURATION_MS = 30_000;

    @Autowired
    public ShuttleService(ShuttleLocationService shuttleLocationService) {
        mainQueue = new LinkedList<>();
        pendingMap = new HashMap<>();
        scheduler = Executors.newSingleThreadScheduledExecutor();
        this.shuttleLocationService = shuttleLocationService;
        this.currentState = new IdleState();
    }

    public ShuttleLocationService getShuttleLocationService() {
        return shuttleLocationService;
    }

    public boolean isInTransit() {
        return currentState instanceof com.project.eveningshuttle.state.InTransitState;
    }

    public void moveToIdleState() {
        currentState = new IdleState();
    }

    public void moveToBoardingState() {
        currentState = new BoardingState();
        this.waitStartTime = System.currentTimeMillis();
        scheduler.schedule(() -> currentState.handleStartJourney(this),
                WAIT_DURATION_MS, TimeUnit.MILLISECONDS);
        System.out.println("Shuttle is waiting for more passengers...");
    }

    public void moveToInTransitState() {
        currentState = new InTransitState();
        this.pendingMap.clear();
        System.out.println("Shuttle journey started with " + mainQueue.size() + "passengers.");
    }

    public synchronized String enqueuePendingPassenger(Passenger passenger) {
        pendingMap.put(passenger.getSuid(), passenger);
        return "Passenger Booked.";
    }

    public synchronized String addPassenger(Passenger passenger) {
        return currentState.handleAddPassenger(this, passenger);
    }

    public String addPassengerInternal(Passenger passenger) {
        int suid = passenger.getSuid();

        if (!pendingMap.containsKey(suid)) {
            if (mainQueue.stream().anyMatch(p -> p.getSuid() == suid)) {
                return "Passenger already boarded";
            }
            return "SUID: " + suid + " did not request pickup";
        }

        pendingMap.remove(suid);
        mainQueue.add(passenger);
        return "Passenger Added";
    }

    public synchronized boolean containsSuid(int suid) {
        return mainQueue.stream().anyMatch(p -> p.getSuid() == suid) ||
                pendingMap.containsKey(suid);
    }

    public void beginShuttleJourney() {
        scheduler.execute(() -> {
            try {
                Passenger p = mainQueue.peek();
                if (p != null && p.getAddress() == STOP_1) {
                    dropOffAtStop(STOP_1);
                    Thread.sleep(3_000);
                    dropOffAtStop(STOP_2);
                    Thread.sleep(3_000);
                } else {
                    dropOffAtStop(STOP_2);
                    Thread.sleep(3_000);
                    dropOffAtStop(STOP_1);
                    Thread.sleep(3_000);
                }
                System.out.println("Returning to campus stop...");
                Thread.sleep(2_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            endJourney();
        });
    }

    private synchronized void endJourney() {
        mainQueue.clear();
        moveToIdleState();
        System.out.println("Shuttle returned to the Campus Stop.");
    }

    private synchronized void dropOffAtStop(int stopNumber) {
        Iterator<Passenger> iterator = mainQueue.iterator();
        while (iterator.hasNext()) {
            Passenger p = iterator.next();
            if (p.getAddress() == stopNumber) {
                System.out.println("Dropped off SUID " + p.getSuid() + " at Stop " +
                        stopNumber);
                iterator.remove();
            }
        }
    }

    public long getWaitRemaining() {
        return WAIT_DURATION_MS - (System.currentTimeMillis() - waitStartTime);
    }

    public synchronized String getETA() {
        return currentState.handleGetETA(this);
    }
}
