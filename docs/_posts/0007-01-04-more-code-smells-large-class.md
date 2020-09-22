## Smell Six: Large Class

+ Similar to a long method
+ Too many fields
+ Too many methods
+ Too many lines of code
+ Too many responsibilities
+ Duplication is likely

<backgroundimage>https://upload.wikimedia.org/wikipedia/commons/thumb/f/fd/Textured_spork.png/512px-Textured_spork.png</backgroundimage>
<!-- .slide: data-background-size="512px 512px" -->
<!-- .slide: data-background-repeat="repeat" -->
<!-- .slide: data-background-opacity="0.3" -->


<div style="font-size: 0.25em">
    Spork.jpg: Jason L. Gohlkederivative work: Plasticspork [<a href="https://creativecommons.org/licenses/by-sa/2.5">CC BY-SA 2.5</a>], <a href="https://commons.wikimedia.org/wiki/File:Textured_spork.png">via Wikimedia Commons</a>
</div>


--

## How do large classes happen?

+ Similar to long methods
    * They grow over time
+ Indicate an absence of refactoring
+ Not knowing how to decompose responsibility

--

## How to fix them?

Generally, split them up!

+ [Extract Class](https://refactoring.guru/extract-class) helps if part of the behavior of the large class can be spun off into a separate component.
+ [Extract Subclass](https://refactoring.guru/extract-subclass) helps if part of the behavior of the large class can be implemented in different ways or is used in rare cases.
+ See [Large Class](https://refactoring.guru/smells/large-class)

Note: First step must be to establish what should be the true single responsibility.  
    With a large class more responsibility will have crept in.  
    Work that out, then move the rest elsewhere.  
    Might be too confused to refactor.  
