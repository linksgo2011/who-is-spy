export CIRCLE_TOKEN='?circle-token=93106a0b530273d9cb344ea608bc609490fdf202'

curl https://circleci.com/api/v1.1/project/github/linksgo2011/who-is-spy/42/artifacts$CIRCLE_TOKEN | grep -o 'https://[^"]*' > artifacts.txt

<artifacts.txt xargs -P4 -I % wget %