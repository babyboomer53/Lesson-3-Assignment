# Lesson-3-assignment

The  Lesson3Streams class contains methods for counting words in a text file.
By  default, Lesson3Streams counts the number of 16-digit hexadecimal numbers
in a file by searching for character strings matching the regular expression,
"[0]{8}[0-9a-f]{8}".  An example  of a sequence that would match this pattern
is "00000000afc9db01".

When  instantiated, objects of the Lesson3Streams class must pass an argument
to  the  constructor specifying the  name of  the input file. Optionally, the
constructor  will  accept a  regular expression as  the second argument. When
specified,  the  second argument will be  used instead of the default pattern
(see previous paragraph).

The Lesson3Streams class uses Java's Streams and stream pipelines to search a
text file and return the number of times a sequence of characters matches the
regular  expression pattern.  Lesson3Streams  provides this  function in  two
methods.  One method utilizes a sequential stream, while the other utilizes a
parallel   stream.   A  parallel   stream  can  utilize  the  multi-threading
capabilities  provided  by  computers containing  multiple  processor  cores;
typically resulting in enhanced performance.

This  program expects  a single argument containing the name of the text file
from  which  the data  will be read.  When the argument  is omitted, an error
message  and a  stack trace will be  displayed on the console and the program
exits immediately.

When  invoked,  Lesson3Streams processes the input  file twice – once using a
sequential  stream,  and again using a  parallel stream. Next, it doubles the
size  of the  data and repeats the two steps. Lesson3Streams will repeat this
process  until the parallel stream runs faster than the sequential stream, or
until it has completed ten iterations.
