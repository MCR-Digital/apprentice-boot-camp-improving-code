## Exercise: Fix these smells (60 mins)

* Fix one smell at a time and run test after each change
* Commit if green with useful message (don’t push!)
* Revert if red (you shouldn’t get a failure with refactoring)

| Smell | Refactorings |
| ----- | ------------ |
| [Long method](https://refactoring.guru/smells/long-method) | [Extract method](https://refactoring.guru/extract-method), [Replace temp with query](https://refactoring.guru/replace-temp-with-query), [Introduce parameter object](https://refactoring.guru/introduce-parameter-object), [Decompose conditional](https://refactoring.guru/decompose-conditional), [Replace method with method object](https://refactoring.guru/replace-method-with-method-object) |
| Conditional complexity | [Simplify conditional expressions](https://refactoring.guru/refactoring/techniques/simplifying-conditional-expressions) (has multiple options) |
| [Large class](https://refactoring.guru/smells/large-class) | [Extract Class](https://refactoring.guru/extract-class), [Extract Subclass](https://refactoring.guru/extract-subclass) |
<!-- .element: style="font-size: 80%" -->

Notes:
    Keep changes small  
    Commit little and often  
    Keep running the tests  