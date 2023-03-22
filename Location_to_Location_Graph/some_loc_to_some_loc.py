import csv

print("##################### START ##################################")

Full_File = '/global/D1/projects/umod/dipp/playground/Location_to_Location_User_ID/results_loc_to_loc_from_user_to_user.csv'
Res_file = '/global/D1/projects/umod/dipp/playground/Location_to_Location_User_ID/res_some_loc_to_some_loc.csv'

file1 = open(Res_file, "a")  # append mode
file1.write('i,j,contacts,mentions'+'\n')
file1.close()

print("################# STARTING THE PROCESS #################")

with open(Full_File, newline='', encoding='utf-8') as full_file:
    no_match = "['none', 'none', 'none', 'none', 'none']"
    No_of_Data = 0
    full_data = csv.DictReader((x.replace('\0', '') for x in full_file))

    file1 = open(Res_file, "a")  # append mode
    
    for line in full_data:
        if((line['i'] != no_match) and (line['j'] != no_match)):
            No_of_Data += 1

            file1 = open(Res_file, "a")  # append mode
            file1.write('"' + line['i'] + '"' + ',' + '"' + line['j'] + '"' + ',' + line['contacts']+ ',' + line['mentions'] + '\n')
            #file1.close()
            file1.flush()
        
    #os.fsync(file1)
    file1.close()

print("No. of Data: ", No_of_Data)
print("######################### DONE ##################################")