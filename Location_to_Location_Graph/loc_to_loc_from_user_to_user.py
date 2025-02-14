import csv
import pandas as pd

print("######################## START ###############################################")

location_user = '/global/D1/projects/umod/dipp/playground/Location_to_Location_User_ID/res_loc_rep.csv'
user_graph = '/global/D1/projects/umod/dipp/playground/Location_to_Location_User_ID/edge_list.csv'
res_file = '/global/D1/projects/umod/dipp/playground/Location_to_Location_User_ID/results_loc_to_loc_from_user_to_user.csv'

print("########################### READING AND MAKING DICT FROM USER LOCATION FILE ##########################################################")

with open(location_user,  newline='', encoding='utf-8') as lu:
    loc_user_dict = {row["userId"]: row["location"] for row in csv.DictReader(lu, ("createdAt","followers","favourites","location","userId"))}

print("############################# USER LOCATION DICT DONE ###############################################################################")

print("###################################### REPLACING USER ID WITH LOCATION ##########################################################################################")
with open(user_graph, newline='', encoding='utf-8') as ug:
    user_graph_data = csv.DictReader((x.replace('\0', '') for x in ug))
    loc_to_loc_user = []
    some_to_some = 0
    some_to_none = 0
    none_to_some = 0
    none_to_none = 0

    for line in user_graph_data:
        res_s = loc_user_dict.get(line['i'])
        res_d = loc_user_dict.get(line['j'])

        if(res_s == None):
            if(res_d == None):
                none_to_none += 1
            else:
                none_to_some += 1
        if(res_s != None):
            if(res_d == None):
                some_to_none += 1
            else:
                some_to_some += 1
        
        if(res_s == None):
            res_s = "['none', 'none', 'none', 'none', 'none']"
        if(res_d == None):
            res_d = "['none', 'none', 'none', 'none', 'none']"


        line['i'] = res_s
        line['j'] = res_d
        loc_to_loc_user.append(line)


print("###################################### USER ID REPLACED WITH LOCATION AND LIST MADE ############################################################")

print("Source has value and Destination has value: ", some_to_some)
print("Source has value and Destination is None: ", some_to_none)
print("Source is None and Destination has value: ", none_to_some)
print("Source is None and Destination is None: ", none_to_none)

print("############################## MAKING DATAFRAME AND PRINTING RESULTS ########################################################################")

df = pd.DataFrame(loc_to_loc_user)
df=df[df.columns.dropna()]
#df.drop('INVALID', axis=1, inplace=True) 
df.to_csv(res_file, index=False)

print("################################## RESULTS SAVED ##################################################################")

print("########################################### DONE ############################################################")