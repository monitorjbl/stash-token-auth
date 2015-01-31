# Token-based Auth Plugin for Stash

![Key](/src/main/resources/images/pluginIcon.png?raw=true)

Enables token-based authentication to the Stash REST API for individual users. Available on the [Atlassian Marketplace](https://marketplace.atlassian.com/plugins/com.thundermoose.plugins.stash-token-auth) now.

## Why?

Stash's REST API is very powerful and easy to use. It's secured and drives most what you actually do in the UI, so there's not much issue in allowing normal users to access it. Unfortunately it requires a password to access. This makes scripting essentially impossible for normal users that care about security. Ever thought, "Boy, it'd be nice if a bash script could open PRs for me so I didn't have to"? Welp, that's not really feasible if you're working in an enterprise environment where your password gives access to a lot of other stuff.

## Tokens
Tokens are the easiest solution to this problem. Now, you can store a token on your filesystem and go crazy with scripting! Because the token is randomly generated (and can be regenerated anytime in the UI), it's not the end of the world if a security breach happens. The person who steals your token can't do anything with it once you regenerate it, and access can be administratively restricted to specific areas of the REST service to limit damage in  the event of a breach.

## Usage

Once you've got the plugin installed, any user can log in and go to their account page to see their token. Simply copy it out and dump it into whatever client you'd like to use. Here's a quick and dirty example using cURL:

```
TOKEN=<paste token here>
curl  -H "X-Auth-User:admin" \
      -H "X-Auth-Token:$TOKEN" \
      http://localhost:7990/stash/rest/api/1.0/projects/PROJECT_1/repos

```

## UI Elements

### Administration config

From this screen, an admin can control settings that affect all tokens on the server.
![Admin Screenshot](/src/main/resources/images/screenshot-admin.png?raw=true)

### User config

From here, a user can see and regenerate his personal token.
![User Screenshot](/src/main/resources/images/screenshot-user.png?raw=true)

## Attributions

Key Icon by [Udana Ekanayake](https://www.iconfinder.com/ekanayake) used under Creative Commons
