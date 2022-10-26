# Cartoon README

## Setup Guide:

Install JavaFX:



## Overview:

I went quite deep into JavaDocs for this
program, so there are quite a few design hurdles that  I jump in this
code, making the design section quite lengthy. The commenting
of the code also includes some of these design choices for better readability,
but these descriptions are pretty light. Therefore, I've got a much
shorter TLDR Design Section, followed by an in depth analysis of my code.

I was also going much more for functionality than efficiency in this program.
Many choices, which will be detailed more deeply, significantly decrease
program efficiency, but eliminate some small bugs that I wasn't otherwise
able to fix. With all that said, I hope you enjoy the thought process
behind what has been my single biggest project.


The program runs between two phases - the introduction, and then the galaxy
itself. The introduction brings in a 2d sprite which gives some directions
to the reader, and the galaxy features 8 spinning planets which orbit
the sun, all of which can be interacted with by the user.

## TLDR Design:

At its lowest level, this program is 8 spheres moving in a circular
path around a single, stationary point, where there lies a stationary
sphere. From the `Cartoon` class, each planet is initialized, set
on its orbital path, and assigned to key inputs such that it can
respond to inputs, either with some change in behavior, or with an 
output of information. **Guide to where to find the full
functionality elements at the bottom**

## Design:
### Intro Sequence:
Note that the majority of the initial 2d sequence is just the code we
were shown in class. Of note is just the "scrolling" implementation and
the tying of some text to the sprite `Astro`. About the scrolling,
the simple solution to get this visual effect was just displaying the
x, x+1th, and x+2th element of an array of strings `ASTRONAUT_DIALOGUE`,
and iterating up x on `[SPACE]`. I also chose to tie the 'instructions'
pane (with text `[SPACE] to...`) to my sprite, this was just a visual
preference - I like how it looks when the sprite moves with the instructions.

Now to the more interesting code.

### Implementing Orbiting "The Conveyor Belt"

The coolest thing in this program is the orbiting planets. I no luck 
scouring javaFX for anything orbit related (as I did with
the planet skins), so I implemented it myself.

The method used for this is best illustrated in the following
[graph](https://www.desmos.com/calculator/pcd6vj1mgl). The z-axis
in the planetary system is the y axis in this graph. The camera sits a bit behind
point `(187.5, -187.5)`. Therefore, if the planet were to simply
follow the path of this circle, it would orbit point `(187.5, 0)` ->
this is where the sun sits.

The implementation for this is about as straightforward as you would
expect: class `CircleCalculations` is used to generate 750 points that follow
this circle (using the equation for a circle (187.5)<sup>2</sup> = (x-187.5)<sup>2</sup> +
y<sup>2</sup>). This outputs a 2 dimensional array[x][z], where the nth
element in the list represents point n, and x and z denote the x and z coordinates.

After this, each planet is assigned an index value (say, 0), where it starts.
Every .02 seconds, the planets index variable increments, and its position updates
based on this variable (moves from position 1 to position 2). When reaching the end 
of the list (point 750), the planet's index variable is set back to 0.

The challenge with this is that `JavaFX` doesn't automatically set the order
of the planets - even if a planet is further from the camera than another,
it will still be shown as being in front of that planet. The solution to this
is just to check if a planet has a negative Z-value (closer to camera), or
positive Z-value (further from camera), in which case it is moved back. Interestingly,
all of this happens in the planets themselves. This is such that the planets don't have
to be initialized as instance variables and can just exist independently.
(though this approach has its downsides, as discussed below).
### Scope (and its flaws)
Yes. There are global variables accessed by multiple levels of logic.
Specifically the paused variable. This is the part of my program that frustrates
me the most, and it's due to an underlying design choice about not
creating instance variables for my planets. One will notice that the process
 of instantiating a planet is very dense, directly requiring the methods
`addPlanet`, and `addPlanet`, as well as class `Planet`. It was therefore
that I decided not to write a thousand lines of instantiation, and simply
set my planets as local variables. The consequences of this are
discussed further below, but one of these is that it became much
more difficult to query planets, meaning that a global variable
that could be changed without 3 layers of communication between
`Cartoon` class and `Planet`s was made much simpler.
### Planets Sending & Receiving Information (continuation of scope)
In an effort to keep planets as independant as possible, most of the
information that they take in are done either A: inside of themselves or 
B: with the creation of new planets. The planets themselves are looking
for the information of a click, and when the planets skins change, it is
instead new planets that are spawned in with the desired skins.

The exceptions to this are the planet facts and "statistics" (though
it is only the earth that has rotation & orbiting stats, all planets
are recording these values for future expansion of the program). These
are tied to timelines in the `makePlanet` method perhaps the
most important of all the code - if you are to look at any code,
look at that code). this is done while the planets are "fresh out of the oven."
(haven't been trash collected yet).
### The Sun
One will notice many "!IsSun" lines scattered around the code, and
many more "IsSuns" being passed in. These are because of the particularity
that is the sun - it features the characteristics of a planet (skin, rotating,
), but lacks orbiting. The natural solution to this would be to create
a class `Sun` as a child of the planet class, but this is not as easy
as would seem, as the 'makePlanet' method which is so key to the program
wouldn't work on such an object. It is therefore that this concession
is made, perhaps to be fixed in future versions of the code (if
there are any).
### Andy Mode
My favorite part of the program. Unfortunately, however, the
implementation of this is not so as as just changing the skins
of all the planets, as the planets themselves are trash collected 
as variables (but still exist visually). Therefore, changing
the textures like this is done by spawning in a set of new planets
(with the desired texture of our great professor's face).
### `throws FileNotFoundException`
This, along with the "try, catch" lines throughout the code are
necessary for the image importing API (or else program just crashes)
if a non-existent image is inputted).

Regarding images, I initially had an images folder, but this wasn't
compatible with codepost. If there are any issues with images, check
Constants file for paths.
## Full Functionality:

This program is long. Here's what you're looking for.

**Composite Shape** - Astro (detailed in file `Astronaut.java`)
- Made up of 1 square and 5 Ellipses
- Acts independently on timeline to move in method `runIntroduction` in file `PaneOrganizer.java`

**Layout Elements**
- Program uses java containers throughout program. E.x. `Borderpane root` passed into `Cartoon`
**Changing Label**
- Planet info labels `planetRotations` and `planetOrbits` created in `setUpInfoDisplay` in `Cartoon`
update as earth rotates & orbits sun.
**Event Handler** - `moveIntroduction` in `PaneOrganizer`
- scrolls through dialogue lines using keyboard input `[space]` and `[S]`
**Quit Button** - `quitButton` in method `setUpCommandBox` in class `Cartoon`
## Debugging Collaborators:
Emil

## Known Bugs:
None
## Time Spent on Project:
20 hours