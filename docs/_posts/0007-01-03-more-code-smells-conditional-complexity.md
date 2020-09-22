## Smell Five: Conditional complexity

+ Deeply nested `if else` statements
```
if (!model.matches(".*\\d+.*")) {
    if (model.length() < 4) {
        model = model.toUpperCase();
    } else if (model.charAt(2) == ' ') {
        model = model.substring(0, 3).toUpperCase() + initCap(model.substring(3));
    } else {
        model = initCap(model);
    }
}
```
+ code can be wide as well as long
+ `switch` statements

--

## No `if`s no buts or `else`!

+ How many conditionals are acceptable?
+ Be _if-ist_
+ Frequently seen in the wild

Notes:
Highlight the opposing points of view form OO purists to common attitudes.  

--

## How to resolve conditional complexity

+ [Decompose Conditional](https://refactoring.guru/decompose-conditional)
+ [Consolidate Conditional Expression](https://refactoring.guru/consolidate-conditional-expression)
+ [Consolidate Duplicate Conditional Fragments](https://refactoring.guru/consolidate-duplicate-conditional-fragments)
+ [Remove Control Flag](https://refactoring.guru/remove-control-flag)
+ [Replace Conditional with Polymorphism](https://refactoring.guru/replace-conditional-with-polymorphism)
+ [Complete list and when to use each here](https://refactoring.guru/refactoring/techniques/simplifying-conditional-expressions)

--

## Example: Replace Conditional with Polymorphism

```java
class Vehicle {
    String vehicleType;
}

...

if (vehicleType.equals("CARS") {
    return "Vehicle Check Available";
} else if (vehicleType.equals("BIKE") {
    return "Vehicle Check Coming Soon!";
} else {
    return "Vehicle Check Unavailable";
}
```

--

```java
class Vehicle {
    String getVehicleCheckStatus(){
        return "Vehicle Check Unavailable";
    }
}
class Car extends Vehicle{
    String getVehicleCheckStatus(){
        return "Vehicle Check Available";
    }
}
class Bike extends Vehicle{
    String getVehicleCheckStatus(){
        return "Vehicle Check Coming Soon!";
    }
}
```

--

```java

if (vehicleType.equals("CARS") {
    return "Vehicle Check Available";
} else if (vehicleType.equals("BIKE") {
    return "Vehicle Check Coming Soon!"
} else {
    return "Vehicle Check Unavailable";
}
```
vs

```java
vehicle.getVehicleCheckStatus();
```