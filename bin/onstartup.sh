#!/bin/sh

# Be sure too, that the git repo this server is building off of is the correct one..
#  Master for PROD, Develop for STAGING, and the local dev branch for DEV

# 
# This script is added to crontab, and then run when the machine starts.
#  It keeps me from having to initialize the machine, this script now does
#  it for me.
#
#  crontab: @reboot <this-file-name.sh>       # create cron entry which runs this file at reboot

# To see if this file has been added to cron already:
#  crontab -l

# Also, there is a file, ~/.my.cnf, which has mysql login info

eval "$(ssh-agent -s)"
ssh-add ~/.ssh/id_rsa

cd /home/quizki/src/eog-api

git pull

# SEE https://stackoverflow.com/questions/29889074/how-to-wait-for-first-command-to-finish
#
# This mysql command isn't running like it should, I think its not waiting for GitPull to 
# finish? I dunno. But anyway, this is where I left off. But, should verify this script even
# runs. the cat ./src... part I mean.

# COMMENTED this out 2018-05-26, don't want to refresh the DB on startup.
#mysql -e "`cat ./src/main/resources/META-INF/sql/init_eog_db.sql`"

export JAVA_HOME=/home/quizki/apps/java
mvn spring-boot:run > out &

exit 0;

