## Smell One: Uncommunicative Name

> Naming things is hard

<div style="font-size:0.25em">\- https://martinfowler.com/bliki/TwoHardThings.html</div>

+ Bad names make things difficult to understand
+ May indicate a poor design

--

## Question: What are some qualities of good names?

Notes: For example:
  - help code read like a sentence  
  - describe meaning  
  - at the same level of abstraction

--

## Recognising bad names

+ Single letter names
+ Abbreviations
+ Say what they are rather than what they represent
    + e.g. ‘userList’ vs ‘usersLoggedIn’
+ Inconsistent
    + Different words for the same thing
    + Different levels of abstraction

--

```
boolean containsLabel(String name, List labels) {
    for (int i = 0; i < labels.size(); i++) {

        if ((labels.get(i).name.toLowerCase())
                .equals(name.toLowerCase())) {
            return true
        }
    }

    return false
}
```

--

## How to fix bad names?

+ Rename Variable
+ [Rename Method](https://refactoring.guru/rename-method)
+ Rename Class
