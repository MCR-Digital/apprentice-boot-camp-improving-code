## Beware differing formats

+ IDEs have built in code styles
+ Some developers tweak these
+ Possible to have contradictory settings in a team
  + Good to be consistent around this (swallow your ego)
+ Reformatting a codebase makes it look like you’ve changed more than you have
  + This can confuse code reviewers

--

## Separate style changes from substance

+ Reformat in separate commit
  + Might be good before restarting work on a dormant codebase
+ Be cautious—reformatting obscures blame
+ IDEs may automatically reformat
    + Review your changes
+ Change the minimum—leave whitespace changes out

--

## Sometimes okay to fix everything

+ Linting tools can sometimes resolve issues for you
+ Not a good idea on an existing codebase
+ Good to implement early on with greenfield projects

--

## Don’t reformat today!

+ Going to be doing a code review exercise
+ Important that you don’t reformat the code
+ Doing so will create lots of noise for your reviewer