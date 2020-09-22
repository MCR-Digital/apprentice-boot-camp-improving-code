## Smell Two: Duplicate Code

+ This is a tricky one
+ Easiest to get wrong
+ Duplicate lines
+ Duplicate methods
+ Duplicate files
+ Duplicate knowledge

--

## Where does duplication come from?

+ Not knowing something already exists
+ Failing to extract constants
+ Copy pasta
+ Wanting something similar, but slightly different
+ Not understanding how to encapsulate knowledge in one place
+ Being in a hurry—introducing duplication is quicker
+ Being interrupted during proper work flow
    + Best to introduce duplication first then address it 

--

```java
protected Redirect validRequest(Context request) {
    Uri uri = request.getUri();
    String dealer = uri.getParameter("dealer").trim();
    String type = uri.getParameter("type").trim();
    …
    return new InternalRedirect(redirectUri);
}
protected Redirect invalidRequest(Context request) {
    String dealer = request.getUri().getParameter("dealer").trim();
    String type = request.getUri().getParameter("type").trim();
    …
    return new InternalRedirect(redirectUri);
}
```

Notes: Where is the duplication?

--

## Addressing duplication

+ [Extract Variable](https://refactoring.guru/extract-variable) / [Extract Method](https://refactoring.guru/extract-method) / [Extract Class](https://refactoring.guru/extract-class)
+ [Pull Up Method](https://refactoring.guru/pull-up-method)
+ Form a [Template Method](https://refactoring.guru/design-patterns/template-method)

Notes: Focus on extracting in this exercise  
    Pull up method and forming a template method requires an inheritance hierarchy