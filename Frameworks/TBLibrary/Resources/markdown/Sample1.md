## Test 1 (paragraph splitting)
This is the first paragraph.

And this is the second.

## Result 1
<p>This is the first paragraph.</p>

<p>And this is the second.</p>

## Test 2 (mixed UL elements)
+ a
* b
+ c

## Result 2
<ul>
<li>a</li>
<li>b</li>
<li>c</li>
</ul>

## Test 3 (mixed OL/UL elements)
+ a
2. b
+ c

## Result 3
<ul>
<li>a</li>
<li>b</li>
<li>c</li>
</ul>

## Test 4 (indented UL elements)
 + a
 + b
 + c

## Result 4
<ul>
<li>a</li>
<li>b</li>
<li>c</li>
</ul>

## Test 5 (stuff before and after list)
Some of my favourite movies are:

* Plan 9 From Outer Space
* Gigli
* Police Academy 6

but nobody else likes them.

## Result 5
<p>Some of my favourite movies are:</p>

<ul>
<li>Plan 9 From Outer Space</li>
<li>Gigli</li>
<li>Police Academy 6</li>
</ul>

<p>but nobody else likes them.</p>
