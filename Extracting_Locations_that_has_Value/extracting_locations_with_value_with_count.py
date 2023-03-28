import csv
import pandas as pd


print("################# START ######################")

Full_Location_Data = '/global/D1/projects/umod/dipp/playground/Location_to_Location_User_ID/results_loc_to_loc_from_user_to_user.csv'
Res_Data = '/global/D1/projects/umod/dipp/playground/Location_to_Location_User_ID/results_locations_with_count.csv'



res_dict = {}
no_match = "['none', 'none', 'none', 'none', 'none']"
no_match_1 = '[None, None, None, None, None]'
total_data = 0
resolved_data = 0

print("################# Making Dictionary with Locations that has Value #########################")

with open(Full_Location_Data, newline='', encoding='utf-8') as fd:
    base_location_data = csv.DictReader((x.replace('\0', '') for x in fd))

    for line in base_location_data:
        total_data += 1
        a = line['i']
        b = line['j']
        if(a!=no_match and b!=no_match and a!=no_match_1 and b!=no_match_1):
            temp_data = a+']'+','+'['+b
            d = res_dict.get(temp_data)
            if(d == None):
                resolved_data += 1
                res_dict[temp_data] = 1
            else:
                res_dict[temp_data] = d+1

print("###################Dictionary Made##############################")

file1 = open(Res_Data, "a")  # append mode
file1.write('i,j,count'+'\n')
file1.close()

print("##################### WRITING RESULTS TO FILE ################################")

file1 = open(Res_Data, "a")
for a,b in res_dict.items():
    c = a.split('],[')
    file1.writelines(str(c[0]) + ',' + str(c[1]) + ',' + str(b) + '\n')
    file1.flush()

file1.close()

print("################################ DONE  ########################################")

print('Total Data: ', total_data)
print('Total Resolved Data: ', resolved_data)