import csv



print("################# START ######################")

def loc_extraction(Full_Location_Data, Res_Data):

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

    

    print('Total Data: ', total_data)
    print('Total Resolved Data: ', resolved_data)
    print('Total  Exception: ', exception_data)
    print('Out of Bound: ', out_of_bound)

    return



Full_Location_Data_01 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/results_loc_to_loc_from_user_to_user_cat_01_only_2.csv'
Full_Location_Data_02 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/results_loc_to_loc_from_user_to_user_cat_02_only_2.csv'
Full_Location_Data_03 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/results_loc_to_loc_from_user_to_user_cat_03_only_2.csv'
Full_Location_Data_04 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/results_loc_to_loc_from_user_to_user_cat_04_only_2.csv'
Full_Location_Data_05 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/results_loc_to_loc_from_user_to_user_cat_05_only_2.csv'
Full_Location_Data_06 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/results_loc_to_loc_from_user_to_user_cat_06_only_2.csv'
Full_Location_Data_07 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/results_loc_to_loc_from_user_to_user_cat_07_only_2.csv'
Full_Location_Data_08 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/results_loc_to_loc_from_user_to_user_cat_08_only_2.csv'
Full_Location_Data_09 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/results_loc_to_loc_from_user_to_user_cat_09_only_2.csv'
Full_Location_Data_10 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/results_loc_to_loc_from_user_to_user_cat_all_only_2.csv'


Full_Location_Data_11 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/results_loc_to_loc_from_user_to_user_cat_01_only_2_or_3.csv'
Full_Location_Data_12 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/results_loc_to_loc_from_user_to_user_cat_02_only_2_or_3.csv'
Full_Location_Data_13 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/results_loc_to_loc_from_user_to_user_cat_03_only_2_or_3.csv'
Full_Location_Data_14 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/results_loc_to_loc_from_user_to_user_cat_04_only_2_or_3.csv'
Full_Location_Data_15 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/results_loc_to_loc_from_user_to_user_cat_05_only_2_or_3.csv'
Full_Location_Data_16 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/results_loc_to_loc_from_user_to_user_cat_06_only_2_or_3.csv'
Full_Location_Data_17 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/results_loc_to_loc_from_user_to_user_cat_07_only_2_or_3.csv'
Full_Location_Data_18 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/results_loc_to_loc_from_user_to_user_cat_08_only_2_or_3.csv'
Full_Location_Data_19 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/results_loc_to_loc_from_user_to_user_cat_09_only_2_or_3.csv'
Full_Location_Data_20 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/results_loc_to_loc_from_user_to_user_cat_all_only_2_or_3.csv'




Res_Data_01 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/results_locations_with_count_cat_1_only_2.csv'
Res_Data_02 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/results_locations_with_count_cat_2_only_2.csv'
Res_Data_03 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/results_locations_with_count_cat_3_only_2.csv'
Res_Data_04 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/results_locations_with_count_cat_4_only_2.csv'
Res_Data_05 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/results_locations_with_count_cat_5_only_2.csv'
Res_Data_06 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/results_locations_with_count_cat_6_only_2.csv'
Res_Data_07 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/results_locations_with_count_cat_7_only_2.csv'
Res_Data_08 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/results_locations_with_count_cat_8_only_2.csv'
Res_Data_09 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/results_locations_with_count_cat_9_only_2.csv'
Res_Data_10 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/results_locations_with_count_cat_all_only_2.csv'


Res_Data_11 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/results_locations_with_count_cat_1_only_2_or_3.csv'
Res_Data_12 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/results_locations_with_count_cat_2_only_2_or_3.csv'
Res_Data_13 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/results_locations_with_count_cat_3_only_2_or_3.csv'
Res_Data_14 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/results_locations_with_count_cat_4_only_2_or_3.csv'
Res_Data_15 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/results_locations_with_count_cat_5_only_2_or_3.csv'
Res_Data_16 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/results_locations_with_count_cat_6_only_2_or_3.csv'
Res_Data_17 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/results_locations_with_count_cat_7_only_2_or_3.csv'
Res_Data_18 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/results_locations_with_count_cat_8_only_2_or_3.csv'
Res_Data_19 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/results_locations_with_count_cat_9_only_2_or_3.csv'
Res_Data_20 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/location_with_value/results_locations_with_count_cat_all_only_2_or_3.csv'




print("############ Cat 01 Only 2 #############")
loc_extraction(Full_Location_Data_01,Res_Data_01)

print("############ Cat 02 Only 2 #############")
loc_extraction(Full_Location_Data_02,Res_Data_02)

print("############ Cat 03 Only 2 #############")
loc_extraction(Full_Location_Data_03,Res_Data_03)

print("############ Cat 04 Only 2 #############")
loc_extraction(Full_Location_Data_04,Res_Data_04)

print("############ Cat 05 Only 2 #############")
loc_extraction(Full_Location_Data_05,Res_Data_05)


print("############ Cat 06 Only 2 #############")
loc_extraction(Full_Location_Data_06,Res_Data_06)

print("############ Cat 07 Only 2 #############")
loc_extraction(Full_Location_Data_07,Res_Data_07)


print("############ Cat 08 Only 2 #############")
loc_extraction(Full_Location_Data_08,Res_Data_08)


# print("############ Cat 09 Only 2 #############")
# loc_extraction(Full_Location_Data_09,Res_Data_09)


print("############ Cat All Only 2 #############")
loc_extraction(Full_Location_Data_10,Res_Data_10)


print("############ Cat 01 Only 2 or 3 #############")
loc_extraction(Full_Location_Data_11,Res_Data_11)

print("############ Cat 02 Only 2 or 3 #############")
loc_extraction(Full_Location_Data_12,Res_Data_12)

print("############ Cat 03 Only 2 or 3 #############")
loc_extraction(Full_Location_Data_13,Res_Data_13)

print("############ Cat 04 Only 2 or 3 #############")
loc_extraction(Full_Location_Data_14,Res_Data_14)

print("############ Cat 05 Only 2 or 3 #############")
loc_extraction(Full_Location_Data_15,Res_Data_15)


print("############ Cat 06 Only 2 or 3 #############")
loc_extraction(Full_Location_Data_16,Res_Data_16)

print("############ Cat 07 Only 2 or 3 #############")
loc_extraction(Full_Location_Data_17,Res_Data_17)


print("############ Cat 08 Only 2 or 3 #############")
loc_extraction(Full_Location_Data_18,Res_Data_18)


# print("############ Cat 09 Only 2 or 3 #############")
# loc_extraction(Full_Location_Data_19,Res_Data_19)


print("############ Cat All Only 2 or 3 #############")
loc_extraction(Full_Location_Data_20,Res_Data_20)




print("################################ DONE  ########################################")