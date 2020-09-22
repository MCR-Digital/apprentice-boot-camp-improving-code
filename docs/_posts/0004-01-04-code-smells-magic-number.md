## Smell Three: Magic Number

+ Literal values in the code
+ Can’t understand meaning of the number from it’s value
+ e.g. OxDEADBEEF, 3.14159

Notes: First is a famous IBM error code, second is Pi  

--

## Why do we end up with magic numbers?

+ Sometimes natural way to write expressions
+ Don’t anticipate duplication
+ We know what the number means when we write it

--

## Recognising Magic Numbers

+ Can see literal values in the code
+ The magic numbers may be duplicated
+ Not all numbers are magic!
    + e.g. `for (int index = 0; … ; … ) {}`

--

```java
    public static Page from(String... pageIds) {
        if (pageIds.length == 2) {
            return from(pageIds[0], pageIds[1]);
        }
        return from(pageIds[0], "");
    }

    private boolean isInvalid(int statusCode) {
        return statusCode < 200 || statusCode >= 300;
    }
```

Notes: What magic numbers can you see here?

--

```java
public void addManufacturedYear(Vehicle vehicle) {
    Optional.ofNullable(vehicle)
        .map(Vehicle::getManufacturedYear)
        .filter(yearValue -> yearValue.toString().length() == 4)
        .ifPresent(keyFactsMap::addManufacturedYear);
}
```

Notes: Another magic number—what does it mean?

--

## Fixing Magic Numbers

+ [Replace Magic Number with Symbolic Constant](https://refactoring.guru/replace-magic-number-with-symbolic-constant)
+ [Replace Type Code with Class](https://refactoring.guru/replace-type-code-with-class)
+ [Replace Type Code with Subclasses](https://refactoring.guru/replace-type-code-with-subclasses)
+ [Replace Type Code with State/Strategy](https://refactoring.guru/replace-type-code-with-state-strategy)
+ Careful: not every use of a literal has the same meaning!

Notes: Usually can just get away with using a constant
    Magic numbers are sometimes used as types, e.g. final int CAR = 1, final int BIKE = 2…  
    Missing type  
    Where magic numbers are used in this way, using one of the other techniques is helpful as you can group behaviour with the type  