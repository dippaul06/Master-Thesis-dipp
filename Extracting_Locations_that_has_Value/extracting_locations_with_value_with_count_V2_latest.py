import csv



print("################# START ######################")

Full_Location_Data = '/global/D1/projects/umod/dipp/playground/Location_to_Location_User_ID/results_locations_with_count_V2_Latest.csv'
Res_Data = '/global/D1/projects/umod/dipp/playground/Location_to_Location_User_ID/results_locations_with_count_V2_Latest.csv'



res_dict = {}
no_match = "['none', 'none', 'none', 'none', 'none']"
no_match_1 = '[None, None, None, None, None]'
total_data = 0
resolved_data = 0
exception_data = 0
out_of_bound = 0

print("################# Making Dictionary with Locations that has Value #########################")

with open(Full_Location_Data, newline='', encoding='utf-8') as data_file:
    location_data = csv.DictReader((x.replace('\0', '') for x in data_file))

    for line in location_data:
        total_data += 1
        try:
            loc_i = line['i']
            loc_j = line['j']
            loc_contacts = int(line['contacts'])

            if((loc_i != no_match and loc_i != no_match_1) and (loc_j != no_match and loc_j != no_match_1)):
                resolved_data += 1
                temp_contacts = res_dict.get(loc_i + ',' + loc_j)
                if(temp_contacts == None):
                    res_dict[loc_i + ',' + loc_j] = loc_contacts
                else:
                    res_dict[loc_i + ',' + loc_j] = temp_contacts + loc_contacts
        except Exception:
            exception_data += 1
            continue

print("###################Dictionary Made##############################")

file1 = open(Res_Data, "a")  # append mode
file1.write('i,j,count'+'\n')
file1.close()

print("##################### WRITING RESULTS TO FILE ################################")

file1 = open(Res_Data, "a")
for a,b in res_dict.items():
    try: 
        c = a.split('],[')
        file1.writelines(str(c[0]) + '],[' + str(c[1]) + ',' + str(b) + '\n')
        file1.flush()
    except Exception:
        out_of_bound += 1
        continue


file1.close()

print("################################ DONE  ########################################")

print('Total Data: ', total_data)
print('Total Resolved Data: ', resolved_data)
print('Total  Exception: ', exception_data)
print('Out of Bound: ', out_of_bound)