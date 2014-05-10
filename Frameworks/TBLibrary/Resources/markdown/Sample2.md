## Test 1 (encode dash, greater than, and less than)
Span-level HTML tags -- e.g. `<span>`, `<cite>`, or `<del>`

## Result 1
<p>Span-level HTML tags -- e.g. <code>&lt;span&gt;</code>, <code>&lt;cite&gt;</code>, or <code>&lt;del&gt;</code></p>

## Test 2 (newlines after H4 tags)
#### Headers in blockquotes

* You can quote a list.
* Etc.

## Result 2
<h4>Headers in blockquotes</h4>

<ul>
<li>You can quote a list.</li>
<li>Etc.</li>
</ul>

## Test 3 (from syntax.textPane)
Note that Markdown formatting syntax is not processed within block-level
HTML tags. E.g., you can't use Markdown-style `*emphasis*` inside an
HTML block.

## Result 3
<p>Note that Markdown formatting syntax is not processed within block-level
HTML tags. E.g., you can't use Markdown-style <code>*emphasis*</code> inside an
HTML block.</p>

## Test 4 (auto-links)
Go to <http://www.petebevin.com/> and poke around.

## Result 4
<p>Go to <a href="http://www.petebevin.com/">http://www.petebevin.com/</a> and poke around.</p>

## Test 5 (auto-links only inside angle brackets)
Go to http://www.petebevin.com/ and poke around.

## Result 5
<p>Go to http://www.petebevin.com/ and poke around.</p>

## Test 6 (HTML blocks)
0 < 1

<table>
 <tr>
 <td>Hello!</td>
 </tr>
</table>

Wasn't that fun!

## Result 6
<p>0 &lt; 1</p>

<table>
 <tr>
 <td>Hello!</td>
 </tr>
</table>

<p>Wasn't that fun!</p>

