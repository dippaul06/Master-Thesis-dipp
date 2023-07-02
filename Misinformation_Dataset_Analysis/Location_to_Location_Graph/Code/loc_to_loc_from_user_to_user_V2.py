import csv
# import pandas as pd
#import os


def loc_to_loc_from_user_to_user(user_graph, res_file):
    print("########################### READING AND MAKING DICT FROM USER LOCATION FILE ##########################################################")
    location_user = '/global/D1/projects/umod/dipp/playground/Location_to_Location_User_ID/res_loc_rep.csv'
    with open(location_user,  newline='', encoding='utf-8') as lu:
        loc_user_dict = {row["userId"]: row["location"] for row in csv.DictReader(lu, ("createdAt","followers","favourites","location","userId"))}

    print("############################# USER LOCATION DICT DONE ###############################################################################")

    file1 = open(res_file, "a")  # append mode
    file1.write('i,j,contacts'+'\n')
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
            file1.write('"' + line['i'] + '"' + ',' + '"' + line['j'] + '"' + ',' + line['contacts']+ '\n')
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

    return


print("######################## START ###############################################")


# user_graph = '/global/D1/projects/umod/dipp/playground/Location_to_Location_User_ID/edge_list.csv'

user_graph_01 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/cat_01_only_3.csv'
user_graph_02 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/cat_02_only_3.csv'
user_graph_03 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/cat_03_only_3.csv'
user_graph_04 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/cat_04_only_3.csv'
user_graph_05 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/cat_05_only_3.csv'
user_graph_06 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/cat_06_only_3.csv'
user_graph_07 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/cat_07_only_3.csv'
user_graph_08 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/cat_08_only_3.csv'
user_graph_09 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/cat_09_only_3.csv'
user_graph_10 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/cat_all_only_3.csv'


# res_file = '/global/D1/projects/umod/dipp/playground/Location_to_Location_User_ID/results_loc_to_loc_from_user_to_user.csv'

res_file_01 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/results_loc_to_loc_from_user_to_user_cat_01_only_3.csv'
res_file_02 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/results_loc_to_loc_from_user_to_user_cat_02_only_3.csv'
res_file_03 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/results_loc_to_loc_from_user_to_user_cat_03_only_3.csv'
res_file_04 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/results_loc_to_loc_from_user_to_user_cat_04_only_3.csv'
res_file_05 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/results_loc_to_loc_from_user_to_user_cat_05_only_3.csv'
res_file_06 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/results_loc_to_loc_from_user_to_user_cat_06_only_3.csv'
res_file_07 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/results_loc_to_loc_from_user_to_user_cat_07_only_3.csv'
res_file_08 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/results_loc_to_loc_from_user_to_user_cat_08_only_3.csv'
res_file_09 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/results_loc_to_loc_from_user_to_user_cat_09_only_3.csv'
res_file_10 = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/results_loc_to_loc_from_user_to_user_cat_all_only_3.csv'


print("############ Cat 01 Only 3 #############")
loc_to_loc_from_user_to_user(user_graph_01,res_file_01)

print("############ Cat 02 Only 3 #############")
loc_to_loc_from_user_to_user(user_graph_02,res_file_02)

print("############ Cat 03 Only 3 #############")
loc_to_loc_from_user_to_user(user_graph_03,res_file_03)

print("############ Cat 04 Only 3 #############")
loc_to_loc_from_user_to_user(user_graph_04,res_file_04)

print("############ Cat 05 Only 3 #############")
loc_to_loc_from_user_to_user(user_graph_05,res_file_05)


print("############ Cat 06 Only 3 #############")
loc_to_loc_from_user_to_user(user_graph_06,res_file_06)

print("############ Cat 07 Only 3 #############")
loc_to_loc_from_user_to_user(user_graph_07,res_file_07)


print("############ Cat 08 Only 3 #############")
loc_to_loc_from_user_to_user(user_graph_08,res_file_08)


# print("############ Cat 09 Only 3 #############")
# loc_to_loc_from_user_to_user(user_graph_09,res_file_09)


print("############ Cat All Only 3 #############")
loc_to_loc_from_user_to_user(user_graph_10,res_file_10)



print("########################################### DONE ############################################################")