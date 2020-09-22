## Code Review

+ Another form of feedback
+ *Compliments* automated review
+ Yet another heuristic

Notes: Who has received a review?  
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

## Code review is critical

+ Great for personal development
+ Important for maintainability
+ Distributes knowledge and consensus
+ Often part of a teams code workflow

--

## When code review goes wrong…

+ Poor work relationships
+ Damaged confidence
+ Low team moral
+ Lack of trust
+ Input shutdown

Note: Important to establish culture of feedback in the team  
    This is a two way street  

--

## Code review done badly

+ Opportunity to demonstrate experience
+ Linting
    + ‘Tabs -> spaces’
    + ‘Indentation is wrong’
+ Bikeshedding
    + Discussing name of a field in a class named `AbstractSingletonProxyFactoryBean`
+ Contradictory advice
    + ‘But the last review suggested using enums!’

Notes: Leave egos at the door  
    Linting should be done by an automated process  
    Explain bikeshedding  
    Possible to end up in a loop—consistency important  

--

## Empathy

+ Person behind the change
    + People usually don’t set out to write bad code
+ Don’t stamp on their hard work
+ Were they under pressure?
+ Consider your rapport

Notes: Seniority can impact rapport  
    Where is the other developer in their journey?  
    What pressures were they under?  

--

## Opportunity to teach

Teaching takes more interaction than a simple comment

Notes: Might need a face-to-face conversation  
    Drawing might help  
    Consider repetition  

--

## Focus on things that matter

+ Signal / noise ratio important
+ People can only absorb so much

Notes: See further reading for ‘Decisions decisions’ talk about decisions that matter  

--

## Good practices: How to approach

+ Seek to understand the goal
+ Ask less experienced people to review your code
+ Read every line
+ Ask for large changes to be split into smaller chunks
+ Until you know each other, review in person
+ Take breaks every hour
+ Good to deliver reviews as well as receive

--

### Good practices: What to write

+ Be polite—read what you wrote and imagine being the audience
+ Praise good code (at least once per review)
+ Support rather than criticise
    Be positive: ‘This might be easier to understand if…’ instead of negative: ‘This code is hard to understand’

--

### Good practices: What to write (continued)

+ Don’t comment on issues they didn’t introduce
+ Avoid accusative language e.g. ‘why did you…’
+ Include reasoning
+ If nitpicking, be explicit e.g. `nit: this should be lower case`

Notes: Possible to look back beyond commit in GitHub to check if they introduced an issue  

--

## Code review is hard

+ We are generally bad at this
+ Fact: you **will** receive bad reviews
+ Only thing you are in control of is how you react
+ You can make it easier

Notes: arguably as important as how it’s delivered  

--

## How to receive a review

+ Seek feedback early
+ Appreciate the time given to you, help by making small changes
+ Drop your ego and don’t be defensive—it’s just code
+ Empathy just as important for you
    + They might not be experienced
    + English might not be their first language

--

## How to receive a review (continued)

+ Ask if you don’t understand—discussions are good
+ Face time can be much more efficient
+ Check back with the reviewer after addressing changes
    + This should generate some positive comments!

--

## A good code review is

+ Supportive
+ Concise
+ Focused
+ Objective

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

## Ways to review: pairing

+ Review is done collaboratively and implicitly
+ Navigator manages task list rather than interrupting flow
+ Talk about design before doing it
+ Shouldn’t feel like review
+ Shared understanding can make you code blind
+ Can be worth seeking external review

--

## Another way: using GitHub to review code

+ Comment on single commits
+ Create reviews on Pull Requests
+ Request reviews from other contributors
+ Enforce reviews using protected branches
+ See status checks inline with the PR

--

## Commenting on single commit

+ From commit, hover over a line you want to attach a comment to
+ Click blue `+` sign
+ Write the comment, using Markdown
+ Click ‘Add single comment’
+ User is notified

--

## Creating a Pull Request review

See [detailed instructions from GitHub](https://help.github.com/en/articles/reviewing-proposed-changes-in-a-pull-request) for more

+ In the pull request, click ‘Files changed’
+ Hover over a line you want to attach a comment to
+ Click blue `+` sign & write the comment, using Markdown
+ Click ‘Start a review’, then add more comments
+ Click ‘Review changes’ at the top right
+ Summarise & choose whether to ‘Approve’ or ‘Request changes’
+ User is notified

Notes: if finish early, get them to explore protected branches  

---

## Exercise: use GitHub to review your neighbours code

+ Need code to be pushed

Notes: Discuss how it felt to receive the review  