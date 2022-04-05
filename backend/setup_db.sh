#!/usr/bin/env bash
echo 'Create `hackathon-fit2022` db!'
psql hackathon-fit2022 -f "`dirname $0`"/setup_db.sql
