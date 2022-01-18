## Code Review

+ What is it?
+ Another form of feedback
+ *Compliments* automated review
+ Yet another heuristic

Notes:  
    Ask people what it is.  
    Who has received a review?  
    Who has delivered a review?  

--

<img src="https://mk0osnewswb2dmu4h0a.kinstacdn.com/images/comics/wtfm.jpg" alt="Meme describing the only valid code review metric as the number of WTFs a minute">
<figcaption>[Source: OSnews](https://www.osnews.com/story/19266/wtfsm/)</figcaption>

Notes: No such thing as perfection  

--

<img src="http://files.explosm.net/comics/Dave/comichighlighting1.png" alt="Comic with a joke about code review">
<figcaption>[Source: Cyanide & Happiness](http://explosm.net/comics/2083/)</figcaption>

Notes:
    Experiences of code review are not positive  

--

<blockquote class="twitter-tweet"><p lang="en" dir="ltr">Ask a programmer to review 10 lines of code, he&#39;ll find 10 issues. Ask him to do 500 lines and he&#39;ll say it looks good.</p>&mdash; Giray Özil (@girayozil) <a href="https://twitter.com/girayozil/status/306836785739210752?ref_src=twsrc%5Etfw">February 27, 2013</a></blockquote> 

--

<blockquote class="twitter-tweet"><p lang="en" dir="ltr">This is my most popular tweet, and it’s the one where I used the male pronoun for a programmer like a dumbass. Unintentional. Apologies.</p>&mdash; Giray Özil (@girayozil) <a href="https://twitter.com/girayozil/status/926579170976649216?ref_src=twsrc%5Etfw">November 3, 2017</a></blockquote> 

--

<img src="https://smartbear.com/SmartBear/media/images/product/collaborator/code-review-best-practices-figure-01.gif" width="600">
<background>white</background>
<figcaption>[From a study by Smartbear](https://smartbear.com/learn/code-review/best-practices-for-peer-code-review/)</figcaption>

Notes: Disclaimer: Smartbear are a code review product supplier  

--

## Why is code review a good thing?

+ Distributes knowledge and consensus
+ Great for personal development
+ Important for maintainability
+ Often part of a teams code workflow

Note: Ask people why code review is important

--

## When code review goes wrong…

+ Damaged confidence
+ Low team moral
+ Lack of trust
+ Poor work relationships
+ Input shutdown

Note: What do you think can happen when it goes wrong?  
    Important to establish culture of feedback in the team  
    People have to feel safe to be open and honest  

--

## Code review done badly

+ Used as opportunity to demonstrate experience
+ Linting
    + ‘Tabs -> spaces’
    + ‘Indentation is wrong’
+ Bikeshedding
    + Discussing name of a field in a class named `AbstractSingletonProxyFactoryBean`
+ Inconsistent standards/subjective comments 
    + ‘But the last review suggested using enums!’

Notes: Leave egos at the door  
    Linting should be done by an automated process  
    Explain bikeshedding (Parkinson’s law of triviality)  
    Possible to end up in a loop—consistency important  

--

> … the time spent on any item of the agenda will be in inverse proportion to the sum involved.  

—Parkinson's Law: The Pursuit of Progress

--

## A good code review is

+ Empathetic
  + Understand goal, positive intent, their situation, async vs in person, include praise, give & take
+ Informative & reasoned
  + ‘This might be easier to understand if…’ vs  
    ‘This is hard to understand’
+ Relevant & objective
  + Signal/noise ratio, concise, focused on changes, restrict to established norms, make `nitpick:` explicit

Notes: Possible to look back beyond commit in GitHub to check if they introduced an issue  
Be polite—read what you wrote and imagine being the audience

--

## Code review is hard

+ We’re generally bad at this
+ Fact: you **will** receive bad reviews
  + Only thing you are in control of is how you react
+ You can make it easier to deliver

Notes: arguably as important as how it’s delivered  

--

## How to receive a review

+ Appreciate the time given to you, submit small changes
+ Empathy just as important for you
  + Don’t be defensive—assume they’re trying to help
  + Reviewer might not be experienced
  + English might not be their first written language
+ Discussions what you don’t understand… face to face?
+ Resubmit to verify understanding—should get positive comments!

--

## Recap

+ As with any feedback: timely is better
+ Not too little and not too big
+ Okay to just say ‘LGTM’
+ Code review should focus on things that can’t be automated
+ Important to be kind and consider the person
+ Stick to things that matter
+ Avoid subjectivity

--

## What ways do we have to review code?

+ Synchronously: pairing
+ Asynchronously: GitHub

Note: Ask people for ideas

--

## Synchronous review: pairing

+ Review done collaboratively and implicitly
+ Navigator buffers rather than interrupting
+ Communicate design before implementing it
+ Shouldn’t feel like review
+ Keep it fresh… seek external review

--

## Asynchronous review: GitHub

+ Create reviews on Pull Requests
+ Request reviews from other contributors
+ Enforce reviews using protected branches

Note: 

--

## Creating a Pull Request review

+ From pull request click ‘Files changed’
+ Drag blue `+` sign over relevant lines & add comment
  + Suggestions are handy
    + Applying on GitHub resolves conversations
    + Can choose to batch into single commit
+ Click ‘Start a review’, then add more comments
+ Click ‘Review changes’ at the top right
+ Summarise & choose whether to ‘Approve’ or ‘Request changes’

Notes: Demo this using the dummy branch that exists  
    Can also comment on single commits  
    GH guide linked to in further reading  

---

## Exercise: use GitHub to review your neighbours code

+ Make sure your changes are all pushed
+ From your fork, click ‘Pull requests’
+ Click ‘New pull request’
  + Note that the [default is populated with the parent repos default branch](https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/proposing-changes-to-your-work-with-pull-requests/creating-a-pull-request#changing-the-branch-range-and-destination-repository)
+ Create a PR with a title like ‘Code review exercise: Jane Smith’

Notes:  
    If finish early, get them to explore protected branches  
    Discuss how it felt to receive the review  