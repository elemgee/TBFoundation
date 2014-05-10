## Test 1 (ID links)
<h3 id="philosophy">Philosophy</h3>

Markdown is intended to be as easy-to-read and easy-to-write as is feasible.

Readability, however, is emphasized above all else. A Markdown-formatted
document should be publishable as-is, as plain text, without looking
like it's been marked up with tags or formatting instructions. While
Markdown's syntax has been influenced by several existing text-to-HTML
filters -- including [Setext] [1], [atx] [2], [Textile] [3], [reStructuredText] [4],
[Grutatext] [5], and [EtText] [6] -- the single biggest source of
inspiration for Markdown's syntax is the format of plain text email.

  [1]: http://docutils.sourceforge.net/mirror/setext.html
  [2]: http://www.aaronsw.com/2002/atx/
  [3]: http://textism.com/tools/textile/
  [4]: http://docutils.sourceforge.net/rst.html
  [5]: http://www.triptico.com/software/grutatxt.html
  [6]: http://ettext.taint.org/doc/

## Result 1
<h3 id="philosophy">Philosophy</h3>

<p>Markdown is intended to be as easy-to-read and easy-to-write as is feasible.</p>

<p>Readability, however, is emphasized above all else. A Markdown-formatted
document should be publishable as-is, as plain text, without looking
like it's been marked up with tags or formatting instructions. While
Markdown's syntax has been influenced by several existing text-to-HTML
filters -- including <a href="http://docutils.sourceforge.net/mirror/setext.html">Setext</a>, <a href="http://www.aaronsw.com/2002/atx/">atx</a>, <a href="http://textism.com/tools/textile/">Textile</a>, <a href="http://docutils.sourceforge.net/rst.html">reStructuredText</a>,
<a href="http://www.triptico.com/software/grutatxt.html">Grutatext</a>, and <a href="http://ettext.taint.org/doc/">EtText</a> -- the single biggest source of
inspiration for Markdown's syntax is the format of plain text email.</p>

## Test 2 (Backslash escapes)
To this end, Markdown's syntax is comprised entirely of punctuation
characters, which punctuation characters have been carefully chosen so
as to look like what they mean. E.g., asterisks around a word actually
look like \*emphasis\*. Markdown lists look like, well, lists. Even
blockquotes look like quoted passages of text, assuming you've ever
used email.

## Result 2
<p>To this end, Markdown's syntax is comprised entirely of punctuation
characters, which punctuation characters have been carefully chosen so
as to look like what they mean. E.g., asterisks around a word actually
look like *emphasis*. Markdown lists look like, well, lists. Even
blockquotes look like quoted passages of text, assuming you've ever
used email.</p>

## Test 3 (code with stuff that looks like backreferences)
Don't replace `$4`...

## Result 3
<p>Don't replace <code>$4</code>...</p>


## Test 4 (block code with backrefs)
This is a normal paragraph.

	This is a $4 preformatted
	code block.

## Result 4
<p>This is a normal paragraph.</p>

<pre><code>This is a $4 preformatted code block.</code>
</pre>

## Test 5 (coded HTML)
For example, to add an HTML table to a Markdown article:

    This is a regular paragraph.

	<table>
		<tr>
			<td>Foo</td>
		</tr>
	</table>

    This is another regular paragraph.

## Result 5
<p>For example, to add an HTML table to a Markdown article:</p>

<pre>
<code>This is a regular paragraph.
&lt;table&gt;
    &lt;tr&gt;
        &lt;td&gt;Foo&lt;/td&gt;
    &lt;/tr&gt;
&lt;/table&gt;
This is another regular paragraph.
</code>
</pre>


## Test 6 (code-quoted link definitions)
Because link names may contain spaces, this shortcut even works for
multiple words in the link text:

	Visit [Daring Fireball][] for more information.

And then define the link:

	[Daring Fireball]: http://daringfireball.net/

Link definitions can be placed anywhere in your Markdown document. I
tend to put them immediately after each paragraph in which they're
used, but if you want, you can put them all at the end of your
document, sort of like footnotes.

## Result 6
<p>Because link names may contain spaces, this shortcut even works for
multiple words in the link text:</p>

<pre><code>Visit [Daring Fireball][] for more information.
</code></pre>

<p>And then define the link:</p>

<pre><code>[Daring Fireball]: http://daringfireball.net/
</code>
</pre>

<p>Link definitions can be placed anywhere in your Markdown document. I
tend to put them immediately after each paragraph in which they're
used, but if you want, you can put them all at the end of your
document, sort of like footnotes.</p>

## Test 7 (block code)
This is a normal paragraph.

    package org.markdownj;

    import java.util.regex.Matcher;

    public interface Replacement {
        String replacement(Matcher m);
    }

## Result 7
<p>This is a normal paragraph.</p>

<pre>
package org.markdownj;

import java.util.regex.Matcher;

public interface Replacement {
    String replacement(Matcher m);
}
</pre>

## Test 8 (HTML inside code backticks)
The only restrictions are that block-level HTML elements -- e.g. `<div>`,
`<table>`, `<pre>`, `<p>`, etc. -- must be separated from surrounding
content by blank lines, and the start and end tags of the block should
not be indented with tabs or spaces. Markdown is smart enough not
to add extra (unwanted) `<p>` tags around HTML block-level tags.

## Result 8
<p>The only restrictions are that block-level HTML elements -- e.g. <code>&lt;div&gt;</code>,
<code>&lt;table&gt;</code>, <code>&lt;pre&gt;</code>, <code>&lt;p&gt;</code>, etc. -- must be separated from surrounding
content by blank lines, and the start and end tags of the block should
not be indented with tabs or spaces. Markdown is smart enough not to add extra (unwanted) <code>&lt;p&gt;</code> tags around HTML block-level tags.</p>

