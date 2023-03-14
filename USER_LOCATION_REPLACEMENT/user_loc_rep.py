import codecs
import io
import pandas as pd
import csv

user_file = '/global/D1/projects/umod/dipp/playground/User_Location_Replacement/userAll.csv'
location_file = '/global/D1/projects/umod/dipp/playground/User_Location_Replacement/frequentplaces120000.txt'
location_resolved_file = '/global/D1/projects/umod/dipp/playground/User_Location_Replacement/frequentplaces120000.txt.resolved'
result_file = '/global/D1/projects/umod/dipp/playground/User_Location_Replacement/result/res_loc_rep.csv'

print("##################### START #####################################")

print("####################### READING THE LOCATION FILES ########################################")
## Reading the Location File
with open(location_file) as lf:
    loc_data = lf.read()
    loc_data_list = loc_data.split("\n")

with open(location_resolved_file) as lrf:
    loc_data_res = lrf.read()
    loc_data_list_res = loc_data_res.split("\n")

print("################################ LOCATION FILES ARE IN THE LIST #############################################")

cols = ['createdAt', 'followers', 'favourites', 'location', 'userId']
user_data_dic = dict.fromkeys(cols)
no_match = "['none', 'none', 'none', 'none', 'none']"

print("######################## READING THE USER FILE ####################################")

with open(user_file, newline='', encoding='utf-8') as uf:
    #user_data = csv.DictReader(uf, fieldnames=cols)
    user_data = csv.DictReader((x.replace('\0', '') for x in uf), fieldnames=cols)
    #reader = csv.reader(x.replace('\0', '') for x in mycsv)
    user_data_dict_list = []
    for line in user_data:
        user_data_dict_list.append(line)

'''


with open(user_file,'rb') as uf:
    content = uf.read().splitlines()
    csv_stream = io.BytesIO(content)
    fixed_lines = (line.replace(b'\x00', b'') for line in csv_stream)
    stream = codecs.iterdecode(fixed_lines, 'utf-8-sig', errors='strict')
    user_data = csv.DictReader(stream, skipinitialspace=True, restkey="INVALID", fieldnames=cols)

    #user_data = csv.DictReader(uf, fieldnames=cols)
    user_data_dict_list = []
    for line in user_data:
        user_data_dict_list.append(line)
'''

print("############################## USER FILE IS IN THE LIST ##################################")

print("########################### LOWERCASE CONVERSION ####################################")
for i in range(len(user_data_dict_list)):
    try:
        user_data_dict_list[i]['location'] = user_data_dict_list[i]['location'].lower()
    except AttributeError:
        continue

for i in range(len(loc_data_list)):
    try:
        loc_data_list[i] = loc_data_list[i].lower()
    except AttributeError:
        continue

print("########################## LOWERCASE CONVERSION DONE ###############################################")

print("########################## USER LOCATION REPLACEMENT ONGOING  #########################################################")
for i, data in enumerate(user_data_dict_list):
    counter = 0
    for j, loc_d in enumerate(loc_data_list):
        if(user_data_dict_list[i]['location'] == loc_data_list[j].rstrip()):
            counter = 1
            user_data_dict_list[i]['location'] = loc_data_list_res[j]
    
    if counter == 0:
        user_data_dict_list[i]['location'] = no_match
    counter = 1 

print("########################## USER LOCATION REPLACEMENT DONE ####################################################")

df = pd.DataFrame(user_data_dict_list)
print("########################### DATAFRAME CREATED ########################################################")

df=df[df.columns.dropna()]
#df.drop('INVALID', axis=1, inplace=True) 
df.to_csv(result_file, index=False)
print("####################### RESULT PRINTED ###############################################")


print("##################### DONE #####################################")