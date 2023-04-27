import csv
import pandas as pd

loc_data = '/global/D1/projects/umod/dipp/playground/Location_to_Location_User_ID/results_locations_with_count_V2_Latest.csv'
res_Data = '/global/D1/projects/umod/dipp/playground/Location_to_Location_User_ID/Location_with_only_country_code_V2_Latest.csv'

cols = ['i','j','count']
res_dict = {}
No_of_rows_in_original = 0
No_of_rows_after_count = 0
No_of_None = 0

print("############ START ################")


print("############ MAKING DataFrame #############")

#df = pd.read_csv(loc_data, sep='],', header=0, names=cols)

df = pd.read_csv(loc_data, sep='],', names=cols)
print("Done Here")
df = df.drop(index=0)
before_dropiing_na = len(df)
print("Before dropping Nan", before_dropiing_na)
df = df.dropna()
after_dropiing_na = len(df)
print("After dropping Nan", after_dropiing_na)
#df['count'] = df['count'].astype('Int32')
#print("Now Enterted Here")
#new_df = pd.DataFrame(columns=cols)
#print("Now created new DF")
No_of_rows_in_original = len(df)
print("Calculations Done: No of Rows: ", No_of_rows_in_original)


loc_reader = {}
loc_reader = df.to_dict('index')

print('############### DF TO DICT DONE #########################')
for index, line in loc_reader.items():
        
        #No_of_rows_in_original += 1
        a = (line['i'].replace('[','')).split()
        b = (line['j'].replace('[','')).split()
        #c = int(line['count'].replace(',',''))
        c = line['count']
        #c = line['count'].replace(',','')
        temp_i = a[0].replace(',','')
        temp_j = b[0].replace(',','')
        temp_count = c

        temp_lookup_data = temp_i + '+' + temp_j
        temp_lookup = res_dict.get(temp_lookup_data)

        if(temp_lookup == None):
            res_dict[temp_lookup_data] = temp_count
        else:
            res_dict[temp_lookup_data] = temp_count + temp_lookup
        


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
    count = b
    file1 = open(res_Data, "a")
    file1.writelines(str(source_i) + ',' + str(destination_j) + ',' + str(count) + '\n')
    file1.flush()
    No_of_rows_after_count += 1

file1.close()
print("####### RESULTS WRITTEN TO FILE ###########")

#new_df.to_csv(res_Data, encoding='utf-8', index=False)
#No_of_rows_after_count = len(new_df)
            

#print("######## Written to file ########")


print("No. of rows in the Original: ", No_of_rows_in_original)
print("No. of rows after count: ", No_of_rows_after_count)
#print("No. of None", No_of_None)

print("######## DONE ########")