import csv

print("###################### Reading the File Location ################################")
data_file = '/global/D1/projects/umod/dipp/Misinformation_Dataset/edge_list_labeled.csv'
result_file = '/global/D1/projects/umod/dipp/Misinformation_Dataset/Result/after_first_filter.csv'


print("####################### Reading the Data File #########################")
with open(data_file, newline='', encoding='utf-8') as csv_data:
    base_data = csv.DictReader(csv_data, delimiter=',', restkey='types')
    row_count = sum(1 for row in base_data)
    print("Number of Rows in the Data File: ", row_count)


print("####################### Reading the Result File #########################")
with open(result_file, newline='', encoding='utf-8') as csv_data:
    base_data = csv.DictReader(csv_data, delimiter=',', restkey='types')
    row_count = sum(1 for row in base_data)
    print("Number of Rows in the Result File: ", row_count)