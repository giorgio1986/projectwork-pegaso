#!/usr/bin/env python

import configparser
import getopt
import psycopg2
import re
import sys
from migrateDef import *
from collections import OrderedDict
from os import listdir, path
from os.path import isfile, join
from pip._vendor.distlib.compat import raw_input

# Execution modes
MODE_DRY_RUN = 10
MODE_MIGRATE = 20

tool_name = 'migration tool v1.7.4'
execution_mode = None
show_migrations = False
environment = None
output_file = None
current_year = None
help_message = 'migrate.py [-h --help] [-s --show] [-m --migrate] [-d --dryrun] [-f=<file> --file=<file>] [-e=<int> --environment=<int>] [-y=<int> --year=<int>]'
first_letter = 'V'
root = 'scripts'
re_migration = re.compile('^V(?P<date>\d{12})_+((?P<client>[0-9a-zA-Z_\-#]+)\.)?[0-9a-zA-Z_\-#]+\.(sql|psql)$')
expected_format_description = 'VYYYYMMDDHHmm_<client>.description.psql'

try:
    opts, args = getopt.getopt(sys.argv[1:], "hsdmwf:e:y:", ["help", "show", "dryrun", "migrate", "file=", "environment=", "year="])

except getopt.GetoptError:
    print(help_message)
    sys.exit(-10)

for opt, arg in opts:
    if opt in ("-d", "--dryrun"):
        execution_mode = MODE_DRY_RUN

    elif opt in ("-m", "--migrate"):
        if execution_mode == MODE_DRY_RUN:
            print('Migrate and Dryrun are mutual, you cannot specify both')
            sys.exit(-20)
        execution_mode = MODE_MIGRATE

    elif opt in ("-s", "--show"):
        show_migrations = True

    elif opt in ("-e", "--environment"):
        try:
            environment = int(arg)
        except ValueError:
            print('Environment parameter must be a number')
            sys.exit(-30)

    elif opt in ("-f", "--file"):
        output_file = arg

    elif opt in ("-y", "--year"):
        current_year = str(arg)

    else:
        print(help_message)
        sys.exit(-1)

print(tool_name)

if execution_mode:
    # print brief message
    msg = ('MIGRATION' if execution_mode == MODE_MIGRATE else 'DRYRUN') + ' MODE'
    print(msg + '\n')

else:
    # if no executing operation was requested, fallback to just show migrations
    show_migrations = True

configParser = configparser.RawConfigParser()
configFilePath = r'environments.conf'
if not path.exists(configFilePath):
    print ('Cannot find environments.conf file')
    exit(-15)

configParser.read(configFilePath)
envList = configParser.sections()

if not environment:
    for i in range(1, len(envList) + 1):
        print('{}. {}'.format(i, envList[i - 1]))

    if len(envList) <= 0:
        print('No environment configurations provided')
        exit(-18)

    try:
        environment = int(input("Choose an environment : "))
    except ValueError:
        print('Environment parameter must be a number')
        sys.exit(-30)

if environment < 1 or environment > len(envList):
    print('Invalid environment')
    exit(-40)

chosen_env = envList[environment - 1]
print('Proceeding with ' + chosen_env)

env_data = dict(configParser.items(chosen_env))

address = config_or_prompt('address', env_data)
username = config_or_prompt('username', env_data)
password = config_or_prompt('password', env_data)
database = config_or_prompt('database', env_data)
client = config_or_default('client', env_data, None)
port = config_or_default('port', env_data, 5432)

if not (address or username or password or database or port):
    print('Invalid connection data')
    exit(-20)

try:
    port = int(port)
except ValueError:
    print('Database port must be a number')
    exit(-50)

if client:
    # standardize client to lowercase
    client = client.lower()

if current_year is None:
    current_year = str(date.today().year)

scripts_path = '.\\' + root + '\\' + current_year
bad_syntax_mig = []
scripts = {}
sql_scripts = None
scripts_to_run = OrderedDict()
scripts_to_run_headers = ''
output_file_content = ''

for f in listdir(scripts_path):
    script_absolute_path = join(scripts_path, f)

    # for all the file inside the directory
    if isfile(script_absolute_path) and f.startswith(first_letter):
        re_match = re_migration.match(f.strip())

        # check if the file respect the format
        if not is_migration_matching_re(re_match):
            bad_syntax_mig.append(f)

        elif len(bad_syntax_mig) == 0:
            scripts[f] = script_absolute_path
            # extract client info from name if present
            w_client = re_match.groupdict().get('client')

            if not w_client or (w_client and client == w_client.lower()):
                # run only if for specified client or if has no client bounds

                # prepare inner sql condition to find not applied migrations
                sql_scripts = ('' if not sql_scripts else sql_scripts + ',\n') + "('{}')".format(f)

# if there are bad naming scripts, show'em and exit
if len(bad_syntax_mig) > 0:
    msg = 'Bad scripts naming, expected is ' + expected_format_description
    for typo in bad_syntax_mig:
        msg += '\n' + typo
    print(msg)
    exit(-30)

SQL_MISSING_MIGRATIONS = """
        select script
        from (values {sql_scripts}) s (script)
        where script not in(select script from db_migrations)
        order by script
"""

SQL_INSERT_SCRIPT_EXECUTION = "insert into db_migrations(script) values(%s) on conflict (script) do nothing;"

# forbidden words in scripts
re_forbidden = dict()
re_forbidden['start transaction'] = re.compile(r'\s*start\s+transaction\s*;', re.IGNORECASE)
re_forbidden['commit'] = re.compile(r'\s*commit\s*;', re.IGNORECASE)
re_forbidden['rollback'] = re.compile(r'\s*rollback\s*;', re.IGNORECASE)
re_forbidden['db_migrations'] = re.compile(r'insert\s+into\s+"?\bpublic"?\."?db_migrations"?\([^;]+;', re.IGNORECASE)
re_forbidden['public'] = re.compile(r'"?\bpublic"?\.', re.IGNORECASE)

first_run = True
script_to_mark_as_executed = None

while first_run or script_to_mark_as_executed:
    first_run = False
    sql_script = None
    cursor = None
    connection = psycopg2.connect(user=username,
                                  password=password,
                                  host=address,
                                  port=port,
                                  database=database)
    try:
        connection.set_session(readonly=False, autocommit=False)
        cursor = connection.cursor()

        if script_to_mark_as_executed:
            # insert a previously failed script as already executed
            cursor.execute(SQL_INSERT_SCRIPT_EXECUTION, (script_to_mark_as_executed,))
            script_to_mark_as_executed = None
            # commit the insert so we can exclude this script
            connection.commit()
            # reset the connection for next commands
            connection.reset()
            # clear previously retrieved scripts
            scripts_to_run.clear()

        # retrieve migration scripts
        if sql_scripts is not None:
            cursor.execute(SQL_MISSING_MIGRATIONS.format(sql_scripts=sql_scripts))
            for row in cursor.fetchall():
                script_to_run = row[0]

                script_absolute_path = scripts[script_to_run]
                # read the file content and sanitize it from forbidden words
                script_to_run_content = clean_from_forbidden_words(read_file(script_absolute_path), re_forbidden)
                scripts_to_run[script_to_run] = script_to_run_content
                scripts_to_run_headers += '\n-- ' + script_to_run
                if output_file:
                    script_annotate = SQL_INSERT_SCRIPT_EXECUTION % "'{}'".format(script_to_run)
                    output_file_content += '\n-- {script_name}\n\n{script_content}\n{script_annotate}\n' \
                        .format(script_name=script_to_run, script_content=script_to_run_content,
                                script_annotate=script_annotate)

        if len(scripts_to_run) > 0:

            if show_migrations:
                print('Showing scripts to run:\n' + scripts_to_run_headers)

            if output_file:
                write_scripts_file(output_file, scripts_to_run_headers, output_file_content)

            if execution_mode:
                print('\nStarting execution of {} migration scripts'.format(len(scripts_to_run)))

                for sql_script, sql_scripts_content in scripts_to_run.items():
                    print("> " + sql_script)
                    # *** EXECUTE ***
                    cursor.execute(sql_scripts_content)

                    # annotate migration as executed
                    cursor.execute(SQL_INSERT_SCRIPT_EXECUTION, (sql_script,))

                if execution_mode == MODE_MIGRATE:
                    connection.commit()
                    print('\nScripts committed!')

                else:
                    connection.rollback()
                    print('\nRollback due to dry run request.')
        else:
            print('No scripts to run')

    except (Exception, psycopg2.Error) as error:
        print ("An execution error has occurred, the transaction is rolled back", error)

        if sql_script:
            if raw_input("\nWould you mark the script as already executed and retry? [y/n] ") == 'y':
                script_to_mark_as_executed = sql_script
    finally:
        #  closing database connection.
        if connection:
            if cursor:
                cursor.close()
            connection.close()