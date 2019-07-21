#!/bin/sh

# 
# This script copies each file in the backup directory to the remote server.
#  If the copy is successful, it removes the file from the backup directory.
#  If the copy fails, the file stays, and this script schedules itself to run again in 2 minutes.
#

BACKUP_SERVER_IP=98.245.226.182
BACKUP_SERVER_USER=pi
BACKUP_SERVER_HOME=/home/$BACKUP_SERVER_USER
FILES=/home/quizki/eog-api-backups

cd $FILES
for f in ./*.tar.gz; do
scp $f $BACKUP_SERVER_USER@$BACKUP_SERVER_IP:$BACKUP_SERVER_HOME/eog-backup/$f

if ssh $BACKUP_SERVER_USER@$BACKUP_SERVER_IP test $BACKUP_SERVER_HOME/eog-backup/$f \> /dev/null 2\>\&1
    then 
        rm $f
fi

done

if [ "$(ls -A $FILES)" ]
    then
        at now +2 minutes -f /home/quizki/src/eog-api/bin/copy-backup-tar-to-remote-server.sh
fi
