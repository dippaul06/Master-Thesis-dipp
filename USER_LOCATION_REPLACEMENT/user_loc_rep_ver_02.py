import pandas as pd
import csv

user_file = '/global/D1/projects/umod/dipp/playground/User_Location_Replacement/userAll.csv'
location_file = '/global/D1/projects/umod/dipp/playground/User_Location_Replacement/frequentplaces120000.txt'
location_resolved_file = '/global/D1/projects/umod/dipp/playground/User_Location_Replacement/frequentplaces120000.txt.resolved'
result_file = '/global/D1/projects/umod/dipp/playground/User_Location_Replacement/result/res_loc_rep.csv'

def is_equal_hashing(a, b, hashing=hash):
    return len(a) == len(b) and hashing(a) == hashing(b) and a == b

print("##################### START #####################################")

print("####################### READING THE LOCATION FILES ########################################")
## Reading the Location File
with open(location_file) as lf:
    loc_data = lf.read()
    loc_data_dict = {}
    i = 0
    for l in loc_data.split("\n"):
        loc_data_dict[i] = l.lower()
        i+=1


with open(location_resolved_file) as lrf:
    loc_res_data = lrf.read()
    loc_res_data_dict = {}
    i = 0
    for l in loc_res_data.split("\n"):
        loc_res_data_dict[i] = l
        i+=1

print("################################ LOCATION FILES ARE IN THE DICT #############################################")

cols = ['createdAt', 'followers', 'favourites', 'location', 'userId']
user_data_dic = dict.fromkeys(cols)
no_match = "['none', 'none', 'none', 'none', 'none']"

print("######################## READING THE USER FILE AND REPLACING LOCATION ####################################")

with open(user_file, newline='', encoding='utf-8') as uf:
    user_data = csv.DictReader((x.replace('\0', '') for x in uf), fieldnames=cols)
    No_of_Data = 0
    No_of_Match = 0
    No_of_No_Match = 0
    user_data_dict_list = []
    for line in user_data:
        No_of_Data += 1
        counter = 0
        for key, value in loc_data_dict.items():
            
            try:
                if(is_equal_hashing(line['location'].lower(), value) == True):
                    counter = 1
                    line['location'] = loc_res_data_dict[key]
                    user_data_dict_list.append(line)
                    No_of_Match += 1
                    

            except AttributeError:
                line['location'] = no_match
                user_data_dict_list.append(line)
                counter = 1
                No_of_No_Match +=1
                

            
        if (counter == 0):
            line['location'] = no_match
            user_data_dict_list.append(line)
            No_of_No_Match += 1
        
        counter = 1


print('No. Of Data: ', No_of_Data)
print('No. of Match: ', No_of_Match)
print('No. of No Match', No_of_No_Match)


print("############################## USER FILE IS IN THE LIST ##################################")


print("########################## USER LOCATION REPLACEMENT DONE ####################################################")

df = pd.DataFrame(user_data_dict_list)
print("########################### DATAFRAME CREATED ########################################################")

df=df[df.columns.dropna()]
#df.drop('INVALID', axis=1, inplace=True) 
df.to_csv(result_file, index=False)
print("####################### RESULT PRINTED ###############################################")


print("##################### DONE #####################################")