# Token-based Auth Plugin

This Stash plugin enables token-based auth to the REST API for individual users.

## Why?

Stash's REST API is very powerful, and lets you do most of what you can in the UI but unfortunately it requires
a password to access. This makes scripting essentially impossible for normal users that care about security.
Ever thought, "Boy, it'd be nice if a bash script could open PRs for me so I didn't have to"? Welp, that's not
really feasible if you're working in an enterprise environment where your password gives access to a lot of other
stuff.

## Tokens
Tokens are the easiest solution to this problem. Now, you can store a token on your filesystem and go crazy with
scripting! Because the token is randomly generated (and can be regenerated anytime in the UI), it's not the end of
the world if a security breach happens. The person who steals your token can't do anything with it once you regenerate
it, and access can be administratively restricted to specific areas of the REST service to limit damage in the event
of a breach.
