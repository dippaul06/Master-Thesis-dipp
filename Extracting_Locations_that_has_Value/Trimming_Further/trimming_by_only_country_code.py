import csv
import sys

#csv.field_size_limit(sys.maxsize)

loc_data = '/global/D1/projects/umod/dipp/playground/Location_to_Location_User_ID/results_locations_with_count.csv'
res_Data = '/global/D1/projects/umod/dipp/playground/Location_to_Location_User_ID/Location_with_only_country_code.csv'

cols = ['i','j','count']
res_dict = {}
No_of_None = 0

print("############ START ################")


print("############ MAKING LOCATION DICT #############")

with open(loc_data, newline='', encoding='utf-8') as fd:
    base_location_data = csv.DictReader(fd, delimiter=']', fieldnames=cols)
    next(base_location_data,None)
    
    for line in base_location_data:
        try:
            try:
                while(line != None):
                    a = line['i'].split()
                    b = line['j'].split()
                    if(a != None and b != None):
                        c = a[0].replace('[','')
                        d = b[0].replace(',[','')

                        source_country = c.replace(',','')
                        dest_country = d.replace(',','')
                        count = float(line['count'].replace(',',''))

                        
                        temp_data = source_country+','+dest_country
                            

                        d = res_dict.get(temp_data)
                        
                        if(d == None):
                            res_dict[temp_data] = int(count)
                        else:
                            res_dict[temp_data] = int(count) + int(d)
            except Exception as e: 
                print(e)
    
        except(AttributeError):
            No_of_None += 1
            continue


print("################ LOCATION DICT MADE #####################")

file1 = open(res_Data, "a")  # append mode
file1.write('i,j,count'+'\n')
file1.close()

No_of_columns = 0

print("########## WRITING TO FILE ###########")
file1 = open(res_Data, "a")
for a,b in res_dict.items():
    No_of_columns += 1
    c = a.split(',')
    file1.writelines(str(c[0]) + ',' + str(c[1]) + ',' + str(b) + '\n')
    file1.flush()

file1.close()
print("####### RESULTS WRITTEN TO FILE ###########")

print("No. of Columns: ", No_of_columns)
print("No. of None", No_of_None)

print("######## DONE ########")