import getpass
from datetime import datetime
from pip._vendor.distlib.compat import raw_input

def config_or_default(key, obj, default):
    return obj[key] if key in obj else default

def config_or_prompt(key, obj):
    return obj[key] if key in obj else raw_input('{} : '.format(key.capitalize()))

def config_or_password(key, obj):
    return obj[key] if key in obj else getpass.getpass('{} : '.format(key.capitalize()))

def read_file(file_path):
    with open(file_path, 'r') as fh:
        return fh.read()

def write_scripts_file(output_file, scripts_to_run_headers, output_file_content):
    with open(output_file, 'w') as fw:
        fw.write('-- Listing content of these scripts :{headers}\n\n'
                 'start transaction;\n'
                 '{content}\n'
                 'commit;'
                 .format(headers=scripts_to_run_headers, content=output_file_content))

def clean_from_forbidden_words(sql_scripts_content, re_forbidden):
    for key, forbidden_re in re_forbidden.items():
        # search and remove forbidden words
        if forbidden_re.search(sql_scripts_content):
            print("WARN: cancel forbidden word '{}'".format(key))
            sql_scripts_content = forbidden_re.sub('', sql_scripts_content)
    return sql_scripts_content

def is_migration_date_valid(date_str):
    is_date = True
    try:
        date = datetime.strptime(date_str, '%Y%m%d%H%M')
        is_date = date <= datetime.now()

    except ValueError:
        is_date = False
    return is_date

def is_migration_matching_re(re_match):
    if re_match:
        date_str = re_match.groupdict().get('date')
        return not date_str or is_migration_date_valid(date_str)
    return False