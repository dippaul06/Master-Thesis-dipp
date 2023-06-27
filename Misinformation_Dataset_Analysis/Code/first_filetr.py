import csv
import ast

print("###################### Reading the File Location ################################")
data_file = '/global/D1/projects/umod/dipp/Misinformation_Dataset/edge_list_labeled.csv'
result_file = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/after_first_filter.csv'

print("#################### Creating the Blank File ################################")
file1 = open(result_file, "a")  # append mode
file1.write('i,j,contacts,types'+'\n')
file1.close()

print("####################### Filtering the Data #########################")
with open(data_file, newline='', encoding='utf-8') as csv_data:
    base_data = csv.DictReader(csv_data, delimiter=',', restkey='types')
    # row_count = sum(1 for row in base_data)
    # print("Number of Rows in the File: ", row_count)
    count = 0
    file1 = open(result_file, "a")  # append mode

    for line in base_data:
        # file1 = open(result_file, "a")  # append mode
        
        if(line['types'] != '[]'):
            temp = line['types']
            if(type(temp) is not str):
                temp = ",".join(str(x) for x in temp)
                temp = temp.replace(" ", "")
                file1.writelines(line['i'] + ',' + line['j'] + ',' + line['contacts'] + ',' + '[' +str(temp) + '\n')
                file1.flush()
                count += 1
                if(not (count % 100000)):
                    print('Currently processed: ' , count)
            else:
                file1.writelines(line['i'] + ',' + line['j'] + ',' + line['contacts'] + ',' + str(line['types']) + '\n')
                file1.flush()
                count += 1
                if(not (count % 100000)):
                    print('Currently processed: ' , count)
             
    file1.close()

    print("Number of Rows in new Filtered File ", count)
    print("##################################### DONE ##################################")