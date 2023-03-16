import pandas as pd
import csv

user_file = '/global/D1/projects/umod/dipp/playground/User_Location_Replacement/userAll.csv'
location_file = '/global/D1/projects/umod/dipp/playground/User_Location_Replacement/frequentplaces1814031.txt'
location_resolved_file = '/global/D1/projects/umod/dipp/playground/User_Location_Replacement/frequentplaces1814031.txt.resolved'
result_file = '/global/D1/projects/umod/dipp/playground/User_Location_Replacement/result/res_loc_rep.csv'



print("##################### START #####################################")

print("####################### READING THE LOCATION FILES AND CREATING DICT ########################################")
## Reading the Location File
with open(location_file) as lf:
  location_data = lf.read().lower().split("\n")
with open(location_resolved_file) as lrf:
  location_res_data = lrf.read().split("\n")
location_data_dict = dict(p for p in zip(location_data,location_res_data))

print("################################ LOCATION FILES ARE IN THE DICT #############################################")

cols = ['createdAt', 'followers', 'favourites', 'location', 'userId']
no_match = "['none', 'none', 'none', 'none', 'none']"

print("######################## READING THE USER FILE AND REPLACING LOCATION ####################################")

with open(user_file, newline='', encoding='utf-8') as uf:
    user_data = csv.DictReader((x.replace('\0', '') for x in uf), fieldnames=cols)
    user_data_dict_list = []
    No_of_Data = 0
    No_of_None = 0
    No_of_Value = 0

    for line in user_data:
        No_of_Data += 1
        try: 
            line_in_lower_case = line['location'].lower()
            res_location = location_data_dict.get(line_in_lower_case, None)
        except AttributeError:
            res_location = "['none', 'none', 'none', 'none', 'none']"
            
        if(res_location == None):
            res_location = "['none', 'none', 'none', 'none', 'none']"
            #No_of_None += 1  
        if(res_location == ''):
            res_location = "['none', 'none', 'none', 'none', 'none']"
            #No_of_None += 1
        else:
            No_of_Value += 1

        line['location'] = res_location
        user_data_dict_list.append(line)
    

    print("No. of Data: ", No_of_Data)
    #print("No of None: ", No_of_None)
    print("No of Value: ", No_of_Value)


print("############################## USER FILE IS IN THE LIST ##################################")


print("########################## USER LOCATION REPLACEMENT DONE ####################################################")

df = pd.DataFrame(user_data_dict_list)
print("########################### DATAFRAME CREATED ########################################################")

df=df[df.columns.dropna()]
#df.drop('INVALID', axis=1, inplace=True) 
df.to_csv(result_file, index=False)
print("####################### RESULT PRINTED ###############################################")


print("##################### DONE #####################################")