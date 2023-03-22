import csv
import pandas as pd
#import os

print("######################## START ###############################################")

location_user = '/global/D1/projects/umod/dipp/playground/Location_to_Location_User_ID/res_loc_rep.csv'
user_graph = '/global/D1/projects/umod/dipp/playground/Location_to_Location_User_ID/edge_list.csv'
res_file = '/global/D1/projects/umod/dipp/playground/Location_to_Location_User_ID/results_loc_to_loc_from_user_to_user.csv'

print("########################### READING AND MAKING DICT FROM USER LOCATION FILE ##########################################################")

with open(location_user,  newline='', encoding='utf-8') as lu:
    loc_user_dict = {row["userId"]: row["location"] for row in csv.DictReader(lu, ("createdAt","followers","favourites","location","userId"))}

print("############################# USER LOCATION DICT DONE ###############################################################################")

file1 = open(res_file, "a")  # append mode
file1.write('i,j,contacts,mentions'+'\n')
file1.close()

#row_count = 0

print("###################################### REPLACING USER ID WITH LOCATION ##########################################################################################")
with open(user_graph, newline='', encoding='utf-8') as ug:
    user_graph_data = csv.DictReader((x.replace('\0', '') for x in ug))
    No_of_Line = 0
    some_to_some = 0
    some_to_none = 0
    none_to_some = 0
    none_to_none = 0
    no_match = "['none', 'none', 'none', 'none', 'none']"

    #row_count = sum(1 for row in user_graph_data)
    #print("No. of Rows: ", row_count)

    file1 = open(res_file, "a")  # append mode


    for line in user_graph_data:
        No_of_Line += 1
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
            res_s = no_match
        if(res_d == None):
            res_d = no_match


        line['i'] = res_s
        line['j'] = res_d

        #file1 = open(res_file, "a")  # append mode
        #file1.write(line['i'] + ','+ line['j']+','+ line['contacts']+ ','+line['mentions']+'\n')
        file1.write('"' + line['i'] + '"' + ',' + '"' + line['j'] + '"' + ',' + line['contacts']+ ',' + line['mentions'] + '\n')
        #file1.close()
        file1.flush()
        
    #os.fsync(file1)
    file1.close()
        


print("###################################### USER ID REPLACED WITH LOCATION AND LIST MADE ############################################################")
print("################################## RESULTS SAVED ##################################################################")

print("Number of Lines: ", No_of_Line)
print("Source has value and Destination has value: ", some_to_some)
print("Source has value and Destination is None: ", some_to_none)
print("Source is None and Destination has value: ", none_to_some)
print("Source is None and Destination is None: ", none_to_none)


print("########################################### DONE ############################################################")