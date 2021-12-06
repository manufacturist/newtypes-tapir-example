# Domain types
Our domain covers actors & movies. Therefore, the types explained here are relevant
to their respective subdomains.

## ActorId
Internal identifier for an Actor within our system. It is a UUID.

## MovieId
Internal identifier for a Movie within our system. It is a UUID.

## ActorCode
A unique actor identifier. It has the following structure:
* Starts with 3 capital letters
* Ends with 3 digits

e.g. `ABC123`

*^ This is a made up identifier*

## FullName
Concatenated string from at least a first name & a last name, separated by a whitespace. 
Our system does **NOT** support mononymous individuals (known by a single name, e.g. Morpheus).

## Title
Represents the name of a movie. It can't be longer than 250 characters.

## Year
A naive lower limit of 1788 is set. The "first" movie appeared in 1888. Subtracting 100 
years seemed to be a safe limit to also cover an actor's year of birth.

## Age
A number between 0 and 120. The upper limit must be increased every century.
This is an obsolete type and should be replaced with date of birth in the near future.