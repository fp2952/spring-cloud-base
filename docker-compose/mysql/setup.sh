#!/bin/bash

set -e

echo 'service mysql status'

echo '1.start mysql..'

service mysql start

sleep 3

echo 'service mysql status'

echo 'show databases'

DATABASES=$(mysql -e "show databases")
DATABASE="main-data"
echo $DATABASES
echo $DATABASE
if [[ "$DATABASES" =~ "$DATABASE" ]];then

echo '----------mysql restart----------'
echo '2.data is already exit,not init...'

else
echo '-----------------mysql first start------'
echo '2.start into datas......'

mysql < /mysql/schema.sql
echo '3.finilly into datas'

sleep 3
echo 'service mysql status'

echo '4.modif password'

mysql < /mysql/privileges.sql

echo '5.modify ok....'

echo 'service mysql status'
echo 'mysql start com, ok-----'

fi
tail -f /dev/null




