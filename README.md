# QT-Challenge-2020

Servers, Laptops, Mobile devices and even Web browsers now all offer runtime environments with access to multi-core CPUs (including GPUs). We should consider how to leverage the capabilities of these runtimes in solutions where compute efficiency is required.

Please code a solution in the language, and on the platform, of your choice. The goal is to process the attached file in the most efficient way you can think of while utilising the compute resources available in your runtime of choice.

The attached file consists of rows of comma separated numbers which will be your input.

e.g.

2323,8349348,121,33

948,4,312623

...

Each line may have a longer or shorter list of numbers with a maximum of 1000 per row. Each number is between zero and 1 million.

The output of your code should be the same number of rows as the input with 5 columns each and show the original row number with some aggregated data: the count of numbers in the row, the average for that row, the max number, the min number.

i.e.

&lt;row&gt;,&lt;count&gt;,&lt;avg&gt;,&lt;max&gt;,&lt;min&gt;

e.g. (using the 2 lines above)

1,4,2087956.25,8349348,33

2,3,104525,312623,4

...

Finally the output order does not need to be the same as the input row order. Precision only to 2 decimal places.

Things to consider; size and maintainability of your code, constrained use of dependencies to reduce cost of cyber scanning/patching, unit tests, effective use of the concurrency features of your chosen runtime and language, how to balance aggressive resource utilisation with other critical aspects of the runtime, sharing of memory or message passing between threads, use of pure functions and higher order functions, evidence that multiple threads are being used and a measure of total time your solution takes on typical hardware. 