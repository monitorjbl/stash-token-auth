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

## Usage

Once you've got the plugin installed, any user can log in and go to their account page to see their token. Simply copy it out and dump it into whatever client you'd like to use. Here's a quick and dirty example using cURL:

```
TOKEN=210Vh7rX2VQaHr0wj1EV5BJIRsnd4xNzQ8tmyMZg4JuOdEz9FHvF/IeJMdrfg/LDaFIBDz5z6RJWep7cUN28uRjeZFjOiq2y68GK1OdDssY=
curl -H "X-Auth-User:admin" -H "X-Auth-Token:$TOKEN" http://localhost:7990/stash/rest/api/1.0/projects/PROJECT_1/repos

```

## UI Elements

### Administration config

From this screen, an admin can control settings that affect all tokens on the server.
![Imgur](http://i.imgur.com/0fCRtbb.png)

### User config

From here, a user can see and regenerate his personal token.
![Imgur](http://i.imgur.com/CjzM2hV.png)
