import csv
import pandas as pd
# import sys

# csv.field_size_limit(sys.maxsize)

def loc_country_code(loc_data, res_Data):
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


loc_data_01 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/results_locations_with_count_cat_1_only_2.csv'
loc_data_02 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/results_locations_with_count_cat_2_only_2.csv'
loc_data_03 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/results_locations_with_count_cat_3_only_2.csv'
loc_data_04 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/results_locations_with_count_cat_4_only_2.csv'
loc_data_05 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/results_locations_with_count_cat_5_only_2.csv'
loc_data_06 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/results_locations_with_count_cat_6_only_2.csv'
loc_data_07 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/results_locations_with_count_cat_7_only_2.csv'
loc_data_08 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/results_locations_with_count_cat_8_only_2.csv'
loc_data_09 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/results_locations_with_count_cat_9_only_2.csv'
loc_data_10 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/results_locations_with_count_cat_all_only_2.csv'

loc_data_11 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/results_locations_with_count_cat_1_only_2_or_3.csv'
loc_data_12 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/results_locations_with_count_cat_2_only_2_or_3.csv'
loc_data_13 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/results_locations_with_count_cat_3_only_2_or_3.csv'
loc_data_14 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/results_locations_with_count_cat_4_only_2_or_3.csv'
loc_data_15 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/results_locations_with_count_cat_5_only_2_or_3.csv'
loc_data_16 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/results_locations_with_count_cat_6_only_2_or_3.csv'
loc_data_17 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/results_locations_with_count_cat_7_only_2_or_3.csv'
loc_data_18 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/results_locations_with_count_cat_8_only_2_or_3.csv'
loc_data_19 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/results_locations_with_count_cat_9_only_2_or_3.csv'
loc_data_20 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/results_locations_with_count_cat_all_only_2_or_3.csv'

res_Data_01 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/Location_with_only_country_code_cat_1_only_2.csv'
res_Data_02 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/Location_with_only_country_code_cat_2_only_2.csv'
res_Data_03 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/Location_with_only_country_code_cat_3_only_2.csv'
res_Data_04 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/Location_with_only_country_code_cat_4_only_2.csv'
res_Data_05 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/Location_with_only_country_code_cat_5_only_2.csv'
res_Data_06 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/Location_with_only_country_code_cat_6_only_2.csv'
res_Data_07 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/Location_with_only_country_code_cat_7_only_2.csv'
res_Data_08 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/Location_with_only_country_code_cat_8_only_2.csv'
res_Data_09 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/Location_with_only_country_code_cat_9_only_2.csv'
res_Data_10 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/Location_with_only_country_code_cat_all_only_2.csv'

res_Data_11 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/Location_with_only_country_code_cat_1_only_2_or_3.csv'
res_Data_12 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/Location_with_only_country_code_cat_2_only_2_or_3.csv'
res_Data_13 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/Location_with_only_country_code_cat_3_only_2_or_3.csv'
res_Data_14 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/Location_with_only_country_code_cat_4_only_2_or_3.csv'
res_Data_15 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/Location_with_only_country_code_cat_5_only_2_or_3.csv'
res_Data_16 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/Location_with_only_country_code_cat_6_only_2_or_3.csv'
res_Data_17 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/Location_with_only_country_code_cat_7_only_2_or_3.csv'
res_Data_18 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/Location_with_only_country_code_cat_8_only_2_or_3.csv'
res_Data_19 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/Location_with_only_country_code_cat_9_only_2_or_3.csv'
res_Data_20 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/Location_with_only_country_code_cat_all_only_2_or_3.csv'

print("############ Cat 01 Only 2 #############")
loc_country_code(loc_data_01,res_Data_01)

print("############ Cat 02 Only 2 #############")
loc_country_code(loc_data_02,res_Data_02)

print("############ Cat 03 Only 2 #############")
loc_country_code(loc_data_03,res_Data_03)

print("############ Cat 04 Only 2 #############")
loc_country_code(loc_data_04,res_Data_04)

print("############ Cat 05 Only 2 #############")
loc_country_code(loc_data_05,res_Data_05)

print("############ Cat 06 Only 2 #############")
loc_country_code(loc_data_06,res_Data_06)

print("############ Cat 07 Only 2 #############")
loc_country_code(loc_data_07,res_Data_07)

print("############ Cat 08 Only 2 #############")
loc_country_code(loc_data_08,res_Data_08)

# print("############ Cat 09 Only 2 #############")
# loc_country_code(loc_data_09,res_Data_09)

print("############ Cat all Only 2 #############")
loc_country_code(loc_data_10,res_Data_10)


print("############ Cat 01 Only 2 or 3 #############")
loc_country_code(loc_data_11,res_Data_11)

print("############ Cat 02 Only 2 or 3 #############")
loc_country_code(loc_data_12,res_Data_12)

print("############ Cat 03 Only 2 or 3 #############")
loc_country_code(loc_data_13,res_Data_13)

print("############ Cat 04 Only 2 or 3 #############")
loc_country_code(loc_data_14,res_Data_14)

print("############ Cat 05 Only 2 or 3 #############")
loc_country_code(loc_data_15,res_Data_15)

print("############ Cat 06 Only 2 or 3 #############")
loc_country_code(loc_data_16,res_Data_16)

print("############ Cat 07 Only 2 or 3 #############")
loc_country_code(loc_data_17,res_Data_17)

print("############ Cat 08 Only 2 or 3 #############")
loc_country_code(loc_data_18,res_Data_18)

# print("############ Cat 09 Only 2 or 3 #############")
# loc_country_code(loc_data_19,res_Data_19)

print("############ Cat all Only 2 or 3 #############")
loc_country_code(loc_data_20,res_Data_20)



print("######## DONE ########")