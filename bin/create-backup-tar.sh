#!/bin/sh

# Haxwell Infrastructure Data Backup Script
#
# This script takes a mysqldump of the database, and saves it in the $HAX_APP_NAME directory.

#  That directory contains other app data, like images. It tars that whole directory,
#  It then queues the ./copy-backup-tar* script to run in two minutes.
#  If that script succeeds its copies the tar that this file creates to a remote server.
#  If that script fails to copy the tars, it reschedules itself for 2 minutes in the future, and tries again
#
#
# This script expects there to be a dtimtp-backup directory on the remote server at $BACKUP_SERVER_HOME.
# It expects an environment variable to be set which indicates the environment the app is running in.
#  This should be set in the crontab file, on a line by itself like: HAX_APP_ENVIRONMENT=STAGING (other values are DEV or PROD)
#
# This script should be set to run as a cronjob. 
#
# These environment variables are expected:
# ----
#  HAX_APP_NAME # ie, eog-api
#  HAX_APP_ENVIRONMENT # ie, DEV|STAGING|PROD
#  HAX_APP_DB_NAME # ie, eog_db

DT=$(date +%Y%m%d%H%M%S)
TODAYS_FILENAME=backup-$HAX_APP_ENVIRONMENT-$DT.tar.gz
BACKUP_SERVER_IP=98.245.226.182
BACKUP_SERVER_USER=pi
BACKUP_SERVER_HOME=/home/$BACKUP_SERVER_USER

cd /home/quizki/
mkdir $HAX_APP_NAME/backup/db -p

cd /home/quizki/$HAX_APP_NAME/backup/db
mysqldump $HAX_APP_DB_NAME > $HAX_APP_DB_NAME-backup-$HAX_APP_ENVIRONMENT-$DT.sql
# TO DO: Find mysqldumps that are older than 1 day, and delete them

mkdir /home/quizki/$HAX_APP_NAME-backups
cd /home/quizki/$HAX_APP_NAME-backups
tar czf $TODAYS_FILENAME /home/quizki/$HAX_APP_NAME

mkdir /home/quizki/$HAX_APP_NAME/backup/log -p
touch /home/quizki/$HAX_APP_NAME/backup/log/log.txt

echo "$(md5sum $TODAYS_FILENAME)" >> /home/quizki/$HAX_APP_NAME/backup/log/log.txt

at now +2 minutes -f /home/quizki/src/$HAX_APP_NAME/bin/copy-backup-tar-to-remote-server.sh


