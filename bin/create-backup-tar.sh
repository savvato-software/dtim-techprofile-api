#!/bin/sh

# Easyah Data Backup Script
#
# This script takes a mysqldump of the database, and saves it in the ./eog-api directory.
#  That directory contains other Easyah data, like images. It tars that whole directory,
#  It then queues the ./copy-backup-tar* script to run in two minutes.
#  If that script succeeds its copies the tar that this file creates to a remote server.
#  If that script fails to copy the tars, it reschedules itself for 2 minutes in the future, and tries again
#
#
#
# This script expects there to be an eog-backup directory on the remote server at $BACKUP_SERVER_HOME.
# It expects an environment variable to be set which indicates the environment Easyah is running in.
#  This should be set in the crontab file, on a line by itself like: EASYAH_ENVIRONMENT=STAGING (other values are DEV or PROD)
#
# This script should be set to run as a cronjob. 

DT=$(date +%Y%m%d%H%M%S)
TODAYS_FILENAME=eog-backup-$EASYAH_ENVIRONMENT-$DT.tar.gz
BACKUP_SERVER_IP=98.245.226.182
BACKUP_SERVER_USER=pi
BACKUP_SERVER_HOME=/home/$BACKUP_SERVER_USER

cd /home/quizki/
mkdir eog-api/backup/db -p

cd /home/quizki/eog-api/backup/db
mysqldump eog_db > eog_db-backup-$EASYAH_ENVIRONMENT-$DT.sql
# TO DO: Find mysqldumps that are older than 1 day, and delete them

mkdir /home/quizki/eog-api-backups
cd /home/quizki/eog-api-backups
tar czf $TODAYS_FILENAME /home/quizki/eog-api

mkdir /home/quizki/eog-api/backup/log -p
touch /home/quizki/eog-api/backup/log/log.txt

echo "$(md5sum $TODAYS_FILENAME)" >> /home/quizki/eog-api/backup/log/log.txt

at now +2 minutes -f /home/quizki/src/eog-api/bin/copy-backup-tar-to-remote-server.sh

