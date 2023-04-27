import csv
import pandas as pd
import sys

csv.field_size_limit(sys.maxsize)

loc_data = '/global/D1/projects/umod/dipp/playground/Location_to_Location_User_ID/results_locations_with_count_V2_Latest.csv'
res_Data = '/global/D1/projects/umod/dipp/playground/Location_to_Location_User_ID/Location_with_only_country_code_V3_Latest.csv'

cols = ['i','j','count']
res_dict = {}
No_of_rows_in_original = 0
No_of_None = 0

print("############ START ################")


print("############ MAKING LOCATION DICT #############")

with open(loc_data, newline=' ', encoding='utf-8') as csvfile:
    #loc_reader = csv.DictReader(csvfile, delimiter=']', fieldnames=cols)
    loc_reader = csv.DictReader(csvfile, delimiter=']', fieldnames=cols)
    next(loc_reader, None)
    data = list(loc_reader)
    row_count = len(data)
    print(row_count)
    for line in loc_reader:
        
        No_of_rows_in_original += 1
        a = (line['i'].replace('[','')).split()
        b = (line['j'].replace('[','')).split()
        c = int(line['count'].replace(',',''))
        #c = line['count'].replace(',','')
        temp_i = a[0].replace(',','')
        temp_j = b[0].replace(',','')
        temp_count = int(c)

        temp_lookup_data = temp_i + '+' + temp_j
        temp_lookup = res_dict.get(temp_lookup_data)

        if(temp_lookup == None):
            res_dict[temp_lookup_data] = int(temp_count)
        else:
            res_dict[temp_lookup_data] = int(temp_count) + int(temp_lookup)
        
            


print("################ LOCATION DICT MADE #####################")

file1 = open(res_Data, "a")  # append mode
file1.write('i,j,count'+'\n')
file1.close()

No_of_rows_after_count = 0

print("########## WRITING TO FILE ###########")
for a,b in res_dict.items():
    temp = a.split('+')
    source_i = temp[0]
    destination_j = temp[1]
    count = int(b)
    file1 = open(res_Data, "a")
    file1.writelines(str(source_i) + ',' + str(destination_j) + ',' + str(count) + '\n')
    file1.flush()
    No_of_rows_after_count += 1

file1.close()
print("####### RESULTS WRITTEN TO FILE ###########")

print("No. of rows in the Original: ", No_of_rows_in_original)
print("No. of rows after count: ", No_of_rows_after_count)
#print("No. of None", No_of_None)

print("######## DONE ########")