## Test 1 (Phrase Emphasis)
*italic*   **bold**
_italic_   __bold__

## Result 1
<p><em>italic</em>   <strong>bold</strong>
<em>italic</em>   <strong>bold</strong></p>

## Test 2 (Links/Inline)
An [example](http://url.com/ "Title")

## Result 2
<p>An <a href="http://url.com/" title="Title">example</a></p>

## Test 3 (Links/Inline, no title)
An [example](http://www.example.com/)

## Result 3
<p>An <a href="http://www.example.com/">example</a></p>

## Test 4 (setext-style headers)
Header 1
========

Header 2
--------

## Result 4
<h1>Header 1</h1>

<h2>Header 2</h2>

## Test 5 (atx-style headers)
# Header 1 #

## Header 2 ##

###### Header 6

## Result 5
<h1>Header 1</h1>

<h2>Header 2</h2>

<h6>Header 6</h6>

## Test 6 (Ordered lists)
1.  Foo
2.  Bar

## Result 6
<ol>
<li>Foo</li>
<li>Bar</li>
</ol>

## Test 7 (unordered lists)
*   A list item.

    With multiple paragraphs.

*   Bar

## Result 7
<ul>
<li><p>A list item.</p>

<p>With multiple paragraphs.</p></li>
<li><p>Bar</p></li>
</ul>

## Test 8 (nested lists)
*   Abacus
    * ass
*   Bastard
    1.  bitch
    2.  bupkis
        * BELITTLER
    3. burper
*   Cunning

## Result 8
<ul>
<li>Abacus
<ul><li>ass</li></ul></li>
<li>Bastard
<ol><li>bitch</li>
<li>bupkis
<ul><li>BELITTLER</li></ul></li>
<li>burper</li></ol></li>
<li>Cunning</li>
</ul>

## Test 9 (blockquotes)
> Email-style angle brackets
> are used for blockquotes.

> > And, they can be nested.

> #### Headers in blockquotes
> 
> * You can quote a list.
> * Etc.

## Result 9
<blockquote>
  <p>Email-style angle brackets
  are used for blockquotes.</p>
  
  <blockquote>
    <p>And, they can be nested.</p>
  </blockquote>
  
  <h4>Headers in blockquotes</h4>
  
  <ul>
  <li>You can quote a list.</li>
  <li>Etc.</li>
  </ul>
</blockquote>

## Test 10 (Code spans)
`<code>` spans are delimited
by backticks.

You can include literal backticks
like `` `this` ``.

## Result 10
<p><code>&lt;code&gt;</code> spans are delimited
by backticks.</p>

<p>You can include literal backticks
like <code>`this`</code>.</p>

## Test 11 (Preformatted code blocks)
This is a normal paragraph.

    This is a preformatted
    code block.

## Result 11
<p>This is a normal paragraph.</p>

<pre><code>This is a preformatted
code block.
</code></pre>

## Test 12 (Horizontal Rules)
---

* * *

- - - -

## Result 12
<hr />

<hr />

<hr />

## Test 13 (Manual line breaks)
Roses are red,  
Violets are blue.

## Result 13
<p>Roses are red, <br />
Violets are blue.</p>

